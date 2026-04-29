package com.example.gameduel;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/games")
    Call<List<Game>> getGames();

    @GET("api/games/{id}")
    Call<Game> getGame(@Path("id") int id);

    @POST("api/games")
    Call<Game> addGame(@Body Game game);

    @PUT("api/games/{id}")
    Call<Game> updateGame(@Path("id") int id, @Body Game game);

    @DELETE("api/games/{id}")
    Call<Void> deleteGame(@Path("id") int id);

    @POST("api/matchups")
    Call<Matchup> createMatchup(@Body Matchup matchup);

    @PUT("api/matchups/{id}/vote")
    Call<Void> voteMatchup(@Path("id") int id, @Query("winnerId") int winnerId);

    @GET("api/leaderboard")
    Call<List<Game>> getLeaderboard();
}