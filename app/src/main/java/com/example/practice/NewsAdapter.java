package com.example.practice;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<News> items;

    public NewsAdapter(List<News> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_container, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        News item = items.get(position);
        holder.itemTitle.setText(item.title);
        holder.itemPreview.setImageDrawable(item.image);
        holder.itemText.setText(item.text.substring(0, Math.min(32, item.text.length())) + "...");
        holder.itemDate.setText(item.date.getDay() + "." + item.date.getMonth() + "." + item.date.getYear());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;
        ImageView itemPreview;
        TextView itemText;
        TextView itemDate;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.news_title);
            itemPreview = itemView.findViewById(R.id.news_preview);
            itemText = itemView.findViewById(R.id.news_description);
            itemDate = itemView.findViewById(R.id.news_date);
        }
    }
}
