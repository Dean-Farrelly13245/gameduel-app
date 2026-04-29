package com.example.gameduel;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private List<Game> gameList;

    public GameAdapter(List<Game> gameList) {
        this.gameList = gameList;
    }

    public void updateList(List<Game> newList) {
        gameList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = gameList.get(position);
        holder.txtTitle.setText(game.getTitle());
        holder.txtGenre.setText(game.getGenre());
        holder.txtPlatform.setText(game.getPlatform());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), GameDetailActivity.class);
            intent.putExtra("title", game.getTitle());
            intent.putExtra("genre", game.getGenre());
            intent.putExtra("platform", game.getPlatform());
            intent.putExtra("releaseYear", game.getReleaseYear());
            intent.putExtra("coverImageUrl", game.getCoverImageUrl());
            intent.putExtra("wins", game.getWins());
            intent.putExtra("losses", game.getLosses());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtGenre, txtPlatform;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_game_title);
            txtGenre = itemView.findViewById(R.id.txt_game_genre);
            txtPlatform = itemView.findViewById(R.id.txt_game_platform);
        }
    }
}