package com.lavish.android.practice;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.appcompat.app.AlertDialog;

public class WeightPicker {

    public void show(Context context, String minQty, final OnWeightPickedListener listener){
        WeightPickerDialogBinding b = WeightPickerDialogBinding.inflate(
                LayoutInflater.from(context)
        );

        new AlertDialog.Builder(context)
                .setTitle("Pick Weight")
                .setView(b.getRoot())
                .setPositiveButton("SELECT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Replace 0s & assign kg & g values from respective NumberPickers
//                        int kg = 0, g = 0;
                        int kg = b.numberPickerKg.getValue();
                        int g = b.numberPickerG.getValue();

                        // Add GuardCode to prevent user from selecting 0kg 0g. If so, then return
                        if(kg == 0 && g == 0) {
                            return;
                        }

                        listener.onWeightPicked(kg, g);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onWeightPickerCancelled();
                    }
                })
                .show();

        setupNumberPickers(b.numberPickerKg, b.numberPickerG, minQty);
    }

    private void setupNumberPickers(NumberPicker numberPickerKg, NumberPicker numberPickerG, String minQty) {
        // Define this method to setup kg & g NumberPickers as per the given ranges
        //kg Range - 0kg to 10kg
        //g Range - 0g to 950g
        Float quantity = Float.parseFloat(minQty) * 1000;
        numberPickerKg.setMinValue((int) (quantity / 1000));
        numberPickerKg.setMaxValue(10);
        numberPickerG.setMinValue(((int) (quantity % 1000)) / 50);
        numberPickerG.setMaxValue(19);

        numberPickerKg.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return value + " " + "kg";
            }
        });

        numberPickerG.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return (value * 50) + " " + "g";
            }
        });

        View firstItemKg = numberPickerKg.getChildAt(0);
        if (firstItemKg != null) {
            firstItemKg.setVisibility(View.INVISIBLE);
        }

        View firstItemG = numberPickerG.getChildAt(0);
        if (firstItemG != null) {
            firstItemG.setVisibility(View.INVISIBLE);
        }

    }

    interface OnWeightPickedListener{
        void onWeightPicked(int kg, int g);
        void onWeightPickerCancelled();
    }

}
