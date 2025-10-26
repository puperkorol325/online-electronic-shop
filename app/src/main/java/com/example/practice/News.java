package com.example.practice;

import android.graphics.drawable.Drawable;

import java.util.Date;
import java.util.Random;

public class News {
    public int id;
    public String title;
    public String text;
    public Drawable image;
    public Date date;

    public News(String title, String text, Drawable image, Date date) {
        this.id = new Random().nextInt();
        this.title = title;
        this.text = text;
        this.image = image;
        this.date = date;
    }
}
