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
import java.util.Set;
import java.util.stream.Collectors;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Item> items;

    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemName.setText(item.name);
        holder.itemPrice.setText(String.valueOf(item.price) + " рублей");
        holder.itemPreview.setImageDrawable(item.preview);

        holder.clickablePart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ItemDetailsActivity.class);
                intent.putExtra("itemID", item.id);
                view.getContext().startActivity(intent);
            }
        });

        holder.popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View popupView = LayoutInflater.from(view.getContext()).inflate(R.layout.product_popup_menu, null);
                popupView.setElevation(10);
                PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                popupView.findViewById(R.id.product_popup_menu_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                TextView addToFavorites = (TextView) popupView.findViewById(R.id.add_to_favorites);
                addToFavorites.setText(MainActivity.FAVORITES.contains(String.valueOf(item.id)) ? "Убрать из избранного" : "Добавить в избранное");
                addToFavorites.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences sp = view.getContext().getSharedPreferences("MegaMall", Context.MODE_PRIVATE);
                        SharedPreferences.Editor spEdit = sp.edit();
                        if (sp.getBoolean(MainActivity.SP_IS_USER_LOGGED_IN, false)) {
                            if (!MainActivity.FAVORITES.contains(String.valueOf(item.id))) {

                                MainActivity.FAVORITES.add(String.valueOf(item.id));
                                spEdit.putStringSet(MainActivity.SP_FAVORITES, new HashSet<>(MainActivity.FAVORITES));
                                spEdit.apply();
                                Toast.makeText(view.getContext(), "Товар добавлен в избранное", Toast.LENGTH_LONG).show();
                                addToFavorites.setText("Убрать из избранного");
                            }else {
                                ArrayList<String> newFavorites = (ArrayList<String>) MainActivity.FAVORITES.stream().filter(str -> !String.valueOf(item.id).equals(str)).collect(Collectors.toList());
                                MainActivity.FAVORITES.clear();
                                MainActivity.FAVORITES.addAll(newFavorites);
                                spEdit.putStringSet(MainActivity.SP_FAVORITES, new HashSet<>(MainActivity.FAVORITES));
                                Toast.makeText(view.getContext(), "Товар убран из избранного", Toast.LENGTH_LONG).show();
                                addToFavorites.setText("Добавить в избранное");
                            }
                        }else {
                            Toast.makeText(view.getContext(), "Зарегистрируйтесь, чтобы добавить товар в избранное", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                popupWindow.showAsDropDown(holder.popupButton);

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
        View clickablePart;
        ImageView popupButton;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemPreview = itemView.findViewById(R.id.item_preview);
            clickablePart = itemView.findViewById(R.id.clickable_part);
            popupButton = itemView.findViewById(R.id.popup_button);
        }
    }
}
