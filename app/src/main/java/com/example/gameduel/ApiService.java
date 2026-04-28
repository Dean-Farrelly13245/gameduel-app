package com.example.gameduel;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/games")
    Call<List<Game>> getGames();
}