package com.lavish.android.practice.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lavish.android.practice.databinding.ListItemProductBinding;
import com.lavish.android.practice.models.Product;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class CustomAdapter extends ArrayAdapter<Product> {

    private Context context;
    private List<Product> products;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        this.context = context;
        products = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Get corresponding view for product at position

        //Get data
        Product product = products.get(position);

        //Bind the data with view
        ListItemProductBinding b = ListItemProductBinding.bind(convertView);

        b.name.setText(product.name);
        b.price.setText("Rs. " + product.price);

        //Return view
        return convertView;
    }
}
