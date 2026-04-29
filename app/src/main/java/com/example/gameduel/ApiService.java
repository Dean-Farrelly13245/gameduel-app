package com.example.gameduel;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/games")
    Call<List<Game>> getGames();

    @GET("api/leaderboard")
    Call<List<Game>> getLeaderboard();

    @POST("api/matchups")
    Call<Matchup> createMatchup(@Body Matchup matchup);

    @PUT("api/matchups/{id}/vote")
    Call<Void> voteMatchup(@Path("id") int matchupId, @Query("winnerId") int winnerId);
}