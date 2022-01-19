package com.example.newsapplication.api;

import com.example.newsapplication.model.NewsModal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitAPI {
    @GET("v2/top-headlines")
    Call<NewsModal> getAllNews(@Query("country") String country, @Query("apiKey") String apikey);

    @GET("v2/top-headlines")
    Call<NewsModal> getNewsByCategory(@Query("country") String country, @Query("category") String category, @Query("apiKey") String apiKey);
}
