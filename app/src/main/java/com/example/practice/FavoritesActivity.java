package com.example.practice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FavoritesActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorites);

        ImageView backButton = (ImageView) findViewById(R.id.get_back_to_main);
        TextView pageName = (TextView) findViewById(R.id.page_name);

        ImageView homeSvg = (ImageView) findViewById(R.id.home_drawable);
        DrawableCompat.setTint(DrawableCompat.wrap(homeSvg.getDrawable()), ContextCompat.getColor(getApplicationContext(), R.color.primaryDarker));

        ImageView loginSvg = (ImageView) findViewById(R.id.login_drawable);
        DrawableCompat.setTint(DrawableCompat.wrap(loginSvg.getDrawable()), ContextCompat.getColor(getApplicationContext(), R.color.primaryDarker));

        ImageView wishlistSvg = (ImageView) findViewById(R.id.wishlist_drawable);
        DrawableCompat.setTint(DrawableCompat.wrap(wishlistSvg.getDrawable()), ContextCompat.getColor(getApplicationContext(), R.color.primaryBlue));

        TextView tv = (TextView) findViewById(R.id.to_login_text);
        tv.setText("Профиль");
        LinearLayout toMain = (LinearLayout) findViewById(R.id.bottom_panel_to_main_page);
        LinearLayout toProfile = (LinearLayout) findViewById(R.id.bottom_panel_to_profile);

        toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences sp = getSharedPreferences("MegaMall", Context.MODE_PRIVATE);
        ArrayList<String> FavoritesIDs = MainActivity.FAVORITES;

        if (!FavoritesIDs.isEmpty()) {
            ((TextView)findViewById(R.id.no_goods)).setText("");
            RecyclerView favoritesRV = (RecyclerView) findViewById(R.id.favorite_goods_container);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            favoritesRV.setLayoutManager(linearLayoutManager);
            ArrayList<Item> favoriteItems = new ArrayList<Item>();
            for (int i = 0; i < MainActivity.GOODS.size(); i++) {
                if (FavoritesIDs.contains(String.valueOf(MainActivity.GOODS.get(i).id))) {

                    favoriteItems.add(MainActivity.GOODS.get(i));
                }
            }
            FavoritesItemAdapter adapter = new FavoritesItemAdapter(favoriteItems);
            favoritesRV.setAdapter(adapter);
        }

        pageName.setText("Избранное");
    }
}
