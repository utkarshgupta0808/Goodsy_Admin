package com.goodsy.goodsyadmin.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserAdapter extends FirestoreRecyclerAdapter<ShopModel, UserAdapter.MyViewHolder>  {

    Bundle bundle;

    public UserAdapter(@NonNull FirestoreRecyclerOptions<ShopModel> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull final UserAdapter.MyViewHolder holder, final int position, @NonNull final ShopModel model) {

        bundle=new Bundle();

        holder.shopName.setText(model.getShopName());
        holder.shopAddress.setText(model.getShopAddress());
        holder.shopCategory.setText(model.getShopCategory());
        holder.shopDes.setText(model.getShopDescription());

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
                bundle.putString("shopId",getSnapshots().getSnapshot(position).getId());
                bundle.putString("shopName",model.getShopName());
                bundle.putString("shopDes",model.getShopDescription());
                bundle.putString("shopCategory",model.getShopCategory());
                bundle.putString("shopAddress",model.getShopAddress());
                bundle.putString("aadhaarFront",model.getAadharFront());
                bundle.putString("panCard",model.getPanCard());
                bundle.putString("gstCertificate",model.getGst());
                bundle.putString("aadhaarBack",model.getAadharBack());
                bundle.putDouble("shopLongitude",model.getShopLongitude());
                bundle.putDouble("shopLatitude",model.getShopLatitude());
                bundle.putString("shopKeeperId",model.getShopKeeperId());
                bundle.putString("applicationStatus",model.getApplicationStatus());



                FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
                firebaseFirestore.collection("ShopKeeper").document(model.getShopKeeperId()).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                bundle.putString("ownerName",documentSnapshot.get("ownerName").toString());
                                bundle.putString("ownerPhone",documentSnapshot.get("ownerNumber").toString());
                                bundle.putString("ownerEmail",documentSnapshot.get("ownerEmail").toString());
                                bundle.putString("ownerAddress",documentSnapshot.get("ownerCity").toString());

                                holder.cardViewPhoto.getContext().startActivity(new Intent(holder.cardViewPhoto.getContext(), ShopInfoActivity.class).putExtras(bundle));

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(holder.cardViewLayout.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });



            }
        });

    }

    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
            shopDes = itemView.findViewById(R.id.shop_description);
            shopAddress = itemView.findViewById(R.id.shop_address);
            shopCategory = itemView.findViewById(R.id.shop_category);
            cardViewPhoto=itemView.findViewById(R.id.card_view_photo);
            cardViewLayout=itemView.findViewById(R.id.card_view_layout);

        }
    }
}
