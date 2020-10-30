package com.lavish.android.practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lavish.android.practice.adapters.ProductAdapter;
import com.lavish.android.practice.adapters.ProductRecyclerAdapter;
import com.lavish.android.practice.databinding.ActivityListViewSampleBinding;
import com.lavish.android.practice.models.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListViewSampleActivity extends AppCompatActivity {

    private ActivityListViewSampleBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityListViewSampleBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        setupSimpleListView();
    }

    /**
     * ListView methods
     * Learn more here : https://www.notion.so/ListView-RecyclerView-b56c9d266d124b03b35b8e75775b6a91#26d2e7c146e341a3a231417990350d37
     */


    private void setupSimpleListView() {
        //Data : List of Strings
        List<String> cities = new ArrayList<>(
                Arrays.asList("Ahmadabad", "Jaipur", "Surat", "Bombay", "Ahmadabad", "Jaipur"
                        , "Surat", "Bombay", "Ahmadabad", "Jaipur", "Surat", "Bombay"
                        , "Surat", "Bombay", "Ahmadabad", "Jaipur", "Surat", "Bombay"
                        , "Surat", "Bombay", "Ahmadabad", "Jaipur", "Surat", "Bombay")
        );


        //Create Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this

                //Layout for each item
                , android.R.layout.simple_list_item_1

                //Data
                , cities);


        //Set adapter
        b.list.setAdapter(adapter);
    }

    private void setupCustomViewListView() {
        //Data : List of Strings
        List<String> cities = new ArrayList<>(
                Arrays.asList("Ahmadabad", "Jaipur", "Surat", "Bombay", "Ahmadabad", "Jaipur"
                        , "Surat", "Bombay", "Ahmadabad", "Jaipur", "Surat", "Bombay"
                        , "Surat", "Bombay", "Ahmadabad", "Jaipur", "Surat", "Bombay"
                        , "Surat", "Bombay", "Ahmadabad", "Jaipur", "Surat", "Bombay")
        );


        //Create Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this

                //Layout for each item
                , R.layout.list_item_with_bg

                //Id for TextView
                , R.id.text

                //Data
                , cities);


        //Set adapter
        b.list.setAdapter(adapter);
    }

    private void setupCustomAdapterListView() {
        //Data : List of products
        List<Product> products = getProducts();

        //Create adapter
        ProductAdapter adapter = new ProductAdapter(this
                , R.layout.list_item_product
                , products);

        //Set adapter to ListView
        b.list.setAdapter(adapter);
    }



    //RecyclerView methods

    private void setupRecyclerView() {
        //Create adapter
        ProductRecyclerAdapter adapter = new ProductRecyclerAdapter(this, getProducts());

        //Layout Manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        b.recyclerView.setLayoutManager(layoutManager);

        //Divider
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        b.recyclerView.addItemDecoration(itemDecor);

        //Set adapter
        b.recyclerView.setAdapter(adapter);
    }



    //Helper methods

    private ArrayList<Product> getProducts() {
        return new ArrayList<>(
                Arrays.asList(
                        new Product("Apple", 100)
                        , new Product("Pomegranate", 80)
                        , new Product("Apple", 100)
                        , new Product("Pomegranate", 80)
                        , new Product("Apple", 100)
                        , new Product("Pomegranate", 80)
                        , new Product("Apple", 100)
                        , new Product("Pomegranate", 80)
                        , new Product("Apple", 100)
                        , new Product("Pomegranate", 80)
                        , new Product("Apple", 100)
                        , new Product("Pomegranate", 80)
                        , new Product("Apple", 100)
                        , new Product("Pomegranate", 80)
                )
        );
    }
}