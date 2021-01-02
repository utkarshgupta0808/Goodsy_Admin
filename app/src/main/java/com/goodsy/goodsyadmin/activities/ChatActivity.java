package com.goodsy.goodsyadmin.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.goodsy.goodsyadmin.R;
import com.goodsy.goodsyadmin.adapters.MessageAdapter;
import com.goodsy.goodsyadmin.models.MessagesModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import id.zelory.compressor.Compressor;

import static com.goodsy.goodsyadmin.utils.Constants.TOTAL_ITEMS_TO_LOAD;
import static com.goodsy.goodsyadmin.utils.Constants.mCurrentPage;

public class ChatActivity extends AppCompatActivity {

    private final List<MessagesModel> messagesList = new ArrayList<>();
    Toolbar toolbar;
    EditText editTextMessage;
    ImageView imageViewAddFiles, imageViewSendBtn;
    String mChatUser;
    String currentUserId;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    LottieAnimationView lottieAnimationViewChat;
    private DatabaseReference mRootRef;
    private MessageAdapter messageAdapter;
    private RecyclerView recyclerViewMessages;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager mLinearLayout;
    private Bitmap compressedImageFile;

    private int itemPos = 0;
    private String mLastKey = "";
    private String mPrevKey = "";

    private StorageReference mImageStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        currentUserId = firebaseAuth.getCurrentUser().getUid();

        if (getIntent().getStringExtra("userId") != null)
            mChatUser = getIntent().getStringExtra("userId");

        editTextMessage = findViewById(R.id.edit_text_chat);
        imageViewAddFiles = findViewById(R.id.chat_add_file);
        imageViewSendBtn = findViewById(R.id.send_button_chat);
        recyclerViewMessages = findViewById(R.id.recyclerviewMessages);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_chats);
        lottieAnimationViewChat = findViewById(R.id.animationViewProgressChat);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mImageStorage = FirebaseStorage.getInstance().getReference();

        messageAdapter = new MessageAdapter(messagesList, this);

        mLinearLayout = new LinearLayoutManager(this);
        recyclerViewMessages.setHasFixedSize(true);
        recyclerViewMessages.setLayoutManager(mLinearLayout);
        recyclerViewMessages.setAdapter(messageAdapter);

        loadMessages();

        imageViewSendBtn.setOnClickListener(v -> sendMessage());

        swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) () -> {

            lottieAnimationViewChat.setVisibility(View.VISIBLE);
            mCurrentPage++;
            itemPos = 0;
            loadMoreMessages();

        });

        imageViewAddFiles.setOnClickListener(view -> checkStoragePermission());

    }

    void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ChatActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
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
                .start(ChatActivity.this);

    }

    private void sendMessage() {

        String message = editTextMessage.getText().toString().trim();

        if (!TextUtils.isEmpty(message)) {

            String current_user_ref = "messages/" + currentUserId + "/" + mChatUser;
            String chat_user_ref = "messages/" + mChatUser + "/" + currentUserId;

            DatabaseReference user_message_push = mRootRef.child("messages").child(currentUserId).child(mChatUser).push();

            String push_id = user_message_push.getKey();

            Map<String, Object> messageMap = new HashMap<>();
            messageMap.put("time", ServerValue.TIMESTAMP);
            messageMap.put("message", message);
            messageMap.put("seen", false);
            messageMap.put("type", "text");
            messageMap.put("from", currentUserId);

            Map<String, Object> messageUserMap = new HashMap<>();
            messageUserMap.put(current_user_ref + "/" + push_id, messageMap);
            messageUserMap.put(chat_user_ref + "/" + push_id, messageMap);

            editTextMessage.setText("");

//            mRootRef.child("Chat").child(currentUserId).child(mChatUser).child("seen").setValue(true);
//            mRootRef.child("Chat").child(currentUserId).child(mChatUser).child("timestamp").setValue(ServerValue.TIMESTAMP);
//
//            mRootRef.child("Chat").child(mChatUser).child(currentUserId).child("seen").setValue(false);
//            mRootRef.child("Chat").child(mChatUser).child(currentUserId).child("timestamp").setValue(ServerValue.TIMESTAMP);

            mRootRef.updateChildren(messageUserMap, (error, ref) -> {
                if (error != null) {

                    Log.d("CHAT_LOG", error.getMessage());

                }
            });

        }

    }

    private void loadMessages() {

        DatabaseReference messageRef = mRootRef.child("messages").child(currentUserId).child(mChatUser);

        Query messageQuery = messageRef.limitToLast(mCurrentPage * TOTAL_ITEMS_TO_LOAD);

        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {

                MessagesModel message = dataSnapshot.getValue(MessagesModel.class);

                itemPos++;

                if (itemPos == 1) {
                    String messageKey = dataSnapshot.getKey();
                    mLastKey = messageKey;
                    mPrevKey = messageKey;
                }

                lottieAnimationViewChat.setVisibility(View.GONE);
                messagesList.add(message);
                messageAdapter.notifyDataSetChanged();

                recyclerViewMessages.scrollToPosition(messagesList.size() - 1);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadMoreMessages() {

        DatabaseReference messageRef = mRootRef.child("messages").child(currentUserId).child(mChatUser);

        Query messageQuery = messageRef.orderByKey().endAt(mLastKey).limitToLast(10);

        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {


                MessagesModel message = dataSnapshot.getValue(MessagesModel.class);
                String messageKey = dataSnapshot.getKey();

                if (!mPrevKey.equals(messageKey)) {
                    messagesList.add(itemPos++, message);
                    Log.d("Check", "LAST");
                } else {
                    mPrevKey = mLastKey;
                    Log.d("Check", "NoLAST");
                }

                if (itemPos == 1) {
                    mLastKey = messageKey;
                }
                lottieAnimationViewChat.setVisibility(View.GONE);
                messageAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                mLinearLayout.scrollToPositionWithOffset(10, 0);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                Uri imageUri = result.getUri();

                File newImageFile = new File(Objects.requireNonNull(imageUri.getPath()));
                try {
                    compressedImageFile = new Compressor(this)
                            .setMaxWidth(300)
                            .setMaxHeight(300)
                            .setQuality(70)
                            .compressToBitmap(newImageFile);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] thumbData = byteArrayOutputStream.toByteArray();

                final String current_user_ref = "messages/" + currentUserId + "/" + mChatUser;
                final String chat_user_ref = "messages/" + mChatUser + "/" + currentUserId;

                DatabaseReference user_message_push = mRootRef.child("messages").child(currentUserId).child(mChatUser).push();

                final String push_id = user_message_push.getKey();

                StorageReference filepath = mImageStorage.child("message_images").child(push_id + ".jpg");

                filepath.putBytes(thumbData).addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        Task<Uri> uriTask = task.getResult().getMetadata().getReference().getDownloadUrl();
                        uriTask.addOnSuccessListener(uri -> {
                            String file = uri.toString();
                            Log.d("Photo", file);

                            Map<String, Object> messageMap = new HashMap<>();
                            messageMap.put("message", file);
                            messageMap.put("seen", false);
                            messageMap.put("type", "image");
                            messageMap.put("time", ServerValue.TIMESTAMP);
                            messageMap.put("from", currentUserId);

                            Map<String, Object> messageUserMap = new HashMap<>();
                            messageUserMap.put(current_user_ref + "/" + push_id, messageMap);
                            messageUserMap.put(chat_user_ref + "/" + push_id, messageMap);

                            editTextMessage.setText("");

                            mRootRef.updateChildren(messageUserMap, (databaseError, databaseReference) -> {

                                if (databaseError != null) {

                                    Log.d("CHAT_LOG", databaseError.getMessage());

                                }

                            });
                        });
                    }

                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                assert result != null;
                Exception error = result.getError();
                Toast.makeText(ChatActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(ChatActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ChatActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    bringImagePicker();
                }
            } else {
                Toast.makeText(ChatActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}