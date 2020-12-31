package com.goodsy.goodsyadmin.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.goodsy.goodsyadmin.R;
import com.goodsy.goodsyadmin.activities.ItemInfoActivity;
import com.goodsy.goodsyadmin.activities.PhotoPreviewActivity;
import com.goodsy.goodsyadmin.models.ItemModel;

public class ItemAdapter extends FirestoreRecyclerAdapter<ItemModel, ItemAdapter.MyViewHolder> {

    Bundle bundle;

    public ItemAdapter(@NonNull FirestoreRecyclerOptions<ItemModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ItemAdapter.MyViewHolder holder, final int position, @NonNull final ItemModel model) {

        bundle=new Bundle();

        holder.itemName.setText(model.getItemName());
        holder.itemDescription.setText(model.getItemDescription());
        holder.itemPrice.setText(model.getItemPrice());
        holder.itemWeight.setText(model.getItemWeight());

        Glide.with(holder.imgItem.getContext()).load(model.getItemImage()).into(holder.imgItem);

        holder.cardViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bundle.putString("photoPreview",model.getItemImage());
                bundle.putString("photoDes","Item Image");
                holder.cardViewPhoto.getContext().startActivity(new Intent(holder.cardViewPhoto.getContext(), PhotoPreviewActivity.class).putExtras(bundle));

            }
        });

        holder.cardViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bundle.putString("itemImage",model.getItemImage());
                bundle.putString("itemName",model.getItemName());
                bundle.putString("itemPrice",model.getItemPrice());
                bundle.putString("itemId",getSnapshots().getSnapshot(position).getId());
                bundle.putString("itemWeight",model.getItemWeight());
                bundle.putString("itemDescription", model.getItemDescription());
                bundle.putString("itemImage", model.getItemImage());

                holder.cardViewLayout.getContext().startActivity(new Intent(holder.cardViewLayout.getContext(), ItemInfoActivity.class).putExtras(bundle));

            }
        });



    }

    @NonNull
    @Override
    public ItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new ItemAdapter.MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView imgItem;
        TextView itemName, itemDescription, itemPrice, itemWeight;
        CardView cardViewPhoto, cardViewLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItem = itemView.findViewById(R.id.img_item);
            itemName = itemView.findViewById(R.id.item_name);
            itemDescription = itemView.findViewById(R.id.item_description);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemWeight = itemView.findViewById(R.id.item_weight);
            cardViewPhoto=itemView.findViewById(R.id.card_view_photo);
            cardViewLayout=itemView.findViewById(R.id.card_view_layout);
        }
    }
}
