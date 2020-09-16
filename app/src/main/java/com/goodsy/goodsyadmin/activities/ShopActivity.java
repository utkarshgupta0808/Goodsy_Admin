package com.goodsy.goodsyadmin.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.goodsy.goodsyadmin.R;

public class ShopActivity extends AppCompatActivity {

    ImageView btnBack;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        btnBack=findViewById(R.id.btn_back);
        recyclerView=findViewById(R.id.shop_recycler);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopActivity.this,WelcomeActivity.class));
                finish();
            }
        });
    }
}