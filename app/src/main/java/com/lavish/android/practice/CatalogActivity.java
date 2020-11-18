package com.lavish.android.practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.lavish.android.practice.adapters.ProductsAdapter;
import com.lavish.android.practice.databinding.ActivityCatalogBinding;
import com.lavish.android.practice.models.Cart;
import com.lavish.android.practice.models.Product;
import com.lavish.android.practice.models.Variant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    private ActivityCatalogBinding b;
    private Cart cart = new Cart();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityCatalogBinding.inflate(getLayoutInflater());

        setContentView(b.getRoot());


        setupProductsList();
    }

    private void setupProductsList() {
        //Create DataSet
        List<Product> products = getProducts();

        //Create adapter object
        ProductsAdapter adapter = new ProductsAdapter(this, products, cart);

        //Set the adapter & LayoutManager to RV
        b.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Divider
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        b.recyclerView.addItemDecoration(itemDecor);

        b.recyclerView.setAdapter(adapter);
    }

    private List<Product> getProducts() {
        return Arrays.asList(
                new Product("Bread", Arrays.asList(
                        new Variant("big", 10)
                        , new Variant("small", 20)
                        , new Variant("medium", 30)
                ))
                , new Product("Apple", 30, 1)
                , new Product("Kiwi", Arrays.asList(
                        new Variant("1kg", 100)
                ))
        );
    }

    public void updateCartSummary(){
        if(cart.noOfItems == 0){
            b.checkout.setVisibility(View.GONE);
        } else {
            b.checkout.setVisibility(View.VISIBLE);

            b.cartSummary.setText("Total : Rs. " + cart.subTotal + "\n" + cart.noOfItems + " items");
        }
    }

}