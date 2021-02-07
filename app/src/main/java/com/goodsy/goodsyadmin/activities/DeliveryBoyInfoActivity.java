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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class DeliveryBoyInfoActivity extends AppCompatActivity {

    ImageView btnBack;
    Bundle bundle;
    TextView delName, delId, dailyCount, totalDeliveries, delLongitude, delLatitude, delNumber, delCity, delEmail, totalReviews, delRating;
    ImageView aadhaarFront, aadhaarBack, panCard, dl, profilePic, marksheet, rc, accDetails, cheque;
    Button acceptShop, rejectShop;

    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy_info);
        btnBack=findViewById(R.id.btn_back);
        delId=findViewById(R.id.del_boy_id);
        dailyCount=findViewById(R.id.daily_counts);
        totalDeliveries=findViewById(R.id.total_deliveries);
        totalReviews=findViewById(R.id.total_reviews);
        profilePic=findViewById(R.id.del_profile_pic);
        delLongitude=findViewById(R.id.longitude);
        delLatitude=findViewById(R.id.latitude);
        delName=findViewById(R.id.del_boy_name);
        delCity=findViewById(R.id.del_boy_city);
        delEmail=findViewById(R.id.del_boy_mail);
        delNumber=findViewById(R.id.del_boy_number);
        aadhaarBack=findViewById(R.id.aadhaar_back);
        aadhaarFront=findViewById(R.id.aadhaar_front);
        dl=findViewById(R.id.dl);
        panCard=findViewById(R.id.pan_card);
        marksheet=findViewById(R.id.marksheet);
        rc=findViewById(R.id.rc);
        accDetails=findViewById(R.id.acc_details);
        delRating=findViewById(R.id.del_boy_rating);
        cheque=findViewById(R.id.cheque);
        acceptShop=findViewById(R.id.accept_shop);
        rejectShop=findViewById(R.id.reject_shop);



        bundle=getIntent().getExtras();
        Toast.makeText(this, bundle.getString("docId")+"jwjkd", Toast.LENGTH_SHORT).show();
        firebaseFirestore=FirebaseFirestore.getInstance();

        if (Objects.equals(bundle.getString("applicationStatus"), "accept") || Objects.equals(bundle.getString("applicationStatus"), "reject")){
            acceptShop.setVisibility(View.GONE);
            rejectShop.setVisibility(View.GONE);
//            Toast.makeText(this, bundle.getString("applicationStatus"), Toast.LENGTH_SHORT).show();
        }

//        Toast.makeText(this, bundle.getString("applicationStatus")+"outside", Toast.LENGTH_SHORT).show();
//        assert bundle != null;
//        shopName.setText(bundle.getString("shopName"));
//        shopLatitude.setText(bundle.getString("shopLatitude"));
//        shopLongitude.setText(bundle.getString("shopLongitude"));
//        shopDes.setText(bundle.getString("shopDes"));
//        shopCategory.setText(bundle.getString("shopCategory"));
//        shopAddress.setText(bundle.getString("shopAddress"));
//        ownerNumber.setText(bundle.getString("ownerPhone"));
//        ownerName.setText(bundle.getString("ownerName"));
//        ownerEmail.setText(bundle.getString("ownerEmail"));
//        ownerCity.setText(bundle.getString("ownerAddress"));
//
//        Glide.with(this).load(bundle.getString("shopImage")).into(shopImage);
//        Glide.with(this).load(bundle.getString("panCard")).into(panCard);
//        Glide.with(this).load(bundle.getString("aadhaarBack")).into(aadhaarBack);
//        Glide.with(this).load(bundle.getString("aadhaarFront")).into(aadhaarFront);
//        Glide.with(this).load(bundle.getString("gstCertificate")).into(gstCertificate);

        firebaseFirestore.collection("DeliveryBoy").document(bundle.getString("docId")).
                get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                delName.setText(documentSnapshot.getString("delName"));
                delCity.setText(documentSnapshot.getString("delCity"));
                delEmail.setText(documentSnapshot.getString("delEmail"));
                delId.setText(documentSnapshot.getString("delId"));
                delLatitude.setText(""+documentSnapshot.get("latitude"));
                delLongitude.setText(""+documentSnapshot.get("longitude"));
                delNumber.setText(documentSnapshot.getString("delNumber"));
                totalDeliveries.setText(""+documentSnapshot.get("totalDeliveries"));
                totalReviews.setText(""+documentSnapshot.get("totalReviews"));
                dailyCount.setText(""+documentSnapshot.get("dailyCounts"));
                delRating.setText(documentSnapshot.getString("delRating"));

                Glide.with(DeliveryBoyInfoActivity.this).load(documentSnapshot.getString("profilePic")).into(profilePic);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        documentReference=firebaseFirestore.collection("DeliveryBoy").document(bundle.getString("docId"));
        documentReference.collection("Documents").document("docs").
                get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Glide.with(DeliveryBoyInfoActivity.this).load(documentSnapshot.getString("rc")).into(rc);
                Glide.with(DeliveryBoyInfoActivity.this).load(documentSnapshot.getString("acc_details")).into(accDetails);
                Glide.with(DeliveryBoyInfoActivity.this).load(documentSnapshot.getString("aadhaarFront")).into(aadhaarFront);
                Glide.with(DeliveryBoyInfoActivity.this).load(documentSnapshot.getString("aadhaarBack")).into(aadhaarBack);
                Glide.with(DeliveryBoyInfoActivity.this).load(documentSnapshot.getString("cancelledCheque")).into(cheque);
                Glide.with(DeliveryBoyInfoActivity.this).load(documentSnapshot.getString("panCard")).into(panCard);
                Glide.with(DeliveryBoyInfoActivity.this).load(documentSnapshot.getString("dl")).into(dl);
                Glide.with(DeliveryBoyInfoActivity.this).load(documentSnapshot.getString("marksheet")).into(marksheet);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1=new Bundle();
                bundle1.putString("photoPreview",bundle.getString("shopImage"));
                bundle1.putString("photoDes","Shop Image");
                startActivity(new Intent(DeliveryBoyInfoActivity.this,PhotoPreviewActivity.class).putExtras(bundle1));

            }
        });

        aadhaarFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1=new Bundle();
                bundle1.putString("photoPreview",bundle.getString("aadhaarFront"));
                bundle1.putString("photoDes","Aadhaar Card Front Side");
                startActivity(new Intent(DeliveryBoyInfoActivity.this,PhotoPreviewActivity.class).putExtras(bundle1));

            }
        });
        aadhaarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1=new Bundle();
                bundle1.putString("photoPreview",bundle.getString("aadhaarBack"));
                bundle1.putString("photoDes","Aadhaar Card Back Side");
                startActivity(new Intent(DeliveryBoyInfoActivity.this,PhotoPreviewActivity.class).putExtras(bundle1));

            }
        });
        panCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1=new Bundle();
                bundle1.putString("photoPreview",bundle.getString("panCard"));
                bundle1.putString("photoDes","Pan Card");
                startActivity(new Intent(DeliveryBoyInfoActivity.this,PhotoPreviewActivity.class).putExtras(bundle1));

            }
        });
        dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1=new Bundle();
                bundle1.putString("photoPreview",bundle.getString("gstCertificate"));
                bundle1.putString("photoDes","Gst Certificate");
                startActivity(new Intent(DeliveryBoyInfoActivity.this,PhotoPreviewActivity.class).putExtras(bundle1));

            }
        });

        acceptShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflater=LayoutInflater.from(DeliveryBoyInfoActivity.this);
                View view1=layoutInflater.inflate(R.layout.alert_dialog,null);
                Button yesButton=view1.findViewById(R.id.btn_yes);
                Button cancelButton=view1.findViewById(R.id.btn_cancel);

                final AlertDialog alertDialog=new AlertDialog.Builder(DeliveryBoyInfoActivity.this)
                        .setView(view1)
                        .create();
                alertDialog.show();

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        firebaseFirestore.collection("DeliveryBoy").document(bundle.getString("docId"))
                                .update("applicationStatus","accept")
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(DeliveryBoyInfoActivity.this, "Shop accepted in DeliveryBoy collection", Toast.LENGTH_SHORT).show();
                                        finish();
                                        alertDialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DeliveryBoyInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
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

                LayoutInflater layoutInflater=LayoutInflater.from(DeliveryBoyInfoActivity.this);
                View view1=layoutInflater.inflate(R.layout.alert_dialog,null);
                Button yesButton=view1.findViewById(R.id.btn_yes);
                Button cancelButton=view1.findViewById(R.id.btn_cancel);

                final AlertDialog alertDialog=new AlertDialog.Builder(DeliveryBoyInfoActivity.this)
                        .setView(view1)
                        .create();
                alertDialog.show();

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        firebaseFirestore.collection("DeliveryBoy").document(bundle.getString("docId"))
                                .update("applicationStatus","reject")
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(DeliveryBoyInfoActivity.this, "Shop rejected in DeliveryBoy collection", Toast.LENGTH_SHORT).show();
                                        finish();
                                        alertDialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DeliveryBoyInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
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