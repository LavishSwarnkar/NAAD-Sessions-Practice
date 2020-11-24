package com.lavish.android.userecom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.lavish.android.userecom.adapters.ProductsAdapter;
import com.lavish.android.userecom.databinding.ActivityCatalogBinding;
import com.lavish.android.userecom.models.Cart;
import com.lavish.android.userecom.models.Product;
import com.lavish.android.userecom.models.Variant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    private ActivityCatalogBinding b;
    private Cart cart = new Cart();
    private MyApp app;
    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityCatalogBinding.inflate(getLayoutInflater());

        setContentView(b.getRoot());

        app = (MyApp) getApplicationContext();
        loadData();
        setupCheckout();
    }

    private void loadData() {
        app.showLoadingDialog(this);
        app.db.collection("inventory")
                .document("products")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists() && documentSnapshot.contains("products"))
                            products = (List<Product>) documentSnapshot.get("products");
                        else
                            products = new ArrayList<>();
                        app.hideLoadingDialog();
                        setupProductsList();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        app.hideLoadingDialog();
                        app.showToast(CatalogActivity.this, e.getMessage());
                        e.printStackTrace();
                    }
                });
    }

    private void setupCheckout() {
        b.checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(CatalogActivity.this)
                        .setTitle("Cart Summary")
                        .setMessage(cart.map.toString())
                        .show();
            }
        });
    }

    private void setupProductsList() {
        //Create adapter object
        ProductsAdapter adapter = new ProductsAdapter(this, products, cart);

        //Set the adapter & LayoutManager to RV
        b.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Divider
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        b.recyclerView.addItemDecoration(itemDecor);

        b.recyclerView.setAdapter(adapter);
    }

    public void updateCartSummary(){
        if(cart.noOfItems == 0){
            b.checkout.setVisibility(View.GONE);
        } else {
            b.checkout.setVisibility(View.VISIBLE);

            b.cartSummary.setText("Total : Rs. " + cart.subTotal + "\n" + cart.noOfItems + " items");
        }
    }

}