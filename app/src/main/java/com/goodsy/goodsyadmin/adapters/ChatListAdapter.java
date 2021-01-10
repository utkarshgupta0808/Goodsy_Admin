package com.goodsy.goodsyadmin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.goodsy.goodsyadmin.R;
import com.goodsy.goodsyadmin.activities.ChatActivity;
import com.goodsy.goodsyadmin.models.ChatListModel;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {

    private final List<ChatListModel> chatArrayList;
    Context context;

    public ChatListAdapter(List<ChatListModel> chatArrayList, Context context) {
        this.chatArrayList = chatArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_chat_list, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

        ChatListModel chatListModel = chatArrayList.get(position);

        Glide.with(context).load(chatListModel.getImage()).placeholder(R.drawable.loading_photo).into(holder.imageViewUser);
        holder.textViewName.setText(chatListModel.getName());

        holder.constraintLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("userId", chatListModel.getDocId());
            intent.putExtra("userName", chatListModel.getName());
            intent.putExtra("userImage", chatListModel.getImage());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return chatArrayList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewMessage;
        ImageView imageViewUser;
        ConstraintLayout constraintLayout;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewUser = itemView.findViewById(R.id.ivUser);
            textViewName = itemView.findViewById(R.id.tvName);
            textViewMessage = itemView.findViewById(R.id.tvMessage);

            constraintLayout = itemView.findViewById(R.id.cons_lay_chat_list_main);
        }
    }
}
