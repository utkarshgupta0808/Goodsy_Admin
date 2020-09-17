package com.goodsy.goodsyadmin.adapters;

import android.app.Activity;
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
import com.goodsy.goodsyadmin.activities.PhotoPreviewActivity;
import com.goodsy.goodsyadmin.activities.ShopInfoActivity;
import com.goodsy.goodsyadmin.models.ShopModel;

public class ShopAdapter extends FirestoreRecyclerAdapter<ShopModel, ShopAdapter.MyViewHolder>  {

    Bundle bundle;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ShopAdapter(@NonNull FirestoreRecyclerOptions<ShopModel> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull final ShopAdapter.MyViewHolder holder, int position, @NonNull final ShopModel model) {

        bundle=new Bundle();

        holder.shopName.setText(model.getShopName());
        holder.shopAddress.setText(model.getShopAddress());
        holder.shopCategory.setText(model.getShopCategory());
        holder.shopDes.setText(model.getShopType());

        Glide.with(holder.imgShop.getContext()).load(model.getShopImage()).into(holder.imgShop);

        holder.cardViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bundle.putString("photoPreview",model.getShopImage());
                bundle.putString("photoDes","Shop Image");
                holder.cardViewPhoto.getContext().startActivity(new Intent(holder.cardViewPhoto.getContext(), PhotoPreviewActivity.class).putExtras(bundle));

            }
        });

        holder.cardViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bundle.putString("shopImage",model.getShopImage());
                bundle.putString("shopName",model.getShopName());
                bundle.putString("shopDes",model.getShopType());
                bundle.putString("shopCategory",model.getShopCategory());
                bundle.putString("shopAddress",model.getShopAddress());
                bundle.putString("aadhaarCardFront",model.getShopImage());
                bundle.putString("panCard",model.getShopImage());
                bundle.putString("gstCertificate",model.getShopImage());
                bundle.putString("aadhaarCardBack",model.getShopImage());
                bundle.putString("shopLongitude",model.getShopImage());
                bundle.putString("shopLattitude",model.getShopImage());

                holder.cardViewPhoto.getContext().startActivity(new Intent(holder.cardViewPhoto.getContext(), ShopInfoActivity.class).putExtras(bundle));



            }
        });

    }

    @NonNull
    @Override
    public ShopAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_shop, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView imgShop;
        TextView shopName, shopDes, shopAddress, shopCategory;
        CardView cardViewPhoto, cardViewLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgShop = itemView.findViewById(R.id.img_shop);
            shopName = itemView.findViewById(R.id.shop_name);
            shopDes = itemView.findViewById(R.id.shop_type);
            shopAddress = itemView.findViewById(R.id.shop_address);
            shopCategory = itemView.findViewById(R.id.shop_category);
            cardViewPhoto=itemView.findViewById(R.id.card_view_photo);
            cardViewLayout=itemView.findViewById(R.id.card_view_layout);

        }
    }
}
