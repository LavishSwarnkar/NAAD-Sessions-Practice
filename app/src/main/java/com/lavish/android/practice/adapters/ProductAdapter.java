package com.lavish.android.practice.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.lavish.android.practice.databinding.ListItemProductBinding;
import com.lavish.android.practice.models.Product;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProductAdapter extends ArrayAdapter<Product> {

    private final Context context;
    private final List<Product> products;

    public ProductAdapter(@NonNull Context context, int resource, @NonNull List<Product> products) {
        super(context, resource, products);

        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //View Binding class
        ListItemProductBinding b;

        //No recyclable view available
        if(convertView == null){
            //View is not inflated yet

            b = ListItemProductBinding.inflate(LayoutInflater.from(context));
            convertView = b.getRoot();
        } else {
            //View is already inflated

            b = ListItemProductBinding.bind(convertView);
        }

        //Get data item
        Product product = products.get(position);

        //Bind data to view
        b.name.setText(product.name);
        b.price.setText("₹" + product.price);

        return convertView;
    }

}
