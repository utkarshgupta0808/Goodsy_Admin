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
import com.goodsy.goodsyadmin.adapters.DeliveryBoyAdapter;
import com.goodsy.goodsyadmin.models.DeliveryBoyModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class NewDeliveryBoyActivity extends AppCompatActivity {

    ImageView btnBack;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    DeliveryBoyAdapter deliveryBoyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_delivery_boy);


        btnBack=findViewById(R.id.btn_back);
        recyclerView=findViewById(R.id.new_delivery_boy_recycler);

        firebaseFirestore= FirebaseFirestore.getInstance();

        Query query= firebaseFirestore.collection("DeliveryBoy").whereEqualTo("applicationStatus", "under");
        FirestoreRecyclerOptions<DeliveryBoyModel> options= new FirestoreRecyclerOptions.Builder<DeliveryBoyModel>()
                .setQuery(query, DeliveryBoyModel.class).build();
        deliveryBoyAdapter=new DeliveryBoyAdapter(options);
        deliveryBoyAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(deliveryBoyAdapter);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewDeliveryBoyActivity.this,WelcomeActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        deliveryBoyAdapter.stopListening();


    }

    @Override
    protected void onStart() {
        super.onStart();
        deliveryBoyAdapter.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,WelcomeActivity.class);
        finish();
        startActivity(intent);
    }
}