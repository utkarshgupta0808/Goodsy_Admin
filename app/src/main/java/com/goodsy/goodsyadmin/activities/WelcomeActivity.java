package com.goodsy.goodsyadmin.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodsy.goodsyadmin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    ImageView btnLogout;
    TextView shopList, itemList, shopAcceptedList, shopRejectedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        firebaseAuth=FirebaseAuth.getInstance();
        shopList=findViewById(R.id.shops_list);
        itemList=findViewById(R.id.items_list);
        btnLogout=findViewById(R.id.btn_logout);
        shopAcceptedList=findViewById(R.id.shops_list_accepted);
        shopRejectedList=findViewById(R.id.shops_list_rejected);

        itemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentItem=new Intent(WelcomeActivity.this, ShopListItemActivity.class);
                startActivity(intentItem);
                finish();
            }
        });



        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflater=LayoutInflater.from(WelcomeActivity.this);
                View view1=layoutInflater.inflate(R.layout.alert_dialog,null);
                Button yesButton=view1.findViewById(R.id.btn_yes);
                Button cancelButton=view1.findViewById(R.id.btn_cancel);

                final AlertDialog alertDialog=new AlertDialog.Builder(WelcomeActivity.this)
                        .setView(view1)
                        .create();
                alertDialog.show();

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logOut();
                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });


            }
        });

        shopList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentShop=new Intent(WelcomeActivity.this, ShopListActivity.class);
                startActivity(intentShop);
                finish();

            }
        });

        shopAcceptedList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentShop=new Intent(WelcomeActivity.this, AcceptedShopsActivity.class);
                startActivity(intentShop);
                finish();

            }
        });

        shopRejectedList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentShop=new Intent(WelcomeActivity.this, RejectedShopsActivity.class);
                startActivity(intentShop);
                finish();

            }
        });

    }

    private void logOut() {

        firebaseAuth.signOut();
        Intent intentLogout=new Intent(WelcomeActivity.this,LoginActivity.class);
        startActivity(intentLogout);
        Toast.makeText(WelcomeActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null || !currentUser.equals("08dYbJiHWBWLG3wUdswjNVQKAls1")) {

            Intent mainIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(mainIntent);
            finish();


        }
    }
}