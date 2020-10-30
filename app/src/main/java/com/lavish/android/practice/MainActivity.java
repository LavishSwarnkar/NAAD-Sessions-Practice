package com.lavish.android.practice;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.lavish.android.practice.adapters.ProductAdapter;
import com.lavish.android.practice.adapters.ProductRecyclerAdapter;
import com.lavish.android.practice.adapters.WordListAdapter;
import com.lavish.android.practice.databinding.ActivityMainBinding;
import com.lavish.android.practice.models.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity{

    private ActivityMainBinding b;
    private WordListAdapter adapter;
    private List<String> words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate view
        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        createInitialData();
        setupAdapter();
        setupFAB();
    }

    private void createInitialData() {
        words = new ArrayList<>();
        for (int i = 0; i < 20; i++)
            words.add("Word " + i);
    }

    private void setupAdapter() {
        //Create adapter object
        adapter = new WordListAdapter(this, words);

        //Setup RecyclerView
        b.recyclerView.setAdapter(adapter);
        b.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupFAB() {
        b.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create new word
                String newWord = "+ Word" + words.size();

                //Add the word to list
                words.add(newWord);

                //Notify adapter
                adapter.notifyItemInserted(words.size() - 1);

                //Scroll to bottom
                b.recyclerView.smoothScrollToPosition(words.size() - 1);
            }
        });
    }

}