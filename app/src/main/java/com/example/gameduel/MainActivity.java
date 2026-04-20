package com.example.gameduel;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_nav);

        // load games screen by default when app opens
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_area, new GamesFragment())
                    .commit();
        }

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_games) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_area, new GamesFragment())
                        .commit();
                return true;
            } else if (id == R.id.nav_matchups) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_area, new MatchupsFragment())
                        .commit();
                return true;
            } else if (id == R.id.nav_leaderboard) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_area, new LeaderboardFragment())
                        .commit();
                return true;
            }

            return false;
        });
    }
}