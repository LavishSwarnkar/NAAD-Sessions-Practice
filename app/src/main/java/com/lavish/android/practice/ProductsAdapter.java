package com.lavish.android.practice;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lavish.android.practice.databinding.VariantBasedProductBinding;
import com.lavish.android.practice.databinding.WeightBasedProductBinding;
import com.lavish.android.practice.models.Product;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

//Adapter for List of Products
class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Context -> Activity -> CatalogActivity

    //Needed for inflating layout
    private Context context;

    //List of data
    private List<Product> productList;

    int lastSelectedItemPosition;

    public ProductsAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    //Inflate the view for item and create a ViewHolder object based on viewType
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == Product.WEIGHT_BASED){
            //Inflate WeightBasedProduct layout
            WeightBasedProductBinding b = WeightBasedProductBinding.inflate(
                    LayoutInflater.from(context)
                    , parent
                    , false
            );

            //Create & Return WeightBasedProductVH
            //Child -> Parent
            return new WeightBasedProductVH(b);
        } else {
            //Inflate VariantsBasedProduct layout
            VariantBasedProductBinding b = VariantBasedProductBinding.inflate(
                    LayoutInflater.from(context)
                    , parent
                    , false
            );

            //Create & Return VariantsBasedProductVH
            return new VariantsBasedProductVH(b);
        }
    }

    //Return ViewType based on position
    @Override
    public int getItemViewType(int position) {
        return productList.get(position).type;
    }



    //Binds the data to view
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        //Get the data at position
        final Product product = productList.get(position);

        if(product.type == Product.WEIGHT_BASED){

            //Get binding
            //Parent -> Child
            WeightBasedProductVH vh = (WeightBasedProductVH) holder;
            WeightBasedProductBinding b = vh.b;

            //Bind data
            b.name.setText(product.name);
            b.pricePerKg.setText("Rs. " + product.pricePerKg);
            b.minQty.setText("MinQty - " + product.minQty + "kg");

            //Setup Contextual Menu inflation
            setupContextMenu(b.getRoot());

        } else {

            //Get binding
            VariantBasedProductBinding b = ((VariantsBasedProductVH) holder).b;

            //Bind data
            b.name.setText(product.name);
            b.variants.setText(product.variantsString());

            //Setup Contextual Menu inflation
            setupContextMenu(b.getRoot());

        }

        //Save dynamic position of selected item to access it in Activity
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                lastSelectedItemPosition = holder.getAdapterPosition();
                return false;
            }
        });

    }

    private void setupContextMenu(ConstraintLayout root) {
        root.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                if(! (context instanceof CatalogActivity))
                    return;

                ((CatalogActivity) context)
                        .getMenuInflater().inflate(R.menu.product_contextual_menu, contextMenu);
            }
        });
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }



    //ViewHolder for WeightBasedProduct
    public static class WeightBasedProductVH extends RecyclerView.ViewHolder{

        WeightBasedProductBinding b;

        public WeightBasedProductVH(@NonNull WeightBasedProductBinding b) {
            super(b.getRoot());
            this.b = b;
        }
    }

    //ViewHolder for VariantsBasedProduct
    public static class VariantsBasedProductVH extends RecyclerView.ViewHolder{

        VariantBasedProductBinding b;

        public VariantsBasedProductVH(@NonNull VariantBasedProductBinding b) {
            super(b.getRoot());
            this.b = b;
        }
    }

}
