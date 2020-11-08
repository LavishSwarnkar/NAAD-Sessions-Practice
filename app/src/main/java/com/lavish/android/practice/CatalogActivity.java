package com.lavish.android.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
        products = new ArrayList<>(Arrays.asList(
                new Product("Apple", 100, 1)
                , new Product("Orange", 100, 1)
                , new Product("Grapes", 100, 1)
                , new Product("Kiwi", 100, 1)
        ));

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


        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        //Meta data
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.filter(query);
                return true;

            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("MyLog", "onQueryTextSubmit : " +  query);
                return false;
            }
        });

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

    //OnClick handler for ContextualMenu of Product
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.product_edit :
                editLastSelectedItem();
                return true;

            case R.id.product_remove :
                removeLastSelectedItem();

                return true;

        }

        return super.onContextItemSelected(item);
    }

    private void editLastSelectedItem() {
        //Get data to be edited                         104
        //products = allProducts = ["Apple", "Orange", "Grapes", "Kiwi"] //Grapes index = 2
        //query : "grap"        104
        //visibleProducts = ["Grapes1"] //Grapes index = 0
        //104

        Product lastSelectedProduct = adapter.visibleProducts.get(adapter.lastSelectedItemPosition);

        //Show Editor Dialog
        new ProductEditorDialog()
                .show(this, lastSelectedProduct, new ProductEditorDialog.OnProductEditedListener() {
                    @Override
                    public void onProductEdited(Product product) {
                        //Update view
                        adapter.notifyItemChanged(adapter.lastSelectedItemPosition);
                    }

                    @Override
                    public void onCancelled() {
                        Toast.makeText(CatalogActivity.this, "Cancelled!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void removeLastSelectedItem() {
        new AlertDialog.Builder(this)
                .setTitle("Do you really want to remove this product?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        products.remove(adapter.lastSelectedItemPosition);
                        adapter.notifyItemRemoved(adapter.lastSelectedItemPosition);

                        Toast.makeText(CatalogActivity.this, "Removed", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();
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