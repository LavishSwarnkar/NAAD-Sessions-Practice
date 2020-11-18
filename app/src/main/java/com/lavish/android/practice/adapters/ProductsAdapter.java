package com.lavish.android.practice.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lavish.android.practice.databinding.ProductItemBinding;
import com.lavish.android.practice.databinding.ProductItemSingleVbBinding;
import com.lavish.android.practice.databinding.ProductItemWbMultiVbBinding;
import com.lavish.android.practice.models.Product;
import com.lavish.android.practice.models.Variant;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

//Adapter for List of Products
public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_SINGLE_VB = 0, TYPE_WB_OR_MULTIPLE_VB = 1;

    //Needed for inflating layout
    private Context context;

    //List of data
    private List<Product> productList;

    public ProductsAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getItemViewType(int position) {
        Product product = productList.get(position);
        if(product.type == Product.WEIGHT_BASED || /* MULTIPLE VB */ product.variants.size() > 1)
            return TYPE_WB_OR_MULTIPLE_VB;

        return TYPE_SINGLE_VB;
    }

    //Inflate the view for item and create a ViewHolder object
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_SINGLE_VB){
            ProductItemSingleVbBinding b = ProductItemSingleVbBinding.inflate(
                    LayoutInflater.from(context)
                    , parent
                    , false
            );

            return new SingleVbVH(b);
        } else {
            ProductItemWbMultiVbBinding b = ProductItemWbMultiVbBinding.inflate(
                    LayoutInflater.from(context)
                    , parent
                    , false
            );

            return new MultipleVbOrWbVH(b);
        }

    }


    //Binds the data to view
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //Get the data at position
        final Product product = productList.get(position);

        if(getItemViewType(position) == TYPE_SINGLE_VB){
            SingleVbVH vh = (SingleVbVH) holder;

            vh.b.name.setText(product.name + " " + product.variants.get(0).name);
            vh.b.price.setText(getPriceText(product));
        } else {

            MultipleVbOrWbVH vh = (MultipleVbOrWbVH) holder;
            vh.b.name.setText(product.name);
            vh.b.price.setText(getPriceText(product));
        }

    }

    private String getPriceText(Product product) {
        if(product.type == Product.WEIGHT_BASED)
            return "Rs. " + product.pricePerKg + "/kg";

        return product.variants.toString()
                .replace("[", "")
                .replaceAll("]", "")
                .replace(",", ", ");
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }



    //Holds the view for each item
    public class SingleVbVH extends RecyclerView.ViewHolder{

        private ProductItemSingleVbBinding b;

        public SingleVbVH(@NonNull ProductItemSingleVbBinding b) {
            super(b.getRoot());
            this.b = b;
        }

    }

    //Holds the view for each item
    public class MultipleVbOrWbVH extends RecyclerView.ViewHolder{

        private ProductItemWbMultiVbBinding b;

        public MultipleVbOrWbVH(@NonNull ProductItemWbMultiVbBinding b) {
            super(b.getRoot());
            this.b = b;
        }

    }

}
