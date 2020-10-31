package com.goodsy.goodsyadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.goodsy.goodsyadmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
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

    private RequestQueue mRequestQue;
    private String URL = "https://fcm.googleapis.com/fcm/send";


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
                                    public void onSuccess(Void aVoid) {
                                        sendNotification();
                                        Toast.makeText(ItemInfoActivity.this, "Item accepted", Toast.LENGTH_SHORT).show();
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

    private void sendNotification() {

        JSONObject json = new JSONObject();
        try {
            json.put("to","/topics/"+ItemListActivity.selectedShop);
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title","Item Accepted");
            notificationObj.put("body",bundle.getString("itemName"));

            JSONObject extraData = new JSONObject();
            extraData.put("category","Item Accepted");
            extraData.put("shopId", ItemListActivity.selectedShop);

            json.put("notification",notificationObj);
            json.put("data",extraData);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("MUR", "onResponse: ");
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("MUR", "onError: "+error.networkResponse);
                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAASbIZ3Jw:APA91bH_PDeA9GdG_2JzSDn9ENYBrnykKk66CzQSqsn9G_9QMIcgvd0rmNwUH5pYsj4Fug-71sy_vqdlwB01f02JyKIB_a-_I6CpRLVUxNX3_nk2tVWErtUzg9jh_oCLKUEW2FUNUUOU");
                    return header;
                }
            };
            mRequestQue.add(request);
        }
        catch (JSONException e)

        {
            e.printStackTrace();
        }


    }

}