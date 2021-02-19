package com.goodsy.goodsyadmin.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.goodsy.goodsyadmin.R;
import com.goodsy.goodsyadmin.adapters.DeliveryBoyAdapter;
import com.goodsy.goodsyadmin.models.DeliveryBoyModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DeliveryBoyListActivity extends AppCompatActivity {

    ImageView btnBack;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    DeliveryBoyAdapter deliveryBoyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy_list);

        btnBack=findViewById(R.id.btn_back);
        recyclerView=findViewById(R.id.delivery_boy_recycler);

        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("DeliveryBoy").whereEqualTo("applicationStatus", "accept");
        FirestoreRecyclerOptions<DeliveryBoyModel> options = new FirestoreRecyclerOptions.Builder<DeliveryBoyModel>()
                .setQuery(query, DeliveryBoyModel.class).build();
        deliveryBoyAdapter = new DeliveryBoyAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(deliveryBoyAdapter);
        deliveryBoyAdapter.notifyDataSetChanged();
        deliveryBoyAdapter.startListening();

        btnBack.setOnClickListener(view -> onBackPressed());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deliveryBoyAdapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}