package com.example.newsapplication.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.newsapplication.NewsDetailActivity;
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

    private SearchView searchView;
    private RecyclerView rvSearch;
    private SwipeRefreshLayout refreshLayout;

    private ArrayList<Articles> articlesArrayList;
    private NewsRVAdapter newsRVAdapter;
    private String keyWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchView);
        rvSearch = findViewById(R.id.rv_Search);
        refreshLayout = findViewById(R.id.swipeRefresh_Search);
        articlesArrayList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvSearch.setLayoutManager(layoutManager);
        newsRVAdapter = new NewsRVAdapter(articlesArrayList, this);
        rvSearch.setAdapter(newsRVAdapter);

        newsRVAdapter.setNewsClickListener(new NewsRVAdapter.NewsClickListener() {
            @Override
            public void onClick(Articles article) {
                Intent intent = new Intent(SearchActivity.this, NewsDetailActivity.class);
                intent.putExtra("article", article);
                intent.putExtra("category", keyWord);
                startActivity(intent);
                newsRVAdapter.notifyDataSetChanged();
            }
        });

        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                articlesArrayList.clear();
                callApi(query, "3dde52248f66463eb8ef34f3d19cb936");
                keyWord = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callApi(keyWord, "3dde52248f66463eb8ef34f3d19cb936");
            }
        });
    }

    private void callApi(String keyWord, String apiKey) {
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
                     refreshLayout.setRefreshing(false);
                     newsRVAdapter.notifyDataSetChanged();

                     if (articles.isEmpty()) {
                         Toast.makeText(SearchActivity.this, "No result found", Toast.LENGTH_SHORT).show();
                     }
                }
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}