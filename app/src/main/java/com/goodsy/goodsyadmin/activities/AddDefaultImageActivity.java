package com.goodsy.goodsyadmin.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.goodsy.goodsyadmin.R;
import com.goodsy.goodsyadmin.adapters.CategoryAdapter;
import com.goodsy.goodsyadmin.models.CategoryModel;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class AddDefaultImageActivity extends AppCompatActivity {

    MaterialCardView materialCardViewAdd, materialCardViewShow;
    ImageView imageViewShowImage, imageViewBack;
    Uri mainImageURI;
    boolean isChanged = false;
    Button buttonUpload;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    AlertDialog progressDialog;
    Spinner spinnerItemCategory;
    String selectedItemCategory;
    ArrayList<CategoryModel> unitModelArrayList;
    ArrayList<String> spinnerData = new ArrayList<>();
    private Bitmap compressedImageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_default_image);

        materialCardViewAdd = findViewById(R.id.cardAddItemPhoto);
        materialCardViewShow = findViewById(R.id.cardShowItemPhoto);
        imageViewShowImage = findViewById(R.id.image_item);
        buttonUpload = findViewById(R.id.btn_upload);
        spinnerItemCategory = findViewById(R.id.item_image_category);
        imageViewBack = findViewById(R.id.back_image);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        materialCardViewAdd.setOnClickListener(v -> {
//            checkStoragePermission()
            if (!selectedItemCategory.equals("Select item category"))
                checkStoragePermission();
            else
                Toast.makeText(this, "Select category first", Toast.LENGTH_SHORT).show();
        });


        buttonUpload.setOnClickListener(v -> uploadImage());

        imageViewBack.setOnClickListener(v -> onBackPressed());

        setData();
        CategoryAdapter unitAdapter = new CategoryAdapter(this, unitModelArrayList);
        spinnerItemCategory.setAdapter(unitAdapter);

        spinnerItemCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategoryModel categoryModel = (CategoryModel) parent.getItemAtPosition(position);
                selectedItemCategory = categoryModel.getItemCategory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setData() {
        unitModelArrayList = new ArrayList<>();
        unitModelArrayList.add(new CategoryModel("Select item category"));

        firebaseFirestore.collection("ItemsCategory").document("autoItemsCategory").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot documentSnapshot = task.getResult();
                assert documentSnapshot != null;
                spinnerData = (ArrayList<String>) documentSnapshot.get("category");
                assert spinnerData != null;
                Collections.sort(spinnerData);
                for (int i = 0; i < Objects.requireNonNull(spinnerData).size(); i++) {
                    unitModelArrayList.add(new CategoryModel(spinnerData.get(i)));

                }
                Toast.makeText(this, "data: " + spinnerData, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void uploadImage() {
        showProgress(this);
        if (mainImageURI != null) {
            File newImageFile = new File(Objects.requireNonNull(mainImageURI.getPath()));
            try {
                compressedImageFile = new Compressor(this)
                        .setMaxHeight(300)
                        .setMaxWidth(300)
                        .setQuality(80)
                        .compressToBitmap(newImageFile);

            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] thumbData = byteArrayOutputStream.toByteArray();

            UploadTask image_path = storageReference.child("DefaultImages/").child("defaultItem/").child("CustomImages").child(selectedItemCategory + "_" + Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid() + "_" + UUID.randomUUID() + ".jpg").putBytes(thumbData);

            image_path.addOnSuccessListener(taskSnapshot -> {
                Task<Uri> task = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
                task.addOnSuccessListener(uri -> {
                    String file = uri.toString();
                    storeImage(file);
                });
            });
        } else {
            hideProgressDialog();
            Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show();
        }
    }

    private void storeImage(String file) {

        firebaseFirestore.collection("DefaultImages").document("defaultItemImages").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot documentSnapshot = task.getResult();
                assert documentSnapshot != null;
                if (documentSnapshot.exists()) {
                    if (documentSnapshot.get(selectedItemCategory) != null) {
                        firebaseFirestore.collection("DefaultImages").document("defaultItemImages").update(selectedItemCategory, FieldValue.arrayUnion(file)).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                hideProgressDialog();
                                finish();
                                Toast.makeText(this, "Add successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                hideProgressDialog();
                                Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        HashMap<String, Object> imageMap = new HashMap<>();
                        ArrayList<String> imageList = new ArrayList<>();
                        imageList.add(file);
                        imageMap.put(selectedItemCategory, imageList);
                        firebaseFirestore.collection("DefaultImages").document("defaultItemImages").update(imageMap).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                hideProgressDialog();
                                finish();
                                imageList.clear();
                                Toast.makeText(this, "Add successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                hideProgressDialog();
                                Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(AddDefaultImageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddDefaultImageActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                bringImagePicker();
            }
        } else {
            bringImagePicker();
        }

    }

    private void bringImagePicker() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(AddDefaultImageActivity.this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                assert result != null;
                mainImageURI = result.getUri();
                materialCardViewShow.setVisibility(View.VISIBLE);
                imageViewShowImage.setImageURI(mainImageURI);
                buttonUpload.setEnabled(true);
                isChanged = true;

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                assert result != null;
                Exception error = result.getError();
                Toast.makeText(AddDefaultImageActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(AddDefaultImageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(AddDefaultImageActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AddDefaultImageActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showProgress(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ViewGroup viewGroup = findViewById(R.id.content);
        View viewDialog = LayoutInflater.from(context).inflate(R.layout.dialog_progress, viewGroup, false);
        builder.setView(viewDialog);
        builder.setCancelable(false);
        progressDialog = builder.create();
        progressDialog.show();
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );

    }

    private void hideProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}