package com.goodsy.goodsyadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.goodsy.goodsyadmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ItemInfoActivity extends AppCompatActivity {

    ImageView btnBack;
    Bundle bundle;
    TextView itemName, itemDes, itemWeight, itemPrice;
    ImageView itemImage;
    Button acceptItem, rejectItem, btnRejectReason;
    EditText rejectReason;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        btnBack=findViewById(R.id.btn_back);
        itemWeight=findViewById(R.id.item_weight);
        itemPrice=findViewById(R.id.item_price);
        itemDes=findViewById(R.id.item_description);
        itemImage=findViewById(R.id.item_image);
        itemName=findViewById(R.id.item_name);
        acceptItem=findViewById(R.id.accept_item);
        rejectItem=findViewById(R.id.reject_item);
        rejectReason=findViewById(R.id.reject_reason);
        btnRejectReason=findViewById(R.id.btn_reject_reason);

        bundle=getIntent().getExtras();
        firebaseFirestore=FirebaseFirestore.getInstance();

        itemPrice.setText(bundle.getString("itemPrice"));
        itemWeight.setText(bundle.getString("itemWeight"));
        itemDes.setText(bundle.getString("itemDescription"));
        itemName.setText(bundle.getString("itemName"));

        Glide.with(this).load(bundle.getString("itemImage")).into(itemImage);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1=new Bundle();
                bundle1.putString("photoPreview",bundle.getString("itemImage"));
                bundle1.putString("photoDes","Item Image");
                startActivity(new Intent(ItemInfoActivity.this,PhotoPreviewActivity.class).putExtras(bundle1));

            }
        });

        acceptItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final LayoutInflater layoutInflater=LayoutInflater.from(ItemInfoActivity.this);
                View view1=layoutInflater.inflate(R.layout.alert_dialog,null);
                Button yesButton=view1.findViewById(R.id.btn_yes);
                Button cancelButton=view1.findViewById(R.id.btn_cancel);

                final AlertDialog alertDialog=new AlertDialog.Builder(ItemInfoActivity.this)
                        .setView(view1)
                        .create();
                alertDialog.show();

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final long[] c = new long[1];

                        firebaseFirestore.collection("ShopsMain").document(ItemListActivity.selectedShop)
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        c[0] =documentSnapshot.getLong("underReviewItem");
                                        c[0]--;

                                        firebaseFirestore.collection("ShopsMain").document(ItemListActivity.selectedShop)
                                                .update("underReviewItem",c[0]).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(ItemInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ItemInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        documentReference=firebaseFirestore.collection("ShopsMain").document(ItemListActivity.selectedShop);
                        documentReference.collection("Items").document(Objects.requireNonNull(bundle.getString("itemId")))
                                .update("itemStatus","accept")
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) { Toast.makeText(ItemInfoActivity.this, "Item accepted", Toast.LENGTH_SHORT).show();
                                        finish();
                                        alertDialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ItemInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

        rejectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflater=LayoutInflater.from(ItemInfoActivity.this);
                View view1=layoutInflater.inflate(R.layout.alert_dialog,null);
                Button yesButton=view1.findViewById(R.id.btn_yes);
                Button cancelButton=view1.findViewById(R.id.btn_cancel);

                final AlertDialog alertDialog=new AlertDialog.Builder(ItemInfoActivity.this)
                        .setView(view1)
                        .create();
                alertDialog.show();

                yesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        rejectReason.setVisibility(View.VISIBLE);
                        btnRejectReason.setVisibility(View.VISIBLE);
                        acceptItem.setVisibility(View.INVISIBLE);
                        rejectItem.setVisibility(View.INVISIBLE);
                        alertDialog.dismiss();
                        Toast.makeText(ItemInfoActivity.this, "Enter reject reason", Toast.LENGTH_SHORT).show();


                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                btnRejectReason.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (rejectReason.getText()==null){
                            rejectReason.setError("Please fill reject reason");
                        }
                        else {

                            final long[] c = new long[1];

                            firebaseFirestore.collection("ShopsMain").document(ItemListActivity.selectedShop)
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    c[0] =documentSnapshot.getLong("underReviewItem");
                                    c[0]--;

                                    firebaseFirestore.collection("ShopsMain").document(ItemListActivity.selectedShop)
                                            .update("underReviewItem",c[0]).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ItemInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ItemInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            documentReference=firebaseFirestore.collection("ShopsMain").document(ItemListActivity.selectedShop);
                            documentReference.collection("Items").document(Objects.requireNonNull(bundle.getString("itemId")))
                                    .update("itemStatus","reject")
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(ItemInfoActivity.this, "Item rejected", Toast.LENGTH_SHORT).show();
                                            finish();
                                            alertDialog.dismiss();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ItemInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                }
                            });

                        }

                    }
                });

            }
        });

    }
}