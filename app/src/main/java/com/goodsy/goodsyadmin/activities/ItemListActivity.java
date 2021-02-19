package com.goodsy.goodsyadmin.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.goodsy.goodsyadmin.R;
import com.goodsy.goodsyadmin.adapters.ItemAdapter;
import com.goodsy.goodsyadmin.models.ItemModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

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

        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference = firebaseFirestore.collection("ShopsMain").document(Objects.requireNonNull(bundle.getString("shopId")));

        Query query = documentReference.collection("Items").whereEqualTo("itemStatus", "under");
        FirestoreRecyclerOptions<ItemModel> options = new FirestoreRecyclerOptions.Builder<ItemModel>()
                .setQuery(query, ItemModel.class).build();
        itemAdapter = new ItemAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ItemListActivity.this));
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();
        itemAdapter.startListening();

        btnBack.setOnClickListener(view -> onBackPressed());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        itemAdapter.stopListening();
    }

}