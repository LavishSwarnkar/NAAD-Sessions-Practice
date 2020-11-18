package com.lavish.android.practice.controllers;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.lavish.android.practice.CatalogActivity;
import com.lavish.android.practice.databinding.ProductItemSingleVbBinding;
import com.lavish.android.practice.models.Cart;
import com.lavish.android.practice.models.Product;

public class SingleVBProductViewBinder {

    private ProductItemSingleVbBinding b;

    public void bind(ProductItemSingleVbBinding b, final Product product, final Cart cart){
        this.b = b;

        b.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.addToCart(product, product.variants.get(0));

                updateQtyViews(1);
            }
        });

        b.incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = cart.addToCart(product, product.variants.get(0));

                updateQtyViews(qty);
            }
        });

        b.decrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = cart.removeFromCart(product, product.variants.get(0));

                updateQtyViews(qty);
            }
        });
    }

    private void updateCheckoutSummary() {
        Context context = b.getRoot().getContext();
        if(context instanceof CatalogActivity){
            ((CatalogActivity) context).updateCartSummary();
        } else {
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateQtyViews(int qty) {
        if(qty == 1){
            b.addBtn.setVisibility(View.GONE);
            b.qtyGroup.setVisibility(View.VISIBLE);
        } else if(qty == 0){
            b.addBtn.setVisibility(View.VISIBLE);
            b.qtyGroup.setVisibility(View.GONE);
        }

        b.quantity.setText(qty + "");
        updateCheckoutSummary();
    }

}
