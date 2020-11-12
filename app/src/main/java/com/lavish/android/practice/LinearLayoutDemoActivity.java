package com.lavish.android.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lavish.android.practice.databinding.ActivityLinearLayoutDemoBinding;

public class LinearLayoutDemoActivity extends AppCompatActivity {

    private ActivityLinearLayoutDemoBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityLinearLayoutDemoBinding.inflate(getLayoutInflater());

        showVariants();
    }

    private void showVariants() {

    }
}