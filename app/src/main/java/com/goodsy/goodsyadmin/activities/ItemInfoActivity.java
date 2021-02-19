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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.goodsy.goodsyadmin.R;
import com.google.firebase.firestore.FieldValue;
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
    private final String URL = "https://fcm.googleapis.com/fcm/send";
    private RequestQueue mRequestQue;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        btnBack = findViewById(R.id.btn_back);
        itemWeight = findViewById(R.id.item_weight);
        itemPrice = findViewById(R.id.item_price);
        itemDes = findViewById(R.id.item_description);
        itemImage = findViewById(R.id.item_image);
        itemName = findViewById(R.id.item_name);
        acceptItem = findViewById(R.id.accept_item);
        rejectItem = findViewById(R.id.reject_item);
        rejectReason = findViewById(R.id.reject_reason);
        btnRejectReason = findViewById(R.id.btn_reject_reason);

        bundle = getIntent().getExtras();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mRequestQue = Volley.newRequestQueue(this);

        itemPrice.setText(bundle.getString("itemPrice"));
        itemWeight.setText(bundle.getString("itemWeight"));
        itemDes.setText(bundle.getString("itemDescription"));
        itemName.setText(bundle.getString("itemName"));

        Glide.with(this).load(bundle.getString("itemImage")).into(itemImage);

        firebaseFirestore.collection("ShopsMain").document(ItemListActivity.selectedShop).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                token = Objects.requireNonNull(task.getResult()).getString("deviceToken");
            }
        });

        btnBack.setOnClickListener(view -> finish());

        itemImage.setOnClickListener(view -> {
            Bundle bundle1 = new Bundle();
            bundle1.putString("photoPreview", bundle.getString("itemImage"));
            bundle1.putString("photoDes", "Item Image");
            startActivity(new Intent(ItemInfoActivity.this, PhotoPreviewActivity.class).putExtras(bundle1));
        });

        acceptItem.setOnClickListener(view -> {
            final LayoutInflater layoutInflater = LayoutInflater.from(ItemInfoActivity.this);
            View view1 = layoutInflater.inflate(R.layout.alert_dialog, null);
            Button yesButton = view1.findViewById(R.id.btn_yes);
            Button cancelButton = view1.findViewById(R.id.btn_cancel);

            final AlertDialog alertDialog = new AlertDialog.Builder(ItemInfoActivity.this).setView(view1).create();
            alertDialog.show();

            yesButton.setOnClickListener(view25 -> firebaseFirestore.collection("ShopsMain").document(ItemListActivity.selectedShop).collection("Items").document(Objects.requireNonNull(bundle.getString("itemId"))).update("itemStatus", "accept", "underReview", false).addOnSuccessListener(aVoid ->
                    firebaseFirestore.collection("ShopsMain").document(ItemListActivity.selectedShop).update("underReviewItem", FieldValue.increment(-1), "itemsCount", FieldValue.increment(1)).addOnSuccessListener(aVoid1 -> {
                        sendNotification();
                        Toast.makeText(ItemInfoActivity.this, "Item accepted", Toast.LENGTH_SHORT).show();
                        finish();
                        alertDialog.dismiss();
                    }).addOnFailureListener(e -> Toast.makeText(ItemInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show())).addOnFailureListener(e -> {
                Toast.makeText(ItemInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }));
            cancelButton.setOnClickListener(view2 -> alertDialog.dismiss());
        });

        rejectItem.setOnClickListener(view -> {
            LayoutInflater layoutInflater = LayoutInflater.from(ItemInfoActivity.this);
            View view1 = layoutInflater.inflate(R.layout.alert_dialog, null);
            Button yesButton = view1.findViewById(R.id.btn_yes);
            Button cancelButton = view1.findViewById(R.id.btn_cancel);

            final AlertDialog alertDialog = new AlertDialog.Builder(ItemInfoActivity.this).setView(view1).create();
            alertDialog.show();

            yesButton.setOnClickListener(view24 -> {
                rejectReason.setVisibility(View.VISIBLE);
                btnRejectReason.setVisibility(View.VISIBLE);
                acceptItem.setVisibility(View.INVISIBLE);
                rejectItem.setVisibility(View.INVISIBLE);
                alertDialog.dismiss();
                Toast.makeText(ItemInfoActivity.this, "Enter reject reason", Toast.LENGTH_SHORT).show();
            });

            cancelButton.setOnClickListener(view23 -> alertDialog.dismiss());

            btnRejectReason.setOnClickListener(view22 -> {
                if (rejectReason.getText().toString().trim().isEmpty()) {
                    rejectReason.setError("Please fill reject reason");
                } else {
                    firebaseFirestore.collection("ShopsMain").document(ItemListActivity.selectedShop).collection("Items").document(Objects.requireNonNull(bundle.getString("itemId"))).update("itemStatus", "reject", "rejectReason", rejectReason.getText().toString().trim()).addOnSuccessListener(aVoid ->
                            firebaseFirestore.collection("ShopsMain").document(ItemListActivity.selectedShop).update("underReviewItem", FieldValue.increment(-1)).addOnSuccessListener(aVoid1 -> {
                                Toast.makeText(ItemInfoActivity.this, "Item rejected", Toast.LENGTH_SHORT).show();
                                finish();
                                alertDialog.dismiss();
                            }).addOnFailureListener(e -> Toast.makeText(ItemInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show())).addOnFailureListener(e -> {
                        Toast.makeText(ItemInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    });
                }
            });
        });
    }

    private void sendNotification() {

        JSONObject object = new JSONObject();
        JSONObject innerObject = new JSONObject();
        try {
            innerObject.put("message", bundle.getString("itemName") + " is added to the items list");
            innerObject.put("shopId", ItemListActivity.selectedShop);
            innerObject.put("notiType", "Accept");
            innerObject.put("title", "Item Accepted");
            innerObject.put("itemImage", bundle.getString("itemImage"));
            object.put("data", innerObject);
            object.put("to", token);
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, URL, object, response -> {
            }, Throwable::printStackTrace) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> map = new HashMap<>();
                    map.put("authorization", "key=AAAASbIZ3Jw:APA91bH_PDeA9GdG_2JzSDn9ENYBrnykKk66CzQSqsn9G_9QMIcgvd0rmNwUH5pYsj4Fug-71sy_vqdlwB01f02JyKIB_a-_I6CpRLVUxNX3_nk2tVWErtUzg9jh_oCLKUEW2FUNUUOU");
                    map.put("Content-Type", "application/json");
                    return map;
                }
            };
            mRequestQue.add(objectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}