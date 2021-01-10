package com.goodsy.goodsyadmin.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.goodsy.goodsyadmin.R;
import com.goodsy.goodsyadmin.activities.ChatActivity;
import com.goodsy.goodsyadmin.activities.PhotoPreviewActivity;
import com.goodsy.goodsyadmin.helper.GetTimeAgo;
import com.goodsy.goodsyadmin.models.MessagesModel;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final List<MessagesModel> mMessageList;
    final int RECEIVER = 1;
    private final int SENDER = 2;
    Context context;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    public MessageAdapter(List<MessagesModel> mMessageList, Context context) {
        this.mMessageList = mMessageList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == SENDER) {
            View viewSender = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_sender_message, parent, false);
            return new SenderViewHolder(viewSender);
        } else {
            View viewReceiver = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_receiver_message, parent, false);
            return new ReceiverViewHolder(viewReceiver);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        MessagesModel c = mMessageList.get(i);

        String message_type = c.getType();

        if (getItemViewType(i) == SENDER) {
            String lastSeenTime = GetTimeAgo.getTimeAgo(c.getTime());
            if (lastSeenTime == null)
                ((SenderViewHolder) holder).textViewTimeSender.setText("just now");
            else
                ((SenderViewHolder) holder).textViewTimeSender.setText(lastSeenTime);
            if (message_type.equals("text")) {
                ((SenderViewHolder) holder).cardViewSenderMessage.setVisibility(View.VISIBLE);
                ((SenderViewHolder) holder).messageTextSender.setText(c.getMessage());
                ((SenderViewHolder) holder).cardViewSenderImage.setVisibility(View.GONE);
            } else {
                ((SenderViewHolder) holder).cardViewSenderMessage.setVisibility(View.GONE);
                ((SenderViewHolder) holder).cardViewSenderImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(c.getMessage()).placeholder(R.drawable.loading_photo).into(((SenderViewHolder) holder).messageImageSender);
            }
            ((SenderViewHolder) holder).messageImageSender.setOnClickListener(v -> {
                Bundle bundle1 = new Bundle();
                bundle1.putString("photoPreview", c.getMessage());
                bundle1.putString("photoDes", "Preview");
                context.startActivity(new Intent(context, PhotoPreviewActivity.class).putExtras(bundle1));
            });
        } else {
            String from_user = c.getFrom();
            String lastSeenTime = GetTimeAgo.getTimeAgo(c.getTime());
            ((ReceiverViewHolder) holder).textViewTimeReceiver.setText(lastSeenTime);
//            firebaseFirestore.collection(Constants.mainUsersCollection).document(from_user).get().addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    String name = task.getResult().getString("Name");
//                    String image = task.getResult().getString("profilePic");
//
//                }
//            });
            ((ReceiverViewHolder) holder).displayNameReceiver.setText(ChatActivity.chatUserName);
            Glide.with(context).load(ChatActivity.chatUserImage).placeholder(R.drawable.loading_photo).into(((ReceiverViewHolder) holder).profileImageReceiver);
            ((ReceiverViewHolder) holder).textViewPicName.setText(ChatActivity.chatUserName);
            Glide.with(context).load(ChatActivity.chatUserImage).placeholder(R.drawable.loading_photo).into(((ReceiverViewHolder) holder).imageViewPicUser);
            if (message_type.equals("text")) {
                ((ReceiverViewHolder) holder).cardViewReceiverMessage.setVisibility(View.VISIBLE);
                ((ReceiverViewHolder) holder).messageTextReceiver.setText(c.getMessage());
                ((ReceiverViewHolder) holder).cardViewReceiverImage.setVisibility(View.GONE);
            } else {
                ((ReceiverViewHolder) holder).cardViewReceiverMessage.setVisibility(View.GONE);
                ((ReceiverViewHolder) holder).cardViewReceiverImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(c.getMessage()).placeholder(R.drawable.loading_photo).into(((ReceiverViewHolder) holder).messageImageReceiver);
            }
            ((ReceiverViewHolder) holder).messageImageReceiver.setOnClickListener(v -> {
                Bundle bundle1 = new Bundle();
                bundle1.putString("photoPreview", c.getMessage());
                bundle1.putString("photoDes", "Preview");
                context.startActivity(new Intent(context, PhotoPreviewActivity.class).putExtras(bundle1));
            });
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        firebaseAuth = FirebaseAuth.getInstance();
        MessagesModel c = mMessageList.get(position);
        if (c.getFrom().equals(firebaseAuth.getCurrentUser().getUid()))
            return SENDER;
        else
            return RECEIVER;
    }

    public static class ReceiverViewHolder extends RecyclerView.ViewHolder {

        public TextView messageTextReceiver;
        public CircleImageView profileImageReceiver;
        public TextView displayNameReceiver, textViewTimeReceiver, textViewPicName;
        public ImageView messageImageReceiver, imageViewPicUser;
        MaterialCardView cardViewReceiverMessage, cardViewReceiverImage;

        public ReceiverViewHolder(View view) {
            super(view);

            cardViewReceiverImage = view.findViewById(R.id.card_receiver_image);
            cardViewReceiverMessage = view.findViewById(R.id.card_receiver_message);
            messageTextReceiver = view.findViewById(R.id.receiver_message);
            profileImageReceiver = view.findViewById(R.id.receiver_image);
            displayNameReceiver = view.findViewById(R.id.receiver_first_name);
            messageImageReceiver = view.findViewById(R.id.message_receiver_image);
            textViewTimeReceiver = view.findViewById(R.id.message_time_receiver);
            textViewPicName = view.findViewById(R.id.receiver_first_name_pic);
            imageViewPicUser = view.findViewById(R.id.receiver_image_pic);

        }
    }

    public static class SenderViewHolder extends RecyclerView.ViewHolder {

        public TextView messageTextSender, textViewTimeSender;
        public ImageView messageImageSender;
        MaterialCardView cardViewSenderMessage, cardViewSenderImage;

        public SenderViewHolder(View view) {
            super(view);

            cardViewSenderMessage = view.findViewById(R.id.card_sender_message);
            cardViewSenderImage = view.findViewById(R.id.card_sender_image);
            messageTextSender = view.findViewById(R.id.sender_message);
            textViewTimeSender = view.findViewById(R.id.sender_message_time);
            messageImageSender = view.findViewById(R.id.message_sender_image);

        }
    }
}
