package com.example.newsapplication.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapplication.model.Articles;
import com.example.newsapplication.NewsDetailActivity;
import com.example.newsapplication.R;
import com.example.newsapplication.model.CategoryRVModal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.ViewHolder> {

    private ArrayList<Articles> articlesArrayList;
    private Context context;
    private NewsClickListener newsClickListener;
    //private String category;

    public NewsRVAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_rv_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Articles articles = articlesArrayList.get(position);
        holder.subTitleTV.setText(articles.getDescription());
        holder.titleTV.setText(articles.getTitle());
        if (articles.getUrlToImage() != null) {
            if (!articles.getUrlToImage().isEmpty()) {
                Picasso.get().load(articles.getUrlToImage()).into(holder.newsIV);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsClickListener.onClick(articles);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (articlesArrayList != null) {
            return articlesArrayList.size();
        } else {
            return 0;
        }
    }

    public void setNewsClickListener(NewsClickListener newsClickListener) {
        this.newsClickListener = newsClickListener;
    }

    public void clearData() {
        this.articlesArrayList = null;
        notifyDataSetChanged();
    }

    interface NewsClickListener {
        void onClick(Articles article);
    }

//    public void setCategory (String category) {
//        this.category = category;
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTV, subTitleTV;
        private ImageView newsIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.idTxtNewsHeading);
            subTitleTV = itemView.findViewById(R.id.idTVSubTitle);
            newsIV = itemView.findViewById(R.id.idIVNews);
        }
    }
}
