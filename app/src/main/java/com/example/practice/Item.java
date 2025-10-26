package com.example.practice;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Item{
    public int id;
    public String name;
    public String description;
    public double price;
    public String category;
    public Drawable preview;

    public Item(int id, String name, String description, double price, String category,Drawable preview) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.preview = preview;
    }
}
