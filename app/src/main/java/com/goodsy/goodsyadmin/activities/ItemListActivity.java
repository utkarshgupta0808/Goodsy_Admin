package com.goodsy.goodsyadmin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.goodsy.goodsyadmin.R;
import com.goodsy.goodsyadmin.adapters.ItemAdapter;
import com.goodsy.goodsyadmin.models.ItemModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ItemListActivity extends AppCompatActivity {

    ImageView btnBack;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    ItemAdapter itemAdapter;
    DocumentReference documentReference;
    Bundle bundle;
    public static String selectedShop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        btnBack=findViewById(R.id.btn_back);
        recyclerView=findViewById(R.id.item_recycler);
        bundle=getIntent().getExtras();

        selectedShop=bundle.getString("shopId");

        firebaseFirestore=FirebaseFirestore.getInstance();
        documentReference=firebaseFirestore.collection("ShopsMain").document(Objects.requireNonNull(bundle.getString("shopId")));

        Query query= documentReference.collection("Items").whereEqualTo("itemStatus","under");
        FirestoreRecyclerOptions<ItemModel> options= new FirestoreRecyclerOptions.Builder<ItemModel>()
                .setQuery(query, ItemModel.class).build();
        itemAdapter=new ItemAdapter(options);
        itemAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ItemListActivity.this));
        recyclerView.setAdapter(itemAdapter);


//        /Query query= firebaseFirestore.collection("ShopsMain").whereEqualTo("applicationStatus","under");



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(ItemListActivity.this, ShopListItemActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
    @Override
    protected void onStop() {
        super.onStop();
        itemAdapter.stopListening();


    }

    @Override
    protected void onStart() {
        super.onStart();
        itemAdapter.startListening();
    }

}