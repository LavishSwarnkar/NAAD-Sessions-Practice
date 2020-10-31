package com.lavish.android.practice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lavish.android.practice.databinding.ProductItemBinding;
import com.lavish.android.practice.models.Product;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//Adapter for List of Products
class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    //Needed for inflating layout
    private Context context;

    //List of data
    private List<Product> productList;

    public ProductsAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    //Inflate the view for item and create a ViewHolder object
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //1. Inflate the layout for product_item.xml
        ProductItemBinding b = ProductItemBinding.inflate(LayoutInflater.from(context)
                , parent
                , false);

        //2. Create ViewHolder object & return
        return new ViewHolder(b);
    }


    //Binds the data to view
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //Get the data at position
        final Product product = productList.get(position);

        //Bind the data
        //Name & Price
        holder.b.name.setText(String.format("%s (Rs. %d)", product.name, product.price));

        //Quantity
        holder.b.quantity.setText(product.qty + "");

        //DecrementButton & Quantity TV Visibility
        holder.b.decrementBtn.setVisibility(product.qty > 0 ? View.VISIBLE : View.GONE);
        holder.b.quantity.setVisibility(product.qty > 0 ? View.VISIBLE : View.GONE);

        //Configure buttons
        holder.b.incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.qty++;
                notifyItemChanged(position);
            }
        });

        holder.b.decrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.qty--;
                notifyItemChanged(position);
            }
        });
    }



    @Override
    public int getItemCount() {
        return productList.size();
    }



    //Holds the view for each item
    public class ViewHolder extends RecyclerView.ViewHolder{

        private ProductItemBinding b;

        public ViewHolder(@NonNull ProductItemBinding b) {
            super(b.getRoot());
            this.b = b;
        }

    }

}
