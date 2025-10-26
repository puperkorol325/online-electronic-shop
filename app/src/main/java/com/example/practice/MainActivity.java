package com.example.practice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String SP_IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN";
    public static final String SP_USER_NAME = "USER_NAME";
    public static final String SP_USER_PASSWORD = "USER_PASSWORD";
    public static final String SP_USER_PHONE_OR_EMAIL = "USER_PHONE_OR_EMAIL";
    public static final String SP_USER_PICTURE = "USER_PICTURE";
    public static final String SP_FAVORITES = "FAVORITES";

    public static final ArrayList<Item> GOODS = new ArrayList<Item>(){};
    public static final ArrayList<Category> CATEGORIES = new ArrayList<Category>();
    public static final ArrayList<News> NEWS = new ArrayList<News>();


    public static final ArrayList<String> FAVORITES = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView homeSvg = (ImageView) findViewById(R.id.home_drawable);
        DrawableCompat.setTint(DrawableCompat.wrap(homeSvg.getDrawable()), ContextCompat.getColor(getApplicationContext(), R.color.primaryBlue));

        LinearLayout toProfileButton = (LinearLayout) findViewById(R.id.bottom_panel_to_profile);
        LinearLayout toWishListButton = (LinearLayout) findViewById(R.id.bottom_panel_to_wishlist);

        SharedPreferences sp = getSharedPreferences("MegaMall", Context.MODE_PRIVATE);
        Boolean isUserLoggedIn = sp.getBoolean(SP_IS_USER_LOGGED_IN, false);

        FAVORITES.addAll(sp.getStringSet(SP_FAVORITES, Set.of()));

        if (isUserLoggedIn){
            TextView tv = (TextView) findViewById(R.id.to_login_text);
            tv.setText("Профиль");
        }

        toProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), isUserLoggedIn ? ProfileActivity.class : LoginActivity.class);
                startActivity(intent);
            }
        });

        toWishListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), isUserLoggedIn ? FavoritesActivity.class : LoginActivity.class);
                startActivity(intent);
            }
        });

        GOODS.clear();
        GOODS.add(new Item(1,"Наушники JBL2", "Крутые наушники", 1299, "Наушники", AppCompatResources.getDrawable(getApplicationContext(), R.drawable.tma2_hd_wireless)));
        GOODS.add(new Item(2,"Наушники JBL", "Крутые наушники", 1299, "Наушники", AppCompatResources.getDrawable(getApplicationContext(), R.drawable.tma2_hd_wireless)));
        RecyclerView bestsellersLv = (RecyclerView) findViewById(R.id.bestsellers_container);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        bestsellersLv.setLayoutManager(layoutManager);
        ItemAdapter itemAdapter = new ItemAdapter(GOODS);
        bestsellersLv.setAdapter(itemAdapter);

        CATEGORIES.clear();
        CATEGORIES.add(new Category("Наушники", AppCompatResources.getDrawable(getApplicationContext(), R.drawable.categories_headset), R.color.lightBlue));
        CATEGORIES.add(new Category("ПК", AppCompatResources.getDrawable(getApplicationContext(), R.drawable.categories_pc), R.color.lightYellow));
        CATEGORIES.add(new Category("Мышки", AppCompatResources.getDrawable(getApplicationContext(), R.drawable.categories_mice), R.color.lightGreen));
        CATEGORIES.add(new Category("Клавиатуры", AppCompatResources.getDrawable(getApplicationContext(), R.drawable.categories_keyboard), R.color.lightRed));
        RecyclerView categoriesRv = (RecyclerView) findViewById(R.id.categories_container);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoriesRv.setLayoutManager(layoutManager1);
        CategoryAdapter categoryAdapter = new CategoryAdapter(CATEGORIES);
        categoriesRv.setAdapter(categoryAdapter);

        NEWS.clear();
        NEWS.add(new News("JBL презентовало новую беспроводную гарнитуру", "В Москве официально презентовали линейку беспроводных гарнитур JBL Live, куда входят 4 модели на любой вкус и кошелёк.", AppCompatResources.getDrawable(getApplicationContext(), R.drawable.jbl_presentation), new Date(2025, 8, 12)));
        NEWS.add(new News("Последняя презентация Apple", "Презентация Apple 9 сентября 2025 года была посвящена выпуску новой линейки продуктов, включая смартфоны iPhone 17 и iPhone 17 Air, обновленные Apple Watch Series 11 и Apple Watch Ultra 3, а также AirPods Pro 3.", AppCompatResources.getDrawable(getApplicationContext(), R.drawable.apple_presentation), new Date(2025, 9, 11)));
        RecyclerView newsRv = (RecyclerView) findViewById(R.id.news_container);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        newsRv.setLayoutManager(layoutManager2);
        NewsAdapter newsAdapter = new NewsAdapter(NEWS);
        newsRv.setAdapter(newsAdapter);
    }
}