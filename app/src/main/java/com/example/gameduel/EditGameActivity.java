package com.example.gameduel;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditGameActivity extends AppCompatActivity {

    private EditText editTitle, editGenre, editPlatform, editReleaseYear, editCoverImageUrl;
    private int gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);

        editTitle = findViewById(R.id.edit_title);
        editGenre = findViewById(R.id.edit_genre);
        editPlatform = findViewById(R.id.edit_platform);
        editReleaseYear = findViewById(R.id.edit_release_year);
        editCoverImageUrl = findViewById(R.id.edit_cover_image_url);

        gameId = getIntent().getIntExtra("id", -1);
        editTitle.setText(getIntent().getStringExtra("title"));
        editGenre.setText(getIntent().getStringExtra("genre"));
        editPlatform.setText(getIntent().getStringExtra("platform"));
        editReleaseYear.setText(String.valueOf(getIntent().getIntExtra("releaseYear", 0)));
        editCoverImageUrl.setText(getIntent().getStringExtra("coverImageUrl"));

        Button btnSave = findViewById(R.id.btn_save_game);
        btnSave.setOnClickListener(v -> saveChanges());
    }

    private void saveChanges() {
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
        game.setId(gameId);
        game.setTitle(title);
        game.setGenre(genre);
        game.setPlatform(platform);
        game.setReleaseYear(releaseYear);
        game.setCoverImageUrl(coverImageUrl);
        game.setDescription(getIntent().getStringExtra("description") != null
                ? getIntent().getStringExtra("description") : "");
        game.setWins(getIntent().getIntExtra("wins", 0));
        game.setLosses(getIntent().getIntExtra("losses", 0));

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<Game> call = apiService.updateGame(gameId, game);

        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditGameActivity.this, "Game updated!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditGameActivity.this,
                            "Failed: " + response.code() + " " + response.message(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Toast.makeText(EditGameActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}