package com.example.newsapplication.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.SearchView;

import com.example.newsapplication.R;
import com.example.newsapplication.api.RetrofitAPI;
import com.example.newsapplication.api.RetrofitClient;
import com.example.newsapplication.main.NewsRVAdapter;
import com.example.newsapplication.model.Articles;
import com.example.newsapplication.model.NewsModal;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    SearchView searchView;
    RecyclerView rvSearch;
    SwipeRefreshLayout refreshLayout;

    ArrayList<Articles> articlesArrayList;
    NewsRVAdapter newsRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchView);
        rvSearch = findViewById(R.id.rv_Search);
        refreshLayout = findViewById(R.id.swipeRefresh_Search);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvSearch.setLayoutManager(layoutManager);
        newsRVAdapter = new NewsRVAdapter(articlesArrayList, this);
        rvSearch.setAdapter(newsRVAdapter);

        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callApi(query, "3dde52248f66463eb8ef34f3d19cb936");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void callApi(String keyWord, String apiKey) {
        //articlesArrayList.clear();
        RetrofitAPI service = RetrofitClient.getClient().create(RetrofitAPI.class);
        Call<NewsModal> call = service.getSearchNews(keyWord, apiKey);
        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                if (response.body() != null) {
                     NewsModal newsModal = response.body();
                     ArrayList<Articles> articles = newsModal.getArticles();
                     for (int i = 0; i < articles.size(); i++) {
                         articlesArrayList.add(new Articles(articles.get(i).getTitle(), articles.get(i).getDescription(), articles.get(i).getUrlToImage(), articles.get(i).getUrl(), articles.get(i).getContent()));
                     }
                     newsRVAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}