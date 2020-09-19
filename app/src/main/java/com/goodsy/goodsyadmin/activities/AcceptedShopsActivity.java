package com.goodsy.goodsyadmin.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.goodsy.goodsyadmin.R;
import com.goodsy.goodsyadmin.adapters.ShopAdapter;
import com.goodsy.goodsyadmin.models.ShopModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AcceptedShopsActivity extends AppCompatActivity {

    ImageView btnBack;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    ShopAdapter shopAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_shops);
        btnBack=findViewById(R.id.btn_back);
        recyclerView=findViewById(R.id.shop__accepted_recycler);

        firebaseFirestore= FirebaseFirestore.getInstance();

        Query query= firebaseFirestore.collection("ShopsMain").whereEqualTo("applicationStatus","accept");
        FirestoreRecyclerOptions<ShopModel> options= new FirestoreRecyclerOptions.Builder<ShopModel>()
                .setQuery(query, ShopModel.class).build();
        shopAdapter=new ShopAdapter(options);
        shopAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(shopAdapter);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AcceptedShopsActivity.this,WelcomeActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        shopAdapter.stopListening();


    }

    @Override
    protected void onStart() {
        super.onStart();
        shopAdapter.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,WelcomeActivity.class);
        finish();
        startActivity(intent);
    }

}