package com.lavish.android.practice.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lavish.android.practice.databinding.ListItemProductBinding;
import com.lavish.android.practice.models.Product;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder> {

    private final Context context;
    private final List<Product> products;

    public ProductRecyclerAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemProductBinding b = ListItemProductBinding.inflate(LayoutInflater.from(context)
                , parent
                , false);

        return new ViewHolder(b);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get data item
        Product product = products.get(position);

        //Bind data to view
        holder.b.name.setText(product.name);
        holder.b.price.setText("₹" + product.price);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ListItemProductBinding b;

        public ViewHolder(ListItemProductBinding b) {
            super(b.getRoot());
            this.b = b;
        }
    }

}
