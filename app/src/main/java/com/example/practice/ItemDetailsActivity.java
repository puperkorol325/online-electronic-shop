package com.example.practice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.io.Serializable;

public class ItemDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_itemdetails);

        ImageView backButton = (ImageView) findViewById(R.id.get_back_to_main);
        TextView pageName = (TextView) findViewById(R.id.page_name);
        TextView itemName = (TextView) findViewById(R.id.item_name);
        TextView itemDescription = (TextView) findViewById(R.id.item_description);
        TextView itemPrice = (TextView) findViewById(R.id.item_price);
        ImageView itemPreview = (ImageView) findViewById(R.id.item_preview);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pageName.setText("О товаре");

        Intent intent = getIntent();
        Integer itemID = intent.getIntExtra("itemID", 0);
        Item item = null;
        for (int i = 0; i < MainActivity.GOODS.size(); i++) {
            if (MainActivity.GOODS.get(i).id == itemID) {
                item = MainActivity.GOODS.get(i);
                break;
            }
        }

        if (item != null) {
            itemName.setText(item.name);
            itemDescription.setText(item.description);
            itemPrice.setText(item.price + " рублей");
            itemPreview.setImageDrawable(item.preview);
        }
    }

}
