package com.goodsy.goodsyadmin.activities;

import androidx.annotation.NonNull;
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

import com.bumptech.glide.Glide;
import com.goodsy.goodsyadmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class ShopInfoActivity extends AppCompatActivity {

    ImageView btnBack;
    Bundle bundle;
    TextView shopName, shopDes, shopCategory, shopAddress, shopLongitude, shopLatitude, ownerName, ownerNumber, ownerCity, ownerEmail;
    ImageView aadhaarFront, aadhaarBack, panCard, gstCertificate, shopImage;
    Button acceptShop, rejectShop;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;

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
        acceptShop=findViewById(R.id.accept_shop);
        rejectShop=findViewById(R.id.reject_shop);


        bundle=getIntent().getExtras();
        firebaseFirestore=FirebaseFirestore.getInstance();

        if (bundle.getString("applicationStatus").equals("accept") || bundle.getString("applicationStatus").equals("reject")){
            acceptShop.setVisibility(View.INVISIBLE);
            rejectShop.setVisibility(View.INVISIBLE);
//            Toast.makeText(this, bundle.getString("applicationStatus"), Toast.LENGTH_SHORT).show();
        }

//        Toast.makeText(this, bundle.getString("applicationStatus")+"outside", Toast.LENGTH_SHORT).show();
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

        acceptShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflater=LayoutInflater.from(ShopInfoActivity.this);
                View view1=layoutInflater.inflate(R.layout.alert_dialog,null);
                Button yesButton=view1.findViewById(R.id.btn_yes);
                Button cancelButton=view1.findViewById(R.id.btn_cancel);

                final AlertDialog alertDialog=new AlertDialog.Builder(ShopInfoActivity.this)
                        .setView(view1)
                        .create();
                alertDialog.show();

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                                firebaseFirestore.collection("ShopsMain").document(Objects.requireNonNull(bundle.getString("shopId")))
                                        .update("applicationStatus","accept")
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(ShopInfoActivity.this, "Shop accepted in ShopsMain collection", Toast.LENGTH_SHORT).show();
                                                finish();
                                                alertDialog.dismiss();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ShopInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        alertDialog.dismiss();
                                    }
                                });


                        documentReference=firebaseFirestore.collection("ShopKeeper").document(Objects.requireNonNull(bundle.getString("shopKeeperId")));
                        documentReference.collection("Shops").document(Objects.requireNonNull(bundle.getString("shopId")))
                                .update("applicationStatus","accept")
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(ShopInfoActivity.this, "Shop accepted in ShopKeeper collection", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(ShopInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });


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

        rejectShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflater=LayoutInflater.from(ShopInfoActivity.this);
                View view1=layoutInflater.inflate(R.layout.alert_dialog,null);
                Button yesButton=view1.findViewById(R.id.btn_yes);
                Button cancelButton=view1.findViewById(R.id.btn_cancel);

                final AlertDialog alertDialog=new AlertDialog.Builder(ShopInfoActivity.this)
                        .setView(view1)
                        .create();
                alertDialog.show();

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                                firebaseFirestore.collection("ShopsMain").document(Objects.requireNonNull(bundle.getString("shopId")))
                                        .update("applicationStatus","reject")
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(ShopInfoActivity.this, "Shop rejected in ShopsMain collection", Toast.LENGTH_SHORT).show();
                                                finish();
                                                alertDialog.dismiss();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ShopInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        alertDialog.dismiss();
                                    }
                                });

                        documentReference=firebaseFirestore.collection("ShopKeeper").document(bundle.getString("shopKeeperId"));
                        documentReference.collection("Shops").document(Objects.requireNonNull(bundle.getString("shopId")))
                                .update("applicationStatus","reject")
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(ShopInfoActivity.this, "Shop rejected in ShopKeeper collection", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(ShopInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });


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


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}