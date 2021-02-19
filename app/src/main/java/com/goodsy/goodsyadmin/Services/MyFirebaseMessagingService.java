package com.goodsy.goodsyadmin.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.goodsy.goodsyadmin.R;
import com.goodsy.goodsyadmin.activities.ChatActivity;
import com.goodsy.goodsyadmin.activities.ItemInfoActivity;
import com.goodsy.goodsyadmin.activities.ItemListActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

import static com.goodsy.goodsyadmin.utils.Constants.CHANNEL_3_ID;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    Bitmap bitmap, output, bitmapImage;
    NotificationManager notificationManager;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (Objects.equals(remoteMessage.getData().get("notiType"), "Message")) {
            getMessageNotification(remoteMessage);
        } else {
//            sendOrderNotification(remoteMessage);
            getItemNotification(remoteMessage);
        }

    }

    private void getItemNotification(RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        Map<String, String> extraData = remoteMessage.getData();

        String itemImage = extraData.get("itemImage");
        String category = extraData.get("category");
        Bitmap bitmap = null;
        assert itemImage != null;
        try {
            URL url = new URL(itemImage);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "Goodsy")
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.noti_stroke)
                .setLargeIcon(bitmap);

        if (extraData.get("category") != null) {
            if (Objects.equals(extraData.get("category"), "Item")) {
                Bundle bundle = new Bundle();
                bundle.putString("itemId", extraData.get("itemId"));
                bundle.putString("itemName", extraData.get("itemName"));
                bundle.putString("itemDescription", extraData.get("itemDescription"));
                bundle.putString("itemPrice", extraData.get("itemPrice"));
                bundle.putString("itemWeight", extraData.get("itemWeight"));
                bundle.putString("itemImage", extraData.get("itemImage"));
                Intent intent = new Intent(this, ItemInfoActivity.class);
                intent.putExtras(bundle);
                ItemListActivity.selectedShop = extraData.get("shopId");
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

    private void getMessageNotification(RemoteMessage remoteMessage) {
        Log.d("Message", "Hit");
        Map<String, String> response = remoteMessage.getData();
        String title = response.get("name");
        String user_id = response.get("user_id");
        String message = response.get("message");
        String sender_fcm = response.get("sender_fcm");
        String type = response.get("type");

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        try {
            String sender_image = response.get("image");
            if (sender_image != null) {
                URL url = new URL(sender_image);
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                getCircleBitmap(bitmap);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel groupChannel = new NotificationChannel(CHANNEL_3_ID, "Chats", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(groupChannel);
        }
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("userId", user_id);
        intent.putExtra("fcm", sender_fcm);
//        intent.putExtra("notification_id", bundleNotificationId);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder;
        if (type.equals("Image")) {
            try {
                String imageAdd = response.get("message");
                if (imageAdd != null) {
                    URL url = new URL(imageAdd);
                    bitmapImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_3_ID)
                    .setContentTitle(title)
                    .setSound(defaultSound)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentText("New Image")
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.noti_stroke)
                    .setLargeIcon(output)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(bitmapImage))
                    .setContentIntent(pendingIntent);

        } else {
            notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_3_ID)
                    .setContentTitle(title)
                    .setSound(defaultSound)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.noti_stroke)
                    .setLargeIcon(output)
                    .setContentIntent(pendingIntent);
        }
        notificationManager.notify(3, notificationBuilder.build());
    }

    public Bitmap getCircleBitmap(Bitmap bitmap) {
        Rect srcRect, dstRect;
        float r;
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();

        if (width > height) {
            output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            int left = (width - height) / 2;
            int right = left + height;
            srcRect = new Rect(left, 0, right, height);
            dstRect = new Rect(0, 0, right, height);
            r = height / 2;
        } else {
            output = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
            int top = (height - width) / 2;
            int bottom = top + width;
            srcRect = new Rect(0, top, width, bottom);
            dstRect = new Rect(0, 0, width, bottom);
            r = width / 2;
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, srcRect, dstRect, paint);

        bitmap.recycle();

        return output;
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }
}
