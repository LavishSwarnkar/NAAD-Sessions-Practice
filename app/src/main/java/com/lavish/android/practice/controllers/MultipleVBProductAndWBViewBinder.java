package com.lavish.android.practice.controllers;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.lavish.android.practice.CatalogActivity;
import com.lavish.android.practice.databinding.ProductItemSingleVbBinding;
import com.lavish.android.practice.databinding.ProductItemWbMultiVbBinding;
import com.lavish.android.practice.dialogs.VariantPickerDialog;
import com.lavish.android.practice.dialogs.WeightPickerDialog;
import com.lavish.android.practice.models.Cart;
import com.lavish.android.practice.models.Product;

import java.util.ConcurrentModificationException;

public class MultipleVBProductAndWBViewBinder {

    private ProductItemWbMultiVbBinding b;
    private Product product;
    private Cart cart;

    public void bind(ProductItemWbMultiVbBinding b, Product product, Cart cart){
        this.b = b;
        this.product = product;
        this.cart = cart;

        b.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog();
            }
        });

        b.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog();
            }
        });
    }

    private void showEditDialog() {
        if(product.type == Product.WEIGHT_BASED){
            showWeightPickerDialog();
        } else {
            showVariantPickerDialog();
        }
    }



    private void showWeightPickerDialog() {
        Context context = b.getRoot().getContext();

        new WeightPickerDialog()
                .show(context, cart, product, new WeightPickerDialog.OnWeightPickedListener() {
                    @Override
                    public void onWeightPicked(int kg, int g) {
                        updateQty(kg + "kg " + g + "g");
                    }

                    @Override
                    public void onRemoved() {
                        hideQty();
                    }
                });
    }



    private void showVariantPickerDialog() {
        Context context = b.getRoot().getContext();

        new VariantPickerDialog()
                .show(context, cart, product, new VariantPickerDialog.OnVariantPickedListener() {
                    @Override
                    public void onQtyUpdated(int qty) {
                        updateQty(qty + "");
                    }

                    @Override
                    public void onRemoved() {
                        hideQty();
                    }
                });
    }



    //Utils

    private void hideQty() {
        b.qtyGroup.setVisibility(View.GONE);
        b.addBtn.setVisibility(View.VISIBLE);

        updateCheckoutSummary();
    }

    private void updateQty(String qtyString) {
        b.qtyGroup.setVisibility(View.VISIBLE);
        b.addBtn.setVisibility(View.GONE);

        b.quantity.setText(qtyString);
        updateCheckoutSummary();
    }

    private void updateCheckoutSummary() {
        Context context = b.getRoot().getContext();
        if(context instanceof CatalogActivity){
            ((CatalogActivity) context).updateCartSummary();
        } else {
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

}
