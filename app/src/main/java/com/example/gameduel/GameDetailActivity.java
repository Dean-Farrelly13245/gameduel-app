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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        ImageView imgCover = findViewById(R.id.img_cover);
        TextView txtTitle = findViewById(R.id.txt_detail_title);
        TextView txtGenre = findViewById(R.id.txt_detail_genre);
        TextView txtPlatform = findViewById(R.id.txt_detail_platform);
        TextView txtReleaseYear = findViewById(R.id.txt_detail_release_year);
        TextView txtWins = findViewById(R.id.txt_detail_wins);
        TextView txtLosses = findViewById(R.id.txt_detail_losses);
        Button btnEdit = findViewById(R.id.btn_edit_game);
        Button btnDelete = findViewById(R.id.btn_delete_game);

        gameId = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        genre = getIntent().getStringExtra("genre");
        platform = getIntent().getStringExtra("platform");
        releaseYear = getIntent().getIntExtra("releaseYear", 0);
        coverImageUrl = getIntent().getStringExtra("coverImageUrl");
        description = getIntent().getStringExtra("description");
        wins = getIntent().getIntExtra("wins", 0);
        losses = getIntent().getIntExtra("losses", 0);

        txtTitle.setText(title);
        txtGenre.setText("Genre: " + genre);
        txtPlatform.setText("Platform: " + platform);
        txtReleaseYear.setText("Release Year: " + releaseYear);
        txtWins.setText("Wins: " + wins);
        txtLosses.setText("Losses: " + losses);

        if (coverImageUrl != null && !coverImageUrl.isEmpty()) {
            Picasso.get().load(coverImageUrl).into(imgCover);
        }

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditGameActivity.class);
            intent.putExtra("id", gameId);
            intent.putExtra("title", title);
            intent.putExtra("genre", genre);
            intent.putExtra("platform", platform);
            intent.putExtra("releaseYear", releaseYear);
            intent.putExtra("coverImageUrl", coverImageUrl);
            intent.putExtra("description", description);
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