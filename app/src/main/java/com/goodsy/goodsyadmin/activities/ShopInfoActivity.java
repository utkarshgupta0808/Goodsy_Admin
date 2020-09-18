package com.goodsy.goodsyadmin.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goodsy.goodsyadmin.R;

public class ShopInfoActivity extends AppCompatActivity {

    ImageView btnBack;
    Bundle bundle;
    TextView shopName, shopDes, shopCategory, shopAddress, shopLongitude, shopLatitude, ownerName, ownerNumber, ownerCity, ownerEmail;
    ImageView aadhaarFront, aadhaarBack, panCard, gstCertificate, shopImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);



        btnBack=findViewById(R.id.btn_back);
        shopAddress=findViewById(R.id.shop_address);
        shopCategory=findViewById(R.id.shop_category);
        shopDes=findViewById(R.id.shop_des);
        shopImage=findViewById(R.id.shop_image);
        shopLongitude=findViewById(R.id.longitude);
        shopLatitude=findViewById(R.id.latitude);
        shopName=findViewById(R.id.shop_name);
        ownerCity=findViewById(R.id.owner_city);
        ownerEmail=findViewById(R.id.owner_mail);
        ownerName=findViewById(R.id.owner_n);
        ownerNumber=findViewById(R.id.owner_number);
        aadhaarBack=findViewById(R.id.aadhaar_back);
        aadhaarFront=findViewById(R.id.aadhaar_front);
        gstCertificate=findViewById(R.id.gst_certificate);
        panCard=findViewById(R.id.pan_card);


        bundle=getIntent().getExtras();

        assert bundle != null;
        shopName.setText(bundle.getString("shopName"));
        shopLatitude.setText(bundle.getString("shopLatitude"));
        shopLongitude.setText(bundle.getString("shopLongitude"));
        shopDes.setText(bundle.getString("shopDes"));
        shopCategory.setText(bundle.getString("shopCategory"));
        shopAddress.setText(bundle.getString("shopAddress"));
        ownerNumber.setText(bundle.getString("ownerPhone"));
        ownerName.setText(bundle.getString("ownerName"));
        ownerEmail.setText(bundle.getString("ownerEmail"));
        ownerCity.setText(bundle.getString("ownerAddress"));

        Glide.with(this).load(bundle.getString("shopImage")).into(shopImage);
        Glide.with(this).load(bundle.getString("panCard")).into(panCard);
        Glide.with(this).load(bundle.getString("aadhaarBack")).into(aadhaarBack);
        Glide.with(this).load(bundle.getString("aadhaarFront")).into(aadhaarFront);
        Glide.with(this).load(bundle.getString("gstCertificate")).into(gstCertificate);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        shopImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1=new Bundle();
                bundle1.putString("photoPreview",bundle.getString("shopImage"));
                bundle1.putString("photoDes","Shop Image");
                startActivity(new Intent(ShopInfoActivity.this,PhotoPreviewActivity.class).putExtras(bundle1));

            }
        });

        aadhaarFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1=new Bundle();
                bundle1.putString("photoPreview",bundle.getString("aadhaarFront"));
                bundle1.putString("photoDes","Aadhaar Card Front Side");
                startActivity(new Intent(ShopInfoActivity.this,PhotoPreviewActivity.class).putExtras(bundle1));

            }
        });
        aadhaarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1=new Bundle();
                bundle1.putString("photoPreview",bundle.getString("aadhaarBack"));
                bundle1.putString("photoDes","Aadhaar Card Back Side");
                startActivity(new Intent(ShopInfoActivity.this,PhotoPreviewActivity.class).putExtras(bundle1));

            }
        });
        panCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1=new Bundle();
                bundle1.putString("photoPreview",bundle.getString("panCard"));
                bundle1.putString("photoDes","Pan Card");
                startActivity(new Intent(ShopInfoActivity.this,PhotoPreviewActivity.class).putExtras(bundle1));

            }
        });
        gstCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1=new Bundle();
                bundle1.putString("photoPreview",bundle.getString("gstCertificate"));
                bundle1.putString("photoDes","Gst Certificate");
                startActivity(new Intent(ShopInfoActivity.this,PhotoPreviewActivity.class).putExtras(bundle1));

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}