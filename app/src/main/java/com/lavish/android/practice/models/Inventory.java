package com.lavish.android.practice.models;

import java.util.List;

public class Inventory {

    List<Product> products;

    public Inventory() {
    }

    public Inventory(List<Product> products) {
        this.products = products;
    }
}