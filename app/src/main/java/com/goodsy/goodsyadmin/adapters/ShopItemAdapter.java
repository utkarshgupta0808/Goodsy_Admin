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
import com.goodsy.goodsyadmin.activities.ItemListActivity;
import com.goodsy.goodsyadmin.activities.PhotoPreviewActivity;
import com.goodsy.goodsyadmin.models.ShopModel;

public class ShopItemAdapter extends FirestoreRecyclerAdapter<ShopModel, ShopItemAdapter.MyViewHolder> {

    Bundle bundle;

    public ShopItemAdapter(@NonNull FirestoreRecyclerOptions<ShopModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ShopItemAdapter.MyViewHolder holder, final int position, @NonNull final ShopModel model) {

        bundle = new Bundle();
        holder.shopName.setText(model.getShopName());
        holder.shopAddress.setText(model.getShopAddress());
        holder.shopCategory.setText(model.getShopCategory());
        holder.shopDes.setText(model.getShopDescription());

        Glide.with(holder.imgShop.getContext()).load(model.getShopImage()).into(holder.imgShop);

        holder.cardViewPhoto.setOnClickListener(view -> {
            bundle.putString("photoPreview", model.getShopImage());
            bundle.putString("photoDes", "Shop Image");
            holder.cardViewPhoto.getContext().startActivity(new Intent(holder.cardViewPhoto.getContext(), PhotoPreviewActivity.class).putExtras(bundle));

        });

        holder.cardViewLayout.setOnClickListener(view -> {
            bundle.putString("shopId", getSnapshots().getSnapshot(position).getId());
            holder.cardViewLayout.getContext().startActivity(new Intent(holder.cardViewLayout.getContext(), ItemListActivity.class).putExtras(bundle));
        });
    }

    @NonNull
    @Override
    public ShopItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_shop, parent, false);
        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgShop;
        TextView shopName, shopDes, shopAddress, shopCategory;
        CardView cardViewPhoto, cardViewLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgShop = itemView.findViewById(R.id.img_shop);
            shopName = itemView.findViewById(R.id.shop_name);
            shopDes = itemView.findViewById(R.id.shop_description);
            shopAddress = itemView.findViewById(R.id.shop_address);
            shopCategory = itemView.findViewById(R.id.shop_category);
            cardViewPhoto = itemView.findViewById(R.id.card_view_photo);
            cardViewLayout = itemView.findViewById(R.id.card_view_layout);
        }
    }
}
