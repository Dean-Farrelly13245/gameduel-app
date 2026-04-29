package com.example.gameduel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameDetailActivity extends AppCompatActivity {

    private int gameId;
    private String title, genre, platform, coverImageUrl, description;
    private int releaseYear, wins, losses;

    private ImageView imgCover;
    private TextView txtTitle, txtGenre, txtPlatform, txtReleaseYear, txtWins, txtLosses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        imgCover = findViewById(R.id.img_cover);
        txtTitle = findViewById(R.id.txt_detail_title);
        txtGenre = findViewById(R.id.txt_detail_genre);
        txtPlatform = findViewById(R.id.txt_detail_platform);
        txtReleaseYear = findViewById(R.id.txt_detail_release_year);
        txtWins = findViewById(R.id.txt_detail_wins);
        txtLosses = findViewById(R.id.txt_detail_losses);
        Button btnEdit = findViewById(R.id.btn_edit_game);
        Button btnDelete = findViewById(R.id.btn_delete_game);

        gameId = getIntent().getIntExtra("id", -1);

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditGameActivity.class);
            intent.putExtra("id", gameId);
            intent.putExtra("title", title);
            intent.putExtra("genre", genre);
            intent.putExtra("platform", platform);
            intent.putExtra("releaseYear", releaseYear);
            intent.putExtra("coverImageUrl", coverImageUrl);
            intent.putExtra("description", description);
            intent.putExtra("wins", wins);
            intent.putExtra("losses", losses);
            startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Game")
                    .setMessage("Are you sure you want to delete " + title + "?")
                    .setPositiveButton("Delete", (dialog, which) -> deleteGame())
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.getGame(gameId).enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Game game = response.body();
                    title = game.getTitle();
                    genre = game.getGenre();
                    platform = game.getPlatform();
                    releaseYear = game.getReleaseYear();
                    coverImageUrl = game.getCoverImageUrl();
                    description = game.getDescription();
                    wins = game.getWins();
                    losses = game.getLosses();

                    txtTitle.setText(title);
                    txtGenre.setText("Genre: " + genre);
                    txtPlatform.setText("Platform: " + platform);
                    txtReleaseYear.setText("Release Year: " + releaseYear);
                    txtWins.setText("Wins: " + wins);
                    txtLosses.setText("Losses: " + losses);

                    if (coverImageUrl != null && !coverImageUrl.isEmpty()) {
                        Picasso.get().load(coverImageUrl).into(imgCover);
                    }
                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Toast.makeText(GameDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteGame() {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<Void> call = apiService.deleteGame(gameId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(GameDetailActivity.this, "Game deleted!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(GameDetailActivity.this, "Failed to delete game", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(GameDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}