package vn.hieunguyen.newsapplication.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import vn.hieunguyen.newsapplication.NewsDetailActivity;
import vn.hieunguyen.newsapplication.api.RetrofitClient;
import vn.hieunguyen.newsapplication.model.Articles;
import com.example.newsapplication.R;
import vn.hieunguyen.newsapplication.api.RetrofitAPI;
import vn.hieunguyen.newsapplication.model.CategoryRVModal;
import vn.hieunguyen.newsapplication.model.NewsModal;
import vn.hieunguyen.newsapplication.search.SearchActivity;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {

    //3dde52248f66463eb8ef34f3d19cb936

    private RecyclerView newsRV, categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;

    ImageView imgSearch;

    private String lastCategory = "all";

    SwipeRefreshLayout refreshLayout;

    Boolean hasMore = false;

    Boolean hasMoreAdapter = false;

    private int lastPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRV = findViewById(R.id.idRVNews);
        categoryRV = findViewById(R.id.idRVCategories);
        loadingPB = findViewById(R.id.idPBloading);
        refreshLayout = findViewById(R.id.swipeRefresh);
        imgSearch = findViewById(R.id.imgSearch);


        articlesArrayList = new ArrayList<>();
        categoryRVModalArrayList = new ArrayList<>();
        newsRVAdapter = new NewsRVAdapter(articlesArrayList, this);
        newsRVAdapter.setNewsClickListener(new NewsRVAdapter.NewsClickListener() {
            @Override
            public void onClick(Articles article) {
                Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);
                intent.putExtra("article", article);
                intent.putExtra("category", lastCategory);
                startActivity(intent);
            }
        });

//        newsRVAdapter.setNewsItemViewClick(new NewsRVAdapter.NewsItemViewClick() {
//            @Override
//            public void onClick(View view) {
//                view.setBackgroundColor(Color.parseColor("#FF6200EE"));
//            }
//        });

        categoryRVAdapter = new CategoryRVAdapter(categoryRVModalArrayList, this, this);
//        categoryRVAdapter.setCategoryClickListener(new CategoryRVAdapter.CategoryClickInterface() {
//            @Override
//            public void onCategoryClick(int position) {
//
//            }
//        });

        newsRVAdapter.setHasMore(hasMoreAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        newsRV.setLayoutManager(layoutManager);
        newsRV.setAdapter(newsRVAdapter);


        categoryRV.setAdapter(categoryRVAdapter);

        getCategories();

        callNewsApi("all");
        newsRVAdapter.notifyDataSetChanged();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                hasMore = true;
                callNewsApi(lastCategory);
                newsRVAdapter.setHasMore(true);
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getCategories() {
        categoryRVModalArrayList.add(new CategoryRVModal("All", "https://images.unsplash.com/photo-1641889588459-c9fd1df8b265?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=900&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology", "https://images.unsplash.com/photo-1553272725-086100aecf5e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHwxfHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=900&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Science", "https://images.unsplash.com/photo-1641841344411-49dbd02896f4?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw1fHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=900&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Sport", "https://images.unsplash.com/photo-1632845617263-a8cc868f613b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDExfHRvd0paRnNrcEdnfHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=900&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("General", "https://images.unsplash.com/photo-1641238215950-9e0435966e20?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDF8Ym84alFLVGFFMFl8fGVufDB8fHx8&auto=format&fit=crop&w=900&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Business", "https://images.unsplash.com/photo-1641760378661-6f290a50a62d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDJ8YWV1NnJMLWo2ZXd8fGVufDB8fHx8&auto=format&fit=crop&w=900&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment", "https://images.unsplash.com/photo-1641610856184-99f8e66da478?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDEwfFM0TUtMQXNCQjc0fHxlbnwwfHx8fA%3D%3D&auto=format&fit=crop&w=900&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Health", "https://images.unsplash.com/photo-1641808895530-4de94b53c6e5?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDN8X2hiLWRsNFEtNFV8fGVufDB8fHx8&auto=format&fit=crop&w=900&q=60"));
        categoryRVAdapter.notifyDataSetChanged();
    }

    private void callNewsApi(String category) {
        if (!hasMore) {
            loadingPB.setVisibility(View.VISIBLE);
        }
        //newsRVAdapter.setHasMore(true);
        articlesArrayList.clear();
        RetrofitAPI service = RetrofitClient.getClient().create(RetrofitAPI.class);
        Call<NewsModal> call = service.getAllNews("us","3dde52248f66463eb8ef34f3d19cb936");
        if(category.equals("all")) {
            call = service.getAllNews("us","3dde52248f66463eb8ef34f3d19cb936");
        } else {
            call = service.getNewsByCategory("in", category, "3dde52248f66463eb8ef34f3d19cb936");
        }

        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                if (response.body() != null) {
                    NewsModal newsModal = response.body();
                    loadingPB.setVisibility(View.GONE);
                    ArrayList<Articles> articles = newsModal.getArticles();
                    for (int i = 0; i < articles.size(); i++) {
                        articlesArrayList.add(new Articles(articles.get(i).getTitle(), articles.get(i).getDescription(), articles.get(i).getUrlToImage(), articles.get(i).getUrl(), articles.get(i).getContent()));
                    }
                    newsRVAdapter.notifyDataSetChanged();

                    refreshLayout.setRefreshing(false);
                    hasMore = false;
                }
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail to get news", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCategoryClick(int position) {
        newsRVAdapter.setHasMore(true);
        String category = categoryRVModalArrayList.get(position).getCategory();
        callNewsApi(category.toLowerCase(Locale.ROOT));
        this.lastCategory = category.toLowerCase(Locale.ROOT);
        newsRVAdapter.notifyDataSetChanged();

        //newsRVAdapter.setCategory(category);
        //newsRVAdapter = new NewsRVAdapter(articlesArrayList, this, category);
    }
}