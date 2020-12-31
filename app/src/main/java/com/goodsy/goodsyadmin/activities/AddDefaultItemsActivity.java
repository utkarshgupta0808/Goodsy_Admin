package com.goodsy.goodsyadmin.activities;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.goodsy.goodsyadmin.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class AddDefaultItemsActivity extends AppCompatActivity {

    ImageView imageViewBack;
    TextInputEditText editTextCategory;
    MaterialButton buttonAdd;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ArrayList<String> itemsCategories = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_default_items);

        imageViewBack = findViewById(R.id.back_image);
        buttonAdd = findViewById(R.id.btn_add);
        editTextCategory = findViewById(R.id.edit_item_category_name);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        getItemCategories();

        buttonAdd.setOnClickListener(v -> {
            if (itemsCategories.size() > 0)
                addItemCategory();
            else
                Toast.makeText(this, "Please wait and try again...", Toast.LENGTH_SHORT).show();
        });
    }

    private void addItemCategory() {
        String itemCategory = Objects.requireNonNull(editTextCategory.getText()).toString().trim();
        String finalItemCategory = itemCategory.substring(0, 1).toUpperCase() + itemCategory.substring(1);
        String itemCategoryCheck = Objects.requireNonNull(editTextCategory.getText()).toString().trim().toLowerCase();
        if (itemsCategories.contains(itemCategoryCheck)) {
            Toast.makeText(this, "Sorry, item category already exists...", Toast.LENGTH_SHORT).show();
        } else {
            firebaseFirestore.collection("ItemsCategory").document("autoItemsCategory").update("category", FieldValue.arrayUnion(finalItemCategory)).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    editTextCategory.setText("");
                    editTextCategory.clearFocus();
                    Toast.makeText(this, "Item category added successfully", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getItemCategories() {
        firebaseFirestore.collection("ItemsCategory").document("autoItemsCategory").addSnapshotListener((value, error) -> {
            if (error != null) {
                Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
                return;
            }
            if (value != null && value.exists()) {
                itemsCategories = (ArrayList<String>) value.get("category");
                assert itemsCategories != null;
                itemsCategories.replaceAll(String::toLowerCase);
                Collections.sort(itemsCategories);
                Toast.makeText(this, "data: " + itemsCategories, Toast.LENGTH_SHORT).show();
            }
        });
//        firebaseFirestore.collection("ItemsCategory").document("autoItemsCategory").get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                DocumentSnapshot documentSnapshot = task.getResult();
//                assert documentSnapshot != null;
//                itemsCategories = (ArrayList<String>) documentSnapshot.get("category");
//                assert itemsCategories != null;
//                itemsCategories.replaceAll(String::toLowerCase);
//                Collections.sort(itemsCategories);
//                Toast.makeText(this, "data: " + itemsCategories, Toast.LENGTH_SHORT).show();
//            }
//        });
    }
//    public static void replace(Set<String> strings)
//    {
//        String[] stringsArray = strings.toArray(new String[0]);
//        for (int i=0; i<stringsArray.length; ++i)
//        {
//            stringsArray[i] = stringsArray[i].toLowerCase();
//        }
//        strings.clear();
//        strings.addAll(Arrays.asList(stringsArray));
//    }
}