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

        holder.txtRank.setText(holder.itemView.getContext().getString(R.string.rank_text, position + 4));
        holder.txtTitle.setText(game.getTitle());
        holder.txtWins.setText(holder.itemView.getContext().getString(R.string.wins_text, game.getWins()));

        if (!TextUtils.isEmpty(game.getCoverImageUrl())) {
            Picasso.get()
                    .load(game.getCoverImageUrl())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_report_image)
                    .fit()
                    .centerCrop()
                    .into(holder.imgCover);
        } else {
            holder.imgCover.setImageResource(android.R.drawable.ic_menu_gallery);
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