package com.example.gameduel;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchupsFragment extends Fragment {

    private View cardGameA;
    private View cardGameB;

    private ImageView imgGameA;
    private ImageView imgGameB;

    private TextView txtGameATitle;
    private TextView txtGameAGenre;
    private TextView txtGameAPlatform;

    private TextView txtGameBTitle;
    private TextView txtGameBGenre;
    private TextView txtGameBPlatform;

    private TextView txtStatus;

    private List<Game> allGames = new ArrayList<>();
    private Game currentGameA;
    private Game currentGameB;

    private final Random random = new Random();
    private boolean isVoting = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matchups, container, false);

        cardGameA = view.findViewById(R.id.card_game_a);
        cardGameB = view.findViewById(R.id.card_game_b);

        imgGameA = view.findViewById(R.id.img_game_a);
        imgGameB = view.findViewById(R.id.img_game_b);

        txtGameATitle = view.findViewById(R.id.txt_game_a_title);
        txtGameAGenre = view.findViewById(R.id.txt_game_a_genre);
        txtGameAPlatform = view.findViewById(R.id.txt_game_a_platform);

        txtGameBTitle = view.findViewById(R.id.txt_game_b_title);
        txtGameBGenre = view.findViewById(R.id.txt_game_b_genre);
        txtGameBPlatform = view.findViewById(R.id.txt_game_b_platform);

        txtStatus = view.findViewById(R.id.txt_status);

        cardGameA.setOnClickListener(v -> voteForGame(currentGameA));
        cardGameB.setOnClickListener(v -> voteForGame(currentGameB));

        loadGames();

        return view;
    }

    private void loadGames() {
        txtStatus.setText("Loading games...");

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<List<Game>> call = apiService.getGames();

        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allGames = response.body();

                    if (allGames.size() < 2) {
                        txtStatus.setText("You need at least 2 games to start voting.");
                        setCardsEnabled(false);
                        return;
                    }

                    showRandomMatchup();
                } else {
                    txtStatus.setText("Failed to load games.");
                    showToast("Failed to load games");
                    setCardsEnabled(false);
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                txtStatus.setText("Could not connect to the API.");
                showToast("Error: " + t.getMessage());
                setCardsEnabled(false);
            }
        });
    }

    private void showRandomMatchup() {
        if (allGames.size() < 2) {
            txtStatus.setText("Not enough games for a matchup.");
            return;
        }

        int firstIndex = random.nextInt(allGames.size());
        int secondIndex = random.nextInt(allGames.size());

        while (secondIndex == firstIndex) {
            secondIndex = random.nextInt(allGames.size());
        }

        currentGameA = allGames.get(firstIndex);
        currentGameB = allGames.get(secondIndex);

        bindGameToCard(currentGameA, imgGameA, txtGameATitle, txtGameAGenre, txtGameAPlatform);
        bindGameToCard(currentGameB, imgGameB, txtGameBTitle, txtGameBGenre, txtGameBPlatform);

        txtStatus.setText("Tap a game to vote");
        setCardsEnabled(true);
        isVoting = false;
    }

    private void bindGameToCard(Game game, ImageView imageView, TextView txtTitle, TextView txtGenre, TextView txtPlatform) {
        txtTitle.setText(game.getTitle());
        txtGenre.setText("Genre: " + game.getGenre());
        txtPlatform.setText("Platform: " + game.getPlatform());

        if (!TextUtils.isEmpty(game.getCoverImageUrl())) {
            Picasso.get()
                    .load(game.getCoverImageUrl())
                    .fit()
                    .centerCrop()
                    .into(imageView);
        } else {
            imageView.setImageDrawable(null);
        }
    }

    private void voteForGame(Game winnerGame) {
        if (winnerGame == null || currentGameA == null || currentGameB == null || isVoting) {
            return;
        }

        isVoting = true;
        setCardsEnabled(false);
        txtStatus.setText("Saving vote...");

        Matchup matchup = new Matchup(currentGameA.getId(), currentGameB.getId(), null);

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<Matchup> createCall = apiService.createMatchup(matchup);

        createCall.enqueue(new Callback<Matchup>() {
            @Override
            public void onResponse(Call<Matchup> call, Response<Matchup> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int matchupId = response.body().getId();
                    sendVote(matchupId, winnerGame.getId());
                } else {
                    isVoting = false;
                    txtStatus.setText("Failed to create matchup.");
                    showToast("Failed to create matchup");
                    setCardsEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<Matchup> call, Throwable t) {
                isVoting = false;
                txtStatus.setText("Could not create matchup.");
                showToast("Error: " + t.getMessage());
                setCardsEnabled(true);
            }
        });
    }

    private void sendVote(int matchupId, int winnerId) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<Void> voteCall = apiService.voteMatchup(matchupId, winnerId);

        voteCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showToast("Vote saved");
                    showRandomMatchup();
                } else {
                    isVoting = false;
                    txtStatus.setText("Failed to save vote.");
                    showToast("Failed to save vote");
                    setCardsEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isVoting = false;
                txtStatus.setText("Could not save vote.");
                showToast("Error: " + t.getMessage());
                setCardsEnabled(true);
            }
        });
    }

    private void setCardsEnabled(boolean enabled) {
        cardGameA.setEnabled(enabled);
        cardGameB.setEnabled(enabled);

        cardGameA.setAlpha(enabled ? 1.0f : 0.6f);
        cardGameB.setAlpha(enabled ? 1.0f : 0.6f);
    }

    private void showToast(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}