package com.goodsy.goodsyadmin.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.goodsy.goodsyadmin.R;

public class PhotoPreviewActivity extends AppCompatActivity {

    ImageView photoPreview, btnBack;
    TextView photoDes;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);

        photoDes=findViewById(R.id.photo_des);
        photoPreview=findViewById(R.id.photo_preview);
        btnBack=findViewById(R.id.btn_back);

        bundle=getIntent().getExtras();

        Glide.with(this).load(bundle.getString("photoPreview")).into(photoPreview);
        photoDes.setText(bundle.getString("photoDes"));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}