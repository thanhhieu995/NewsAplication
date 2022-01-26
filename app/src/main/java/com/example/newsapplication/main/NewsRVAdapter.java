package com.example.newsapplication.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapplication.model.Articles;
import com.example.newsapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.ViewHolder> {

    private ArrayList<Articles> articlesArrayList;
    private Context context;
    private NewsClickListener newsClickListener;
    //private String category;
    private View view;
    private NewsItemViewClick newsItemViewClick;

    private final ArrayList<Integer> selected = new ArrayList<>();

    private boolean hasMoreAdapter = false;

    private int selectedPosition = RecyclerView.NO_POSITION;

    public NewsRVAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_rv_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        //hasMore = true;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Articles articles = articlesArrayList.get(position);
        holder.subTitleTV.setText(articles.getDescription());
        holder.titleTV.setText(articles.getTitle());
        if (articles.getUrlToImage() != null) {
            if (!articles.getUrlToImage().isEmpty()) {
                Picasso.get().load(articles.getUrlToImage()).into(holder.newsIV);
            }
        }

        if (!selected.contains(position)) {
            holder.itemView.setBackgroundColor(Color.parseColor("#292D36"));
        } else {
            holder.itemView.setBackgroundColor(Color.CYAN);
        }

        if (hasMoreAdapter == true) {
            selected.clear();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsClickListener.onClick(articles);
                //selectedPosition = position;
                //holder.itemView.setBackgroundColor(Color.parseColor("#FF6200EE"));
                v.setBackgroundColor(Color.CYAN);
//                view = holder.itemView;
//                if (newsItemViewClick != null) {
//                    newsItemViewClick.onClick(view);
//                }
//                if (selected.isEmpty()) {
//                    selected.add(position);
//                } else {
//                    int oldSelected = selected.get(0);
//                    //selected.clear();
//                    selected.add(position);
//                    notifyItemChanged(oldSelected);
//                }
//                selected.add(position);
//                if(hasMoreAdapter == true) {
//                    selected.clear();
//                } else {
//
//                }
                selected.add(position);
//                if (hasMoreAdapter == false) {
//                    selected.add(position);
//                } else {
//                    selected.clear();
//                }
            }
        });



//        if (selectedPosition == position) {
//            holder.itemView.setBackgroundColor(Color.GREEN);
//        } else {
//
//        }

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

    public interface NewsClickListener {
        void onClick(Articles article);
    }

    public interface NewsItemViewClick {
        void onClick(View view);
    }

    public void setNewsItemViewClick (NewsItemViewClick newsItemViewClick) {
        this.newsItemViewClick = newsItemViewClick;
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

    public void setHasMore(boolean hasMore) {
        this.hasMoreAdapter = hasMore;
    }
}
