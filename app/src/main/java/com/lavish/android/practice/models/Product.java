package com.lavish.android.practice.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {

    public static final int WEIGHT_BASED = 0, VARIANTS_BASED = 1;

    public String name;
    public int type, pricePerKg;
    public float minQty;
    public List<Variant> variants;

    public Product() {

    }

    //Extracts & sets variants from String[]
    public void fromVariantStrings(String[] vs) {
        variants = new ArrayList<>();
        for(String s : vs){
            String[] v = s.split(",");
            variants.add(new Variant(v[0], Integer.parseInt(v[1])));
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", pricePerKg=" + pricePerKg +
                ", minQty=" + minQty +
                ", variants=" + variants +
                '}';
    }
}
