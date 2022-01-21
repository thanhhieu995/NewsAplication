package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapplication.model.Articles;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class NewsDetailActivity extends AppCompatActivity {

    String title, desc, content, imageURL, url, category;
    private String articlesArrayList;
    private Articles article;
    private TextView titleTV, subDescTV, contentTV, categoryTV;
    private ImageView newsIV;
    private Button readNewsBtn;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail);

//        title = getIntent().getStringExtra("title");
//        desc = getIntent().getStringExtra("desc");
//        content = getIntent().getStringExtra("content");
//        imageURL = getIntent().getStringExtra("image");
//        url = getIntent().getStringExtra("url");

        category = getIntent().getStringExtra("category");

        //articlesArrayList = getIntent().getExtras().getSerializable("article");

        article = (Articles) getIntent().getExtras().getSerializable("article");

        titleTV = findViewById(R.id.DetailTVTitle);
        subDescTV = findViewById(R.id.DetailTVDescription);
        contentTV = findViewById(R.id.DetailTVContent);
        newsIV = findViewById(R.id.DetailIVNews);
        readNewsBtn = findViewById(R.id.DetailBtnRead);
        categoryTV = findViewById(R.id.DetailTVCategory);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshDetail);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        titleTV.setText(article.getTitle());
        subDescTV.setText(article.getDescription());
        contentTV.setText(article.getContent());
        Picasso.get().load(article.getUrlToImage()).into(newsIV);

        if (category != null) {
            categoryTV.setText(category.toUpperCase(Locale.ROOT));
        }
        readNewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(article.getUrl()));
                startActivity(intent);
            }
        });
    }
}