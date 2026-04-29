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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderboardFragment extends Fragment {

    private RecyclerView recyclerLeaderboard;
    private LeaderboardAdapter adapter;
    private TextView txtStatus;

    private ImageView imgFirst;
    private ImageView imgSecond;
    private ImageView imgThird;

    private TextView txtFirstTitle;
    private TextView txtSecondTitle;
    private TextView txtThirdTitle;

    private TextView txtFirstWins;
    private TextView txtSecondWins;
    private TextView txtThirdWins;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        txtStatus = view.findViewById(R.id.txt_leaderboard_status);
        recyclerLeaderboard = view.findViewById(R.id.recycler_leaderboard);

        imgFirst = view.findViewById(R.id.img_first_place);
        imgSecond = view.findViewById(R.id.img_second_place);
        imgThird = view.findViewById(R.id.img_third_place);

        txtFirstTitle = view.findViewById(R.id.txt_first_title);
        txtSecondTitle = view.findViewById(R.id.txt_second_title);
        txtThirdTitle = view.findViewById(R.id.txt_third_title);

        txtFirstWins = view.findViewById(R.id.txt_first_wins);
        txtSecondWins = view.findViewById(R.id.txt_second_wins);
        txtThirdWins = view.findViewById(R.id.txt_third_wins);

        recyclerLeaderboard.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LeaderboardAdapter(new ArrayList<>());
        recyclerLeaderboard.setAdapter(adapter);

        loadLeaderboard();

        return view;
    }

    private void loadLeaderboard() {
        txtStatus.setText(getString(R.string.loading_leaderboard));

        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<List<Game>> call = apiService.getLeaderboard();

        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Game> gameList = response.body();

                    Game firstPlace = gameList.get(0);
                    Game secondPlace = gameList.get(1);
                    Game thirdPlace = gameList.get(2);

                    bindPodiumGame(firstPlace, imgFirst, txtFirstTitle, txtFirstWins);
                    bindPodiumGame(secondPlace, imgSecond, txtSecondTitle, txtSecondWins);
                    bindPodiumGame(thirdPlace, imgThird, txtThirdTitle, txtThirdWins);

                    List<Game> remainingGames = new ArrayList<>(gameList.subList(3, gameList.size()));
                    adapter.updateList(remainingGames);

                    txtStatus.setText("");
                } else {
                    txtStatus.setText(getString(R.string.failed_load_leaderboard));
                    Toast.makeText(getContext(), getString(R.string.failed_load_leaderboard), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                txtStatus.setText(getString(R.string.could_not_connect_api));
                Toast.makeText(getContext(), getString(R.string.error_with_message, t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindPodiumGame(Game game, ImageView imageView, TextView titleView, TextView winsView) {
        titleView.setText(game.getTitle());
        winsView.setText(getString(R.string.wins_text, game.getWins()));

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
}