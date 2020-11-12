package com.lavish.android.practice.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {

    //String - NameOfCartItem
    public Map<String, CartItem> map = new HashMap<>();
    public Map<String, Integer> totalVariantsQtyMap = new HashMap<>();

    /**
     * totalVariantsQtyMap :
     * Rice 2
     * (1kg, 5kg)
     *
     * map :
     * Rice 1kg - 2
     * Rice 5kg - 0
     */

    public int addToCart(Product product, Variant variant){
        //Unique key
        String fullName = product.name + " " + variant.name;

        //Update qty
        if(map.containsKey(fullName)){

            //Item already exists
            map.get(fullName).qty++;

        } else {

            //Add item to cart
            map.put(fullName
                    , new CartItem(fullName, variant.price));
        }

        //Update totalQty
        if(totalVariantsQtyMap.containsKey(product.name)){
            int totalQty = totalVariantsQtyMap.get(product.name);
            totalQty++;
            totalVariantsQtyMap.put(product.name, totalQty);
        } else {
            totalVariantsQtyMap.put(product.name, 1);
        }

        return map.get(fullName).qty;
    }

    public int removeFromCart(Product product, Variant variant){
        //Unique key
        String fullName = product.name + " " + variant.name;

        //Update qty
        map.get(fullName).qty--;

        //Check for removal
        int qty = map.get(fullName).qty;
        if(qty == 0)
            map.remove(fullName);

        //Update totalQty
        int totalQty = totalVariantsQtyMap.get(product.name);
        totalQty--;
        if(totalQty == 0)
            totalVariantsQtyMap.remove(product.name);
        else
            totalVariantsQtyMap.put(product.name, totalQty);

        return qty;
    }

}
