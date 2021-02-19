package com.goodsy.goodsyadmin.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.goodsy.goodsyadmin.R;
import com.goodsy.goodsyadmin.adapters.ChatListAdapter;
import com.goodsy.goodsyadmin.models.ChatListModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.goodsy.goodsyadmin.utils.Constants.mainUsersCollection;

public class ChatListActivity extends AppCompatActivity {

    public static List<ChatListModel> chatList = new ArrayList<>();
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String currentUserId;
    Toolbar toolbar;
    TextView textViewNoUser;
    int i = 0;
    LottieAnimationView lottieAnimationViewProgress;
    @SuppressLint("StaticFieldLeak")
    ChatListAdapter chatListadapter;
    private RecyclerView recyclerViewChatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        toolbar = findViewById(R.id.chat_list_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        recyclerViewChatList = findViewById(R.id.recyclerviewChatList);
        lottieAnimationViewProgress = findViewById(R.id.animationViewProgress);
        textViewNoUser = findViewById(R.id.no_chat_user);

        currentUserId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

        DatabaseReference messageRef = mRootRef.child("messages").child(currentUserId);

        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot documentSnapshot : snapshot.getChildren()) {
                        ChatListModel chatListModel = new ChatListModel();
                        firebaseFirestore.collection(mainUsersCollection).document(Objects.requireNonNull(documentSnapshot.getKey())).get().addOnCompleteListener(task -> {
                            chatListModel.setImage(Objects.requireNonNull(task.getResult()).getString("profilePic"));
                            chatListModel.setName(task.getResult().getString("Name"));
                            chatListModel.setDocId(documentSnapshot.getKey());
                            i++;
                            chatList.add(chatListModel);

                            if (i == snapshot.getChildrenCount()) {
                                if (chatList.size() > 0) {
                                    lottieAnimationViewProgress.setVisibility(View.GONE);
                                    textViewNoUser.setVisibility(View.GONE);
                                    recyclerViewChatList.setVisibility(View.VISIBLE);
                                    chatListadapter = new ChatListAdapter(chatList, ChatListActivity.this);
                                    recyclerViewChatList.setHasFixedSize(false);
                                    recyclerViewChatList.setLayoutManager(new LinearLayoutManager(ChatListActivity.this));
                                    recyclerViewChatList.setAdapter(chatListadapter);
                                    chatListadapter.notifyDataSetChanged();
                                } else {
                                    lottieAnimationViewProgress.setVisibility(View.GONE);
                                    textViewNoUser.setVisibility(View.VISIBLE);
                                    recyclerViewChatList.setVisibility(View.GONE);
                                }
                            }
//                        else {
//                            lottieAnimationViewProgress.setVisibility(View.GONE);
//                            textViewNoUser.setVisibility(View.VISIBLE);
//                            recyclerViewChatList.setVisibility(View.GONE);
//                        }
                        });
                    }
                } else {
                    lottieAnimationViewProgress.setVisibility(View.GONE);
                    textViewNoUser.setVisibility(View.VISIBLE);
                    recyclerViewChatList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                lottieAnimationViewProgress.setVisibility(View.GONE);
                textViewNoUser.setVisibility(View.VISIBLE);
                recyclerViewChatList.setVisibility(View.GONE);
                Log.d("ChatListError", "Failed to read value.", error.toException());
            }
        });

    }

}