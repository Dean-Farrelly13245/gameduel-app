package com.example.gameduel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GamesFragment extends Fragment {

    private RecyclerView recyclerGames;
    private GameAdapter adapter;
    private List<Game> allGames = new ArrayList<>();
    private Spinner spinnerSort;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_games, container, false);

        recyclerGames = view.findViewById(R.id.recycler_games);
        recyclerGames.setLayoutManager(new LinearLayoutManager(getContext()));

        spinnerSort = view.findViewById(R.id.spinner_sort);
        String[] sortOptions = {"A-Z", "Z-A", "Most Wins", "Most Losses", "Most Recent", "Oldest"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, sortOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(spinnerAdapter);

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortGames(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

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
                    allGames = response.body();
                    adapter = new GameAdapter(new ArrayList<>(allGames));
                    recyclerGames.setAdapter(adapter);
                    sortGames(spinnerSort.getSelectedItemPosition());
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

    private void sortGames(int position) {
        if (allGames == null || allGames.isEmpty()) return;

        List<Game> sorted = new ArrayList<>(allGames);

        switch (position) {
            case 0: Collections.sort(sorted, (a, b) -> a.getTitle().compareTo(b.getTitle())); break;
            case 1: Collections.sort(sorted, (a, b) -> b.getTitle().compareTo(a.getTitle())); break;
            case 2: Collections.sort(sorted, (a, b) -> b.getWins() - a.getWins()); break;
            case 3: Collections.sort(sorted, (a, b) -> b.getLosses() - a.getLosses()); break;
            case 4: Collections.sort(sorted, (a, b) -> b.getReleaseYear() - a.getReleaseYear()); break;
            case 5: Collections.sort(sorted, (a, b) -> a.getReleaseYear() - b.getReleaseYear()); break;
        }

        if (adapter != null) adapter.updateList(sorted);
    }
}