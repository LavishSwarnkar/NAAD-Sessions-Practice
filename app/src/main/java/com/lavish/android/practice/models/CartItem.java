package com.lavish.android.practice.models;

public class CartItem {

    public String name;
    public int qty, price;

    public CartItem(String name, int price) {
        this.name = name;
        this.price = price;
        qty = 1;
    }
}
