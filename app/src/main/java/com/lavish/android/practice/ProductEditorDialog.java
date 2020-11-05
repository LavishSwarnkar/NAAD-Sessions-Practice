package com.lavish.android.practice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lavish.android.practice.databinding.DialogProductEditBinding;
import com.lavish.android.practice.models.Product;

import java.util.regex.Pattern;

import static com.lavish.android.practice.models.Product.VARIANTS_BASED;
import static com.lavish.android.practice.models.Product.WEIGHT_BASED;

class ProductEditorDialog {

    private DialogProductEditBinding b;
    private Product product;


    void show(final Context context, final Product product, final OnProductEditedListener listener){

        //Inflate
        b = DialogProductEditBinding.inflate(
                LayoutInflater.from(context)
        );
        this.product = product;

        //Create dialog
        new AlertDialog.Builder(context)
                .setTitle("Edit Product")
                .setView(b.getRoot())
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(areProductDetailsValid())
                            listener.onProductEdited(product);
                        else {
                            Toast.makeText(context, "Invalid details!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onCancelled();
                    }
                })
                .show();

        setupRadioGroup();
    }


    //Checks if all variant details are valid
    private boolean areProductDetailsValid() {
        //Check name
        String name = b.name.getText().toString().trim();
        if(name.isEmpty())
            return false;

        product.name = name;

        switch (b.productType.getCheckedRadioButtonId()){
            case R.id.weight_based_rbtn :

                //Set type
                product.type = WEIGHT_BASED;

                //Get values from views
                String pricePerKg = b.price.getText().toString().trim()
                        , minQty = b.minQty.getText().toString().trim();

                //Check inputs
                if(pricePerKg.isEmpty() || minQty.isEmpty() || !minQty.matches("\\d+(kg|g)"))
                    return false;

                //All good, set values of product
                product.pricePerKg = Integer.parseInt(pricePerKg);
                product.minQty = extractMinQtyFromString(minQty);

                return true;
            case R.id.variants_based_rbtn :

                //Set type
                product.type = VARIANTS_BASED;

                //Get value from view
                String variants = b.variants.getText().toString().trim();

                return areVariantsValid(variants);
        }

        return false;
    }


    //Checks for valid Variants input and extracts Variants from it
    private boolean areVariantsValid(String variants) {
        if(variants.length() == 0)
            return true;

        String[] vs = variants.split("\n");
        Pattern pattern = Pattern.compile("^\\w+,\\d+$");
        for(int i=0 ; i<vs.length ; i++){
            String variant = vs[i];
            if(!pattern.matcher(variant).matches()){
                return false;
            }
        }

        //Extracts Variants from String[]
        product.fromVariantStrings(vs);

        return true;
    }


    //Returns weight in float from strings like "100kg" or "500g"
    private float extractMinQtyFromString(String minQty) {
        if(minQty.contains("kg"))
            return Integer.parseInt(minQty.replace("kg", ""));
        else
            return Integer.parseInt(minQty.replace("g", "")) / 1000f;
    }


    //Change visibility of views based on ProductType Selection
    private void setupRadioGroup() {
        b.productType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.weight_based_rbtn){
                    b.weightBasedRoot.setVisibility(View.VISIBLE);
                    b.variantsRoot.setVisibility(View.GONE);
                } else {
                    b.variantsRoot.setVisibility(View.VISIBLE);
                    b.weightBasedRoot.setVisibility(View.GONE);
                }
            }
        });
    }


    //Listener Interface to notify Activity of Dialog events
    interface OnProductEditedListener{
        void onProductEdited(Product product);
        void onCancelled();
    }

}
