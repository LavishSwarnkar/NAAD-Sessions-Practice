package com.lavish.android.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import com.lavish.android.practice.databinding.ActivityContextualMenuDemoBinding;

public class ContextualMenuDemoActivity extends AppCompatActivity {

    private ActivityContextualMenuDemoBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityContextualMenuDemoBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        //Approach 1
        //Pass view which when clicked, should open the ContextualMenu
        registerForContextMenu(b.textView);

        //Approach 2
        b.textView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                getMenuInflater().inflate(R.menu.contextual_menu_demo, contextMenu);
            }
        });
    }

    //Inflate the contextual menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.contextual_menu_demo, menu);
    }

    //OnClick handler for ContextualMenu
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        String text = b.textView.getText().toString();

        switch (item.getItemId()){
            case R.id.uppercase :
                b.textView.setText(text.toUpperCase());
                return true;

            case R.id.lowercase :
                b.textView.setText(text.toLowerCase());
                return true;

        }

        return super.onContextItemSelected(item);
    }
}