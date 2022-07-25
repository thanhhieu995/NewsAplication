package vn.hieunguyen.newsapplication.api;

import vn.hieunguyen.newsapplication.model.NewsModal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @GET("v2/top-headlines")
    Call<NewsModal> getAllNews(@Query("country") String country, @Query("apiKey") String apikey);

    @GET("v2/top-headlines")
    Call<NewsModal> getNewsByCategory(@Query("country") String country, @Query("category") String category, @Query("apiKey") String apiKey);

    @GET("v2/everything")
    Call<NewsModal> getSearchNews(@Query("q") String keyWord, @Query("apiKey") String apiKey);
}
