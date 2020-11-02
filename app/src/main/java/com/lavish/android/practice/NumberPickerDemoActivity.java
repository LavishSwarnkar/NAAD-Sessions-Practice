package com.lavish.android.practice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.lavish.android.practice.databinding.ActivityNumberPickerDemoBinding;
import com.lavish.android.practice.databinding.WeightPickerDialogBinding;

import androidx.appcompat.app.AppCompatActivity;

public class NumberPickerDemoActivity extends AppCompatActivity {


    private ActivityNumberPickerDemoBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = ActivityNumberPickerDemoBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        //setupNumberPicker();
    }

    private void showWeightPicker() {
        WeightPickerDialogBinding b = WeightPickerDialogBinding.inflate(getLayoutInflater());

        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title)
                .setView(b.getRoot())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(NumberPickerDemoActivity.this, "OK clicked", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(NumberPickerDemoActivity.this, "CANCEL clicked", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("WAIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(NumberPickerDemoActivity.this, "WAIT clicked", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

        setupNumberPicker(b.numberPicker);
    }

    private void setupNumberPicker(NumberPicker numberPicker) {
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(26);

        numberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return Character.toString((char) ('A' + i - 1));
                //return i + " " + ((i%2 == 0) ? "Apples" : "Oranges");
            }
        });
    }
}