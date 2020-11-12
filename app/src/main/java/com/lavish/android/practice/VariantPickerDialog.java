package com.lavish.android.practice;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.lavish.android.practice.databinding.DialogVariantPickerBinding;
import com.lavish.android.practice.databinding.VariantItemBinding;
import com.lavish.android.practice.models.Cart;
import com.lavish.android.practice.models.CartItem;
import com.lavish.android.practice.models.Product;
import com.lavish.android.practice.models.Variant;

class VariantPickerDialog {

    private Context context;
    private Cart cart;
    private Product product;
    private DialogVariantPickerBinding b;

    public void show(Context context, Cart cart, Product product, final OnWeightPickedListener listener){
        this.context = context;
        this.cart = cart;
        this.product = product;

        b = DialogVariantPickerBinding.inflate(
                LayoutInflater.from(context)
        );

        new AlertDialog.Builder(context)
                .setTitle("Pick Variants")
                .setView(b.getRoot())
                .setPositiveButton("SELECT", null)
                .show();

        showVariants();
    }

    private void showVariants() {
        for(Variant variant : product.variants){
            //Inflate
            VariantItemBinding ib = VariantItemBinding.inflate(
                    /* Inflater */ LayoutInflater.from(context)
                    ,/* Parent */ b.getRoot()
                    , true
            );

            //Bind data
            ib.variantName.setText(variant.toString());

            setupButtons(variant, ib);
        }
    }

    private void setupButtons(final Variant variant, final VariantItemBinding ib) {
        ib.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Update qtyView
                int qty = cart.addToCart(product, variant);
                ib.qty.setText(qty + "");

                //Update buttons visibility
                if(qty == 1){
                    ib.remove.setVisibility(View.VISIBLE);
                    ib.qty.setVisibility(View.VISIBLE);
                }
            }
        });

        ib.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Update qtyView
                int qty = cart.removeFromCart(product, variant);
                ib.qty.setText(qty + "");

                //Update buttons visibility
                if(qty == 0){
                    ib.remove.setVisibility(View.GONE);
                    ib.qty.setVisibility(View.GONE);
                }
            }
        });
    }

    interface OnWeightPickedListener{
        void onWeightPicked();
        void onWeightPickerCancelled();
    }

}
