package com.lavish.android.practice;

import android.os.Bundle;

import com.lavish.android.practice.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    private ActivityMainBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate view
        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
    }

}