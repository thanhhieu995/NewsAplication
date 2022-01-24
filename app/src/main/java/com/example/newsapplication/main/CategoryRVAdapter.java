package com.example.newsapplication.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapplication.NewsDetailActivity;
import com.example.newsapplication.R;
import com.example.newsapplication.model.CategoryRVModal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder> {

    private ArrayList<CategoryRVModal> categoryRVModals;
    private Context context;
    private CategoryClickInterface categoryClickInterface;
    int selectedPosition = -1;

    public CategoryRVAdapter(ArrayList<CategoryRVModal> categoryRVModals, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryRVModals = categoryRVModals;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_rv_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CategoryRVModal categoryRVModal = categoryRVModals.get(position);
        holder.txtCategory.setText(categoryRVModal.getCategory());
        Picasso.get().load(categoryRVModal.getCategoryImageUrl()).into(holder.imgCategory);

        if (selectedPosition == position) {
            //holder.imgCategory.setBackgroundColor(Color.GREEN);
            holder.txtCategory.setTextColor(Color.GREEN);
        } else {
            holder.txtCategory.setTextColor(Color.WHITE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                categoryClickInterface.onCategoryClick(position);
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryRVModals.size();
    }

    public interface CategoryClickInterface{
        void onCategoryClick(int position);
    }

    public void setCategoryClickListener(CategoryClickInterface categoryClickInterface) {
        this.categoryClickInterface = categoryClickInterface;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCategory;
        TextView txtCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCategory = itemView.findViewById(R.id.idIVCategory);
            txtCategory = itemView.findViewById(R.id.idTVCategory);
        }
    }
}
