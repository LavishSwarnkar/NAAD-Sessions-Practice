package com.lavish.android.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lavish.android.practice.databinding.ActivityCatalogBinding;
import com.lavish.android.practice.models.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    private ActivityCatalogBinding b;
    private ArrayList<Product> products;
    private ProductsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityCatalogBinding.inflate(getLayoutInflater());

        setContentView(b.getRoot());

        setupProductsList();
    }

    private void setupProductsList() {
        //Create DataSet
        products = new ArrayList<>();

        //Create adapter object
        adapter = new ProductsAdapter(this, products);

        //Set the adapter & LayoutManager to RV
        b.recyclerView.setAdapter(adapter);
        b.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        b.recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        );
    }



    //OPTIONS MENU

    //Inflates the option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_catalog_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //OnItem Click Listener for Options Menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_item){
            showProductEditorDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    private void showProductEditorDialog() {
        new ProductEditorDialog()
                .show(this, new Product(), new ProductEditorDialog.OnProductEditedListener() {
                    @Override
                    public void onProductEdited(Product product) {
                        products.add(product);
                        adapter.notifyItemInserted(products.size() - 1);
                    }

                    @Override
                    public void onCancelled() {
                        Toast.makeText(CatalogActivity.this, "Cancelled!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}