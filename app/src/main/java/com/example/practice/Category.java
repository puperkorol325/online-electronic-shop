package com.example.practice;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import java.util.Random;

public class Category {
    public int id;
    public String name;
    public Drawable image;
    public int color;
    public Category(String name, Drawable image, int color) {
        this.id = new Random().nextInt();
        this.name = name;
        this.image = image;
        this.color = color;
    }
}
