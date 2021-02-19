package com.goodsy.goodsyadmin.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.goodsy.goodsyadmin.R;
import com.goodsy.goodsyadmin.adapters.ShopAdapter;
import com.goodsy.goodsyadmin.models.ShopModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ShopListActivity extends AppCompatActivity {

    ImageView btnBack;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    ShopAdapter shopAdapter;
    RelativeLayout relativeLayoutLoading;
    LottieAnimationView lottieAnimationViewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        btnBack = findViewById(R.id.btn_back);
        recyclerView = findViewById(R.id.shop_recycler);
        relativeLayoutLoading = findViewById(R.id.relative_loading);
        lottieAnimationViewMain = findViewById(R.id.loading_animation);

        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("ShopsMain").whereEqualTo("applicationStatus", "under");
        FirestoreRecyclerOptions<ShopModel> options = new FirestoreRecyclerOptions.Builder<ShopModel>()
                .setQuery(query, ShopModel.class).build();
        shopAdapter = new ShopAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(shopAdapter);
        shopAdapter.notifyDataSetChanged();
        shopAdapter.startListening();

        lottieAnimationViewMain.cancelAnimation();
        relativeLayoutLoading.setVisibility(View.GONE);

        btnBack.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shopAdapter.stopListening();
    }

}