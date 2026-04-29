package com.example.gameduel;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class GameDetailActivity extends AppCompatActivity {

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

        String title = getIntent().getStringExtra("title");
        String genre = getIntent().getStringExtra("genre");
        String platform = getIntent().getStringExtra("platform");
        int releaseYear = getIntent().getIntExtra("releaseYear", 0);
        String coverImageUrl = getIntent().getStringExtra("coverImageUrl");
        int wins = getIntent().getIntExtra("wins", 0);
        int losses = getIntent().getIntExtra("losses", 0);

        txtTitle.setText(title);
        txtGenre.setText(getString(R.string.genre_text, genre));
        txtPlatform.setText(getString(R.string.platform_text, platform));
        txtReleaseYear.setText(getString(R.string.release_year_text, releaseYear));
        txtWins.setText(getString(R.string.wins_text, wins));
        txtLosses.setText(getString(R.string.losses_text, losses));

        if (coverImageUrl != null && !coverImageUrl.isEmpty()) {
            Picasso.get().load(coverImageUrl).into(imgCover);
        }
    }
}