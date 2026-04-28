package com.example.gameduel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GamesFragment extends Fragment {

    private RecyclerView recyclerGames;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_games, container, false);

        recyclerGames = view.findViewById(R.id.recycler_games);
        recyclerGames.setLayoutManager(new LinearLayoutManager(getContext()));

        loadGames();

        return view;
    }

    private void loadGames() {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<List<Game>> call = apiService.getGames();

        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Game> games = response.body();
                    GameAdapter adapter = new GameAdapter(games);
                    recyclerGames.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Failed to load games", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}