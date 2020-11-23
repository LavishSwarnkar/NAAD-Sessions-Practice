package com.lavish.android.practice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;
import com.lavish.android.practice.models.Account;
import com.lavish.android.practice.models.Product;
import com.lavish.android.practice.models.Variant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FirebaseDemoActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_demo);

        setup();
        //addSimpleDocUsingMap();
        //setSimpleDocUsingMap();
        //setWithMergeDocUsingMap();
        //nestedDataAndDataTypes();
        //saveCustomObject();
        //changeCustomObject();
        //batchedWrites();
        //batchedWrites1();

        //saveAcc();

        db.collection("products")
                .get()
        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Product> products = new ArrayList<>();
                for(DocumentSnapshot doc : queryDocumentSnapshots){
                    if(doc.exists()){
                        Product product = doc.toObject(Product.class);
                        products.add(product);
                    }
                }
                
                showProducts(products);
            }
        });

        //txn();
        //txn1();
        txn1("b", "c", 100);
    }

    private void showProducts(List<Product> products) {
    }

    private void saveAcc() {
        WriteBatch batch = db.batch();

        batch.set(db.collection("accounts").document("a"), new Account("a", 50));
        batch.set(db.collection("accounts").document("b"), new Account("b", 100));
        batch.set(db.collection("accounts").document("c"), new Account("c", 200));

        batch.commit()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FirebaseDemoActivity.this, "Done!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FirebaseDemoActivity.this, "Failure!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void txn1(final String fromAcc, final String toAcc, final int amt) {
        //from -- amt --> to

        db.runTransaction(new Transaction.Function<Integer>() {
            @Nullable
            @Override
            public Integer apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                Account from = transaction.get(
                        db.collection("accounts")
                                .document(fromAcc)
                ).toObject(Account.class);

                if(from.bal < amt)
                    return -1;

                transaction.update(db.collection("accounts")
                        .document(fromAcc), "bal", FieldValue.increment(-amt));
                transaction.update(db.collection("accounts")
                        .document(toAcc), "bal", FieldValue.increment(amt));

                //Return updated bal
                return from.bal - amt;
            }
        }).addOnSuccessListener(new OnSuccessListener<Integer>() {
            @Override
            public void onSuccess(Integer bal) {
                if(bal == -1){
                    new AlertDialog.Builder(FirebaseDemoActivity.this)
                            .setTitle("Insufficient balance")
                            .setMessage("The from account has insufficient balance.")
                            .show();
                } else {
                    new AlertDialog.Builder(FirebaseDemoActivity.this)
                            .setTitle("Transaction successful")
                            .setMessage("Updated bal = " + bal)
                            .show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FirebaseDemoActivity.this, "Failure!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Bank transfer transaction (A -> B)
     *
     * S1 : Read A.bal
     * S2 : A.bal -= amt
     * S3 : Read B.bal
     * S4 : B.bal += amt
     * S5 : Commit
     */
    private void txn() {
        final int amt = 100;

        //Read A
        db.collection("ac")
                .document("a")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Account a = documentSnapshot.toObject(Account.class);

                        if(a.bal < amt){
                            return;
                        }

                        a.bal -= amt;


                        db.collection("ac")
                                .document("b")
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        Account b = documentSnapshot.toObject(Account.class);

                                        b.bal += amt;

                                        WriteBatch batch = db.batch();

                                        batch.update(db.collection("ac").document("a"), "bal", -100);
                                        batch.update(db.collection("ac").document("b"), "bal", 100);

                                        batch.commit();

                                    }
                                });
                    }
                });
    }

    private void batchedWrites1() {
        Product product = new Product("Apple")
                , product1 = new Product("Apple1")
                , product2 = new Product("Apple2");

        WriteBatch batch = db.batch();

        batch.set(db.collection("prods").document(product.name), product);
        batch.set(db.collection("prods").document(product1.name), product1);
        batch.set(db.collection("prods").document(product2.name), product2);

        batch.commit()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FirebaseDemoActivity.this, "Done!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void batchedWrites() {
        /**
         * add() -> Automatically generates new unique id (Order) (Primary key unknown)
         * set(Document ID) -> Sets/Overwrite to the doc id passed (User (phoneNo, emailId)) (PrimaryKey)
         */

        Product product = new Product("Apple")
                , product1 = new Product("Apple1")
                , product2 = new Product("Apple2");

        x = 0;

        db.collection("prods").document(product.name).set(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        x++;
                        if(x == 3){
                            //All 3 done!
                        }
                    }
                });
        db.collection("prods").document(product1.name).set(product1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        x++;
                        if(x == 3){
                            //All 3 done!
                        }
                    }
                });
        db.collection("prods").document(product2.name).set(product2)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        x++;
                        if(x == 3){
                            //All 3 done!
                        }
                    }
                });


    }

    private void changeCustomObject() {
        db.collection("products")
                .document("Apple")
                .update("pricePerKg", 200)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FirebaseDemoActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FirebaseDemoActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                });
    }

    private void saveCustomObject() {
        Product product = new Product("Apple");

        product.variants = Arrays.asList(
            new Variant("A", 5)
            , new Variant("B", 10)
        );
        db.collection("products")
                .document(product.name)
                .set(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FirebaseDemoActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FirebaseDemoActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                });
    }

    private void nestedDataAndDataTypes() {
        Map<String, Object> docData = new HashMap<>();
        docData.put("stringExample", "Hello world!");
        docData.put("booleanExample", true);
        docData.put("numberExample", 3.14159265);
        docData.put("dateExample", new Timestamp(new Date()));
        docData.put("listExample", Arrays.asList(1, 2, 3));
        docData.put("nullExample", null);

        Map<String, Object> nestedData = new HashMap<>();
        nestedData.put("a", 5);
        nestedData.put("b", true);

        docData.put("objectExample", nestedData);

        db.collection("data").document("one")
                .set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FirebaseDemoActivity.this
                                ,  "DocumentSnapshot successfully written!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FirebaseDemoActivity.this
                                ,  "Error writing document" + e, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setWithMergeDocUsingMap() {
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("sex", "F");
        user.put("dateExample", new Timestamp(new Date()));

        // Add a new document with a generated ID
        db.collection("users")
                .document("grgokrog")
                .set(user, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FirebaseDemoActivity.this
                                , "Done", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FirebaseDemoActivity.this
                                , "Error adding document", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setSimpleDocUsingMap() {
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "greg");
        user.put("last", "hrh");
        //user.put("born", 1815);

        // Add a new document with a generated ID
        db.collection("users")
                .document("ada")
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FirebaseDemoActivity.this
                                , "DocumentSnapshot added with ID: ada", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FirebaseDemoActivity.this
                                , "Error adding document", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setup() {
        db = FirebaseFirestore.getInstance();
    }

    private void addSimpleDocUsingMap() {
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(FirebaseDemoActivity.this
                                , "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FirebaseDemoActivity.this
                                , "Error adding document", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}