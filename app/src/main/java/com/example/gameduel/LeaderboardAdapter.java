package com.example.gameduel;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {

    private List<Game> gameList;

    public LeaderboardAdapter(List<Game> gameList) {
        this.gameList = gameList;
    }

    public void updateList(List<Game> newList) {
        gameList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaderboard, parent, false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
        Game game = gameList.get(position);

        holder.txtRank.setText("#" + (position + 4));
        holder.txtTitle.setText(game.getTitle());
        holder.txtWins.setText("Wins: " + game.getWins());

        if (!TextUtils.isEmpty(game.getCoverImageUrl())) {
            Picasso.get()
                    .load(game.getCoverImageUrl())
                    .fit()
                    .centerCrop()
                    .into(holder.imgCover);
        } else {
            holder.imgCover.setImageDrawable(null);
        }
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public static class LeaderboardViewHolder extends RecyclerView.ViewHolder {
        TextView txtRank;
        TextView txtTitle;
        TextView txtWins;
        ImageView imgCover;

        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRank = itemView.findViewById(R.id.txt_rank);
            txtTitle = itemView.findViewById(R.id.txt_leaderboard_title);
            txtWins = itemView.findViewById(R.id.txt_leaderboard_wins);
            imgCover = itemView.findViewById(R.id.img_leaderboard_cover);
        }
    }
}