package com.goodsy.goodsyadmin.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.goodsy.goodsyadmin.R;

public class OrderPerShopActivity extends AppCompatActivity {


    RelativeLayout relativeLayoutLoading;
    LottieAnimationView lottieAnimationViewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_per_shop);


        relativeLayoutLoading = findViewById(R.id.relative_loading);
        lottieAnimationViewMain = findViewById(R.id.loading_animation);

        lottieAnimationViewMain.cancelAnimation();
        relativeLayoutLoading.setVisibility(View.GONE);
    }
}