package com.example.practice;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class FavoritesItemAdapter extends RecyclerView.Adapter<FavoritesItemAdapter.ViewHolder> {
    private List<Item> items;

    public FavoritesItemAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemName.setText(item.name);
        holder.itemPrice.setText(String.valueOf(item.price) + " рублей");
        holder.itemPreview.setImageDrawable(item.preview);

        holder.deleteFromFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition == RecyclerView.NO_POSITION) {
                    return;
                }

                SharedPreferences sp = view.getContext().getSharedPreferences("MegaMall", Context.MODE_PRIVATE);
                SharedPreferences.Editor spEdit = sp.edit();

                ArrayList<String> FavoritesIDs = MainActivity.FAVORITES;
                ArrayList<String> newFavorites = (ArrayList<String>) MainActivity.FAVORITES.stream().filter(str -> !String.valueOf(item.id).equals(str)).collect(Collectors.toList());
                MainActivity.FAVORITES.clear();
                MainActivity.FAVORITES.addAll(newFavorites);
                spEdit.putStringSet(MainActivity.SP_FAVORITES, new HashSet<>(MainActivity.FAVORITES));
                spEdit.apply();

                items.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);

                Toast.makeText(view.getContext(), "Товар убран из избранного", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemPrice;
        ImageView itemPreview;
        TextView deleteFromFavorites;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemPreview = itemView.findViewById(R.id.item_preview);
            deleteFromFavorites = itemView.findViewById(R.id.delete_from_favorites);
        }
    }
}
