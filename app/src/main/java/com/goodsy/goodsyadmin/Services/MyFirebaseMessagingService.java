package com.goodsy.goodsyadmin.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.goodsy.goodsyadmin.R;
import com.goodsy.goodsyadmin.activities.ItemInfoActivity;
import com.goodsy.goodsyadmin.activities.ItemListActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        Map<String, String> extraData = remoteMessage.getData();

        String itemImage = extraData.get("itemImage");
        String category = extraData.get("category");
        Bitmap bitmap = null;
        assert itemImage!=null;
        try {
            URL url= new URL(itemImage);
            bitmap= BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "Goodsy")
                        .setContentTitle(title)
                        .setContentText(body)
                        .setSmallIcon(R.drawable.noti_stroke)
                        .setLargeIcon(bitmap);

        if(extraData.get("category")!=null){
            if(extraData.get("category").equals("Item")){
                Bundle bundle= new Bundle();
                bundle.putString("itemId",extraData.get("itemId"));
                bundle.putString("itemName",extraData.get("itemName"));
                bundle.putString("itemDescription",extraData.get("itemDescription"));
                bundle.putString("itemPrice",extraData.get("itemPrice"));
                bundle.putString("itemWeight",extraData.get("itemWeight"));
                bundle.putString("itemImage",extraData.get("itemImage"));
                Intent intent= new Intent(this, ItemInfoActivity.class);
                intent.putExtras(bundle);
                ItemListActivity.selectedShop=extraData.get("shopId");
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                notificationBuilder.setContentIntent(pendingIntent);
//                startActivity(intent);
            }
        }
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//        notificationBuilder.setContentIntent(pendingIntent);


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        int id = (int) System.currentTimeMillis();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Goodsy", "New Items", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(id, notificationBuilder.build());

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }
}
