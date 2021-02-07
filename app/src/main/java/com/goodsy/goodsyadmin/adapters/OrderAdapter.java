package com.goodsy.goodsyadmin.adapters;

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
import com.goodsy.goodsyadmin.activities.DeliveryBoyInfoActivity;
import com.goodsy.goodsyadmin.activities.PhotoPreviewActivity;
import com.goodsy.goodsyadmin.models.OrderModel;
import com.goodsy.goodsyadmin.models.OrderModel;

public class OrderAdapter extends FirestoreRecyclerAdapter<OrderModel, OrderAdapter.MyViewHolder>  {

    Bundle bundle;

    public OrderAdapter(@NonNull FirestoreRecyclerOptions<OrderModel> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull final OrderAdapter.MyViewHolder holder, final int position, @NonNull final OrderModel model) {

        bundle=new Bundle();

//        holder.delName.setText(model.getDelName());
//        holder.delId.setText(model.getDelId());
//        holder.delCity.setText(model.getDelCity());
//        holder.delNumber.setText(model.getDelNumber());
//        holder.delRating.setText(model.getDelRating());
//        Glide.with(holder.imgDel.getContext()).load(model.getProfilePic()).into(holder.imgDel);

        holder.cardViewPhoto.setOnClickListener(view -> {

//            bundle.putString("photoPreview",model.getProfilePic());
//            bundle.putString("photoDes",model.getDelName());
//            holder.cardViewPhoto.getContext().startActivity(new Intent(holder.cardViewPhoto.getContext(), PhotoPreviewActivity.class).putExtras(bundle));

        });

        holder.cardViewLayout.setOnClickListener(view -> {


//            bundle.putString("applicationStatus",model.getApplicationStatus());
//            bundle.putString("docId",model.getDocId());
//            Toast.makeText(holder.cardViewLayout.getContext(), model.getDocId(), Toast.LENGTH_SHORT).show();
//
//            holder.cardViewLayout.getContext().startActivity(new Intent(holder.cardViewLayout.getContext(), DeliveryBoyInfoActivity.class).putExtras(bundle));



        });

    }

    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_delivery_boy, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView imgDel;
        TextView delName, delId, delCity, delNumber, delRating;
        CardView cardViewPhoto, cardViewLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDel = itemView.findViewById(R.id.del_img);
            delName = itemView.findViewById(R.id.del_name);
            delId = itemView.findViewById(R.id.del_id);
            delCity = itemView.findViewById(R.id.del_city);
            delNumber = itemView.findViewById(R.id.del_number);
            delRating = itemView.findViewById(R.id.del_rating);
            cardViewPhoto=itemView.findViewById(R.id.card_view_photo);
            cardViewLayout=itemView.findViewById(R.id.delivery_boy_card_view_layout);

        }
    }
}
