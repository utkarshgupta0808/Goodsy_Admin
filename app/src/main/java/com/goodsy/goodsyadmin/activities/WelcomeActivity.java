package com.goodsy.goodsyadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.goodsy.goodsyadmin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.goodsy.goodsyadmin.utils.Constants.mainUsersCollection;

public class WelcomeActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    public static String adminDeviceToken;
    FirebaseFirestore firebaseFirestore;
    CardView shopList, itemList, shopAcceptedList, shopRejectedList, addDefaultImages, addDefaultItems, allDeliveryBoys, newDeliverBoys;
    ImageView btnLogout, imageViewChat, imageViewTokenUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        shopList = findViewById(R.id.shops_list);
        itemList = findViewById(R.id.items_list);
        btnLogout = findViewById(R.id.btn_logout);
        shopAcceptedList = findViewById(R.id.shops_list_accepted);
        shopRejectedList = findViewById(R.id.shops_list_rejected);
        addDefaultImages = findViewById(R.id.add_default_images);
        addDefaultItems = findViewById(R.id.add_default_items);
        imageViewChat = findViewById(R.id.chat_image);
        imageViewTokenUpdate = findViewById(R.id.update_token);
        allDeliveryBoys=findViewById(R.id.all_delivery_boys);
        newDeliverBoys=findViewById(R.id.new_delivery_boy);

        FirebaseMessaging.getInstance().subscribeToTopic("news");
        if (getIntent().hasExtra("category")) {
            if (getIntent().getStringExtra("category").equals("Item")) {
                Bundle bundle = new Bundle();
                bundle.putString("itemId", getIntent().getStringExtra("itemId"));
                bundle.putString("itemName", getIntent().getStringExtra("itemName"));
                bundle.putString("itemDescription", getIntent().getStringExtra("itemDescription"));
                bundle.putString("itemPrice", getIntent().getStringExtra("itemPrice"));
                bundle.putString("itemWeight", getIntent().getStringExtra("itemWeight"));
                bundle.putString("itemImage", getIntent().getStringExtra("itemImage"));
                Intent intent = new Intent(WelcomeActivity.this, ItemInfoActivity.class);
                intent.putExtras(bundle);
                ItemListActivity.selectedShop = getIntent().getStringExtra("shopId");
                startActivity(intent);
            }
        }

        newDeliverBoys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, NewDeliveryBoyActivity.class));
                finish();
            }
        });

        itemList.setOnClickListener(view -> {
            Intent intentItem = new Intent(WelcomeActivity.this, ShopListItemActivity.class);
            startActivity(intentItem);
            finish();
        });

        allDeliveryBoys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, DeliveryBoyListActivity.class));
                finish();
            }
        });

        imageViewChat.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatListActivity.class);
            startActivity(intent);
        });

        addDefaultImages.setOnClickListener(v -> {
            Intent intentItem = new Intent(WelcomeActivity.this, AddDefaultImageActivity.class);
            startActivity(intentItem);
        });

        addDefaultItems.setOnClickListener(v -> {
            Intent intentItem = new Intent(WelcomeActivity.this, AddDefaultItemsActivity.class);
            startActivity(intentItem);
        });

        btnLogout.setOnClickListener(view -> {

            LayoutInflater layoutInflater = LayoutInflater.from(WelcomeActivity.this);
            View view1 = layoutInflater.inflate(R.layout.alert_dialog, null);
            Button yesButton = view1.findViewById(R.id.btn_yes);
            Button cancelButton = view1.findViewById(R.id.btn_cancel);

            final AlertDialog alertDialog = new AlertDialog.Builder(WelcomeActivity.this).setView(view1).create();
            alertDialog.show();

            yesButton.setOnClickListener(view2 -> logOut());
            cancelButton.setOnClickListener(view22 -> alertDialog.dismiss());


        });

        shopList.setOnClickListener(view -> {
            Intent intentShop = new Intent(WelcomeActivity.this, ShopListActivity.class);
            startActivity(intentShop);
            finish();

        });

        shopAcceptedList.setOnClickListener(view -> {
            Intent intentShop = new Intent(WelcomeActivity.this, AcceptedShopsActivity.class);
            startActivity(intentShop);
            finish();

        });

        shopRejectedList.setOnClickListener(view -> {
            Intent intentShop = new Intent(WelcomeActivity.this, RejectedShopsActivity.class);
            startActivity(intentShop);
            finish();

        });

        imageViewTokenUpdate.setOnClickListener(v -> FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                adminDeviceToken = task1.getResult();
                firebaseFirestore.collection(mainUsersCollection).document(firebaseAuth.getCurrentUser().getUid()).update("deviceToken", adminDeviceToken);
            }
        }));

    }

    private void logOut() {

        firebaseAuth.signOut();
        Intent intentLogout = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intentLogout);
        Toast.makeText(WelcomeActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null || !currentUser.getUid().equals("08dYbJiHWBWLG3wUdswjNVQKAls1")) {
            Intent mainIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }
}