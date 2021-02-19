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
import com.goodsy.goodsyadmin.adapters.ShopItemAdapter;
import com.goodsy.goodsyadmin.models.ShopModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ShopListItemActivity extends AppCompatActivity {

    ImageView btnBack;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    ShopItemAdapter shopItemAdapter;
    RelativeLayout relativeLayoutLoading;
    LottieAnimationView lottieAnimationViewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list_item);

        btnBack = findViewById(R.id.btn_back);
        recyclerView = findViewById(R.id.shop_new_item_recycler);
        relativeLayoutLoading = findViewById(R.id.relative_loading);
        lottieAnimationViewMain = findViewById(R.id.loading_animation);

        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("ShopsMain").whereGreaterThan("underReviewItem", 0);
        FirestoreRecyclerOptions<ShopModel> options = new FirestoreRecyclerOptions.Builder<ShopModel>().setQuery(query, ShopModel.class).build();
        shopItemAdapter = new ShopItemAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(shopItemAdapter);
        shopItemAdapter.notifyDataSetChanged();
        shopItemAdapter.startListening();

        lottieAnimationViewMain.cancelAnimation();
        relativeLayoutLoading.setVisibility(View.GONE);

        btnBack.setOnClickListener(view -> onBackPressed());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shopItemAdapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}