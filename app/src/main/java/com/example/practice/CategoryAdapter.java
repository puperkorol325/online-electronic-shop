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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> items;

    public CategoryAdapter(List<Category> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category item = items.get(position);
        holder.itemName.setText(item.name);
        holder.itemPreview.setImageDrawable(item.image);
        holder.itemCard.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), item.color));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        ImageView itemPreview;
        CardView itemCard;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.category_name);
            itemPreview = itemView.findViewById(R.id.category_image);
            itemCard = itemView.findViewById(R.id.category_card);
        }
    }
}
