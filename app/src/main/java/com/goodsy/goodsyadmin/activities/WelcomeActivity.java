package com.goodsy.goodsyadmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.goodsy.goodsyadmin.R;
import com.goodsy.goodsyadmin.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

import static com.goodsy.goodsyadmin.utils.Constants.mainUsersCollection;

public class WelcomeActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    public static String adminDeviceToken;
    FirebaseFirestore firebaseFirestore;
    RelativeLayout relativeLayoutLoading;
    LottieAnimationView lottieAnimationViewMain;
    NestedScrollView nestedScrollViewMain;
    CardView shopList, itemList, shopAcceptedList, shopRejectedList, addDefaultImages, addDefaultItems, allDeliveryBoys, newDeliverBoys;
    ImageView btnLogout, imageViewChat, imageViewTokenUpdate;
    ImageView imageViewOne, imageViewTwo, imageViewThird, imageViewFourth, imageViewFive, imageViewSix, imageViewSeven, imageViewEight, imageViewNine, imageViewTen, imageViewEleven, imageViewTwelve;

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
        allDeliveryBoys = findViewById(R.id.all_delivery_boys);
        newDeliverBoys = findViewById(R.id.new_delivery_boy);
        imageViewOne = findViewById(R.id.image_one);
        imageViewTwo = findViewById(R.id.image_second);
        imageViewThird = findViewById(R.id.image_third);
        imageViewFourth = findViewById(R.id.image_fourth);
        imageViewFive = findViewById(R.id.image_five);
        imageViewSix = findViewById(R.id.image_six);
        imageViewSeven = findViewById(R.id.image_seven);
        imageViewEight = findViewById(R.id.image_eight);
        imageViewNine = findViewById(R.id.image_nine);
        imageViewTen = findViewById(R.id.image_ten);
        imageViewEleven = findViewById(R.id.image_eleven);
        imageViewTwelve = findViewById(R.id.image_twelve);
        relativeLayoutLoading = findViewById(R.id.relative_loading);
        lottieAnimationViewMain = findViewById(R.id.loading_animation);
        nestedScrollViewMain = findViewById(R.id.nested_main);

        Glide.with(WelcomeActivity.this).load(Constants.linkVeniceGradient).placeholder(R.drawable.loading_photo).into(imageViewOne);
        Glide.with(WelcomeActivity.this).load(Constants.linkAzureLaneGradient).placeholder(R.drawable.loading_photo).into(imageViewTwo);
        Glide.with(WelcomeActivity.this).load(Constants.linkMaldivesGradient).placeholder(R.drawable.loading_photo).into(imageViewThird);
        Glide.with(WelcomeActivity.this).load(Constants.linkAnamNisarGradient).placeholder(R.drawable.loading_photo).into(imageViewFourth);
        Glide.with(WelcomeActivity.this).load(Constants.linkVeniceGradient).placeholder(R.drawable.loading_photo).into(imageViewFive);
        Glide.with(WelcomeActivity.this).load(Constants.linkSubuGradient).placeholder(R.drawable.loading_photo).into(imageViewSix);
        Glide.with(WelcomeActivity.this).load(Constants.linkInboxGradient).placeholder(R.drawable.loading_photo).into(imageViewSeven);
        Glide.with(WelcomeActivity.this).load(Constants.linkNeuromancerGradient).placeholder(R.drawable.loading_photo).into(imageViewEight);
        Glide.with(WelcomeActivity.this).load(Constants.linkAnamNisarGradient).placeholder(R.drawable.loading_photo).into(imageViewNine);
        Glide.with(WelcomeActivity.this).load(Constants.linkMoonPurpleGradient).placeholder(R.drawable.loading_photo).into(imageViewTen);
        Glide.with(WelcomeActivity.this).load(Constants.linkAzureLaneGradient).placeholder(R.drawable.loading_photo).into(imageViewEleven);
        Glide.with(WelcomeActivity.this).load(Constants.linkReefGradient).placeholder(R.drawable.loading_photo).into(imageViewTwelve);

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

        newDeliverBoys.setOnClickListener(v -> startActivity(new Intent(WelcomeActivity.this, NewDeliveryBoyActivity.class)));

        itemList.setOnClickListener(view -> startActivity(new Intent(WelcomeActivity.this, ShopListItemActivity.class)));

        allDeliveryBoys.setOnClickListener(v -> startActivity(new Intent(WelcomeActivity.this, DeliveryBoyListActivity.class)));

        imageViewChat.setOnClickListener(v -> startActivity(new Intent(this, ChatListActivity.class)));

        addDefaultImages.setOnClickListener(v -> startActivity(new Intent(WelcomeActivity.this, AddDefaultImageActivity.class)));

        addDefaultItems.setOnClickListener(v -> startActivity(new Intent(WelcomeActivity.this, AddDefaultItemsActivity.class)));

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

        shopList.setOnClickListener(view -> startActivity(new Intent(WelcomeActivity.this, ShopListActivity.class)));

        shopAcceptedList.setOnClickListener(view -> startActivity(new Intent(WelcomeActivity.this, AcceptedShopsActivity.class)));

        shopRejectedList.setOnClickListener(view -> startActivity(new Intent(WelcomeActivity.this, RejectedShopsActivity.class)));

        imageViewTokenUpdate.setOnClickListener(v -> FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                adminDeviceToken = task1.getResult();
                firebaseFirestore.collection(mainUsersCollection).document(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid()).update("deviceToken", adminDeviceToken);
            }
        }));

        nestedScrollViewMain.setVisibility(View.VISIBLE);
        lottieAnimationViewMain.cancelAnimation();
        relativeLayoutLoading.setVisibility(View.GONE);
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