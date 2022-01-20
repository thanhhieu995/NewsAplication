package com.example.newsapplication.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.example.newsapplication.R;

public class SearchActivity extends AppCompatActivity {

    SearchView searchView;
    RecyclerView rvSearch;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchView);
        rvSearch = findViewById(R.id.rv_Search);
        refreshLayout = findViewById(R.id.swipeRefresh_Search);
    }
}