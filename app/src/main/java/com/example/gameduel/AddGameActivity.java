package com.example.gameduel;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGameActivity extends AppCompatActivity {

    private EditText editTitle, editGenre, editPlatform, editReleaseYear, editCoverImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        editTitle = findViewById(R.id.edit_title);
        editGenre = findViewById(R.id.edit_genre);
        editPlatform = findViewById(R.id.edit_platform);
        editReleaseYear = findViewById(R.id.edit_release_year);
        editCoverImageUrl = findViewById(R.id.edit_cover_image_url);

        Button btnSave = findViewById(R.id.btn_save_game);
        btnSave.setOnClickListener(v -> saveGame());
    }

    private void saveGame() {
        String title = editTitle.getText().toString().trim();
        String genre = editGenre.getText().toString().trim();
        String platform = editPlatform.getText().toString().trim();
        String releaseYearStr = editReleaseYear.getText().toString().trim();
        String coverImageUrl = editCoverImageUrl.getText().toString().trim();

        if (title.isEmpty() || genre.isEmpty() || platform.isEmpty() || releaseYearStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int releaseYear = Integer.parseInt(releaseYearStr);

        Game game = new Game();
        game.setTitle(title);
        game.setGenre(genre);
        game.setPlatform(platform);
        game.setReleaseYear(releaseYear);
        game.setCoverImageUrl(coverImageUrl);
        game.setDescription("");
        game.setWins(0);
        game.setLosses(0);

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<Game> call = apiService.addGame(game);

        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddGameActivity.this, "Game added!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddGameActivity.this, "Failed to add game", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Toast.makeText(AddGameActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}