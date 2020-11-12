package com.lavish.android.practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.lavish.android.practice.adapters.ProductsAdapter;
import com.lavish.android.practice.databinding.ActivityCatalogBinding;
import com.lavish.android.practice.models.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    /**
     * 1. Create layout for each item
     * 2. Add RecyclerView to the layout
     * 3. Create Adapter for RV
     * 4. Initialize the adapter
     * 5. Set the adapter to RV
     */

    private ActivityCatalogBinding b;

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
        ProductsAdapter adapter = new ProductsAdapter(this, products);

        //Set the adapter & LayoutManager to RV
        b.recyclerView.setAdapter(adapter);
        b.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Product> getProducts() {
        return new ArrayList<>();
    }

}