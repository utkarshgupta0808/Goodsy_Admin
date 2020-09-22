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
import com.goodsy.goodsyadmin.adapters.ShopItemAdapter;
import com.goodsy.goodsyadmin.models.ShopModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ShopListItemActivity extends AppCompatActivity {

    ImageView btnBack;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    ShopItemAdapter shopItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list_item);

        btnBack=findViewById(R.id.btn_back);
        recyclerView=findViewById(R.id.shop_new_item_recycler);

        firebaseFirestore= FirebaseFirestore.getInstance();

        Query query= firebaseFirestore.collection("ShopsMain").whereGreaterThan("underReviewItem",0);
        FirestoreRecyclerOptions<ShopModel> options= new FirestoreRecyclerOptions.Builder<ShopModel>()
                .setQuery(query, ShopModel.class).build();
        shopItemAdapter=new ShopItemAdapter(options);
        shopItemAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(shopItemAdapter);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopListItemActivity.this,WelcomeActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        shopItemAdapter.stopListening();


    }

    @Override
    protected void onStart() {
        super.onStart();
        shopItemAdapter.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

}