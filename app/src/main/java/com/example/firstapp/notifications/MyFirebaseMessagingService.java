package com.example.firstapp.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.firstapp.R;
import com.example.firstapp.models.FireStoreManager;
import com.example.firstapp.mvpinterfaces.chat.Chat;
import com.example.firstapp.views.ChatActivity;
import com.example.firstapp.views.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService implements com.example.firstapp.mvpinterfaces.MyFirebaseMessagingService {


    private String CHANNEL_ID = "Channel1";
    private final static String RECEIVER = "receiver";
    private final static String MESSAGE = "message";
    private final static String USERNAME = "username";
    private final static String MESSAGE_TYPE = "messageType";
    private final static String SENDER_AVATAR = "senderAvatar";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        sendTokenToServer(s);
    }


    @Override
    public void sendTokenToServer(String token) {

        FireStoreManager fireStoreManager = new FireStoreManager();

        fireStoreManager.saveToken(token);
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() != 0) {
            sendNotification(remoteMessage.getData());
        }


    }


    @Override
    public void sendNotification(Map<String, String> payload) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setContentTitle(getString(R.string.hey));
        builder.setContentText(getString(R.string.new_message));
        builder.setSmallIcon(R.drawable.ic_yamsafer);
        builder.setPriority(Notification.PRIORITY_MAX);

        Glide.with(this)
                .asBitmap()
                .load(payload.get(SENDER_AVATAR))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        builder.setLargeIcon(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });


        if (payload.get(MESSAGE_TYPE).equals("0")) {
            builder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(payload.get(MESSAGE))
                    .setSummaryText(payload.get(USERNAME))
                    .setBigContentTitle(null));


            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra(RECEIVER, payload.get(RECEIVER));

            PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);

            builder.setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(createNotificationChannel());
                builder.setChannelId(CHANNEL_ID);
            }

            notificationManager.notify(0, builder.build());

        } else

            Glide.with(this)
                    .asBitmap()
                    .load(payload.get(SENDER_AVATAR))
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            builder.setStyle(new NotificationCompat.BigPictureStyle()
                                    .bigLargeIcon(null)
                                    .bigPicture(resource)
                                    .setSummaryText(payload.get(USERNAME)));


                            Intent intent = new Intent(MyFirebaseMessagingService.this, ChatActivity.class);
                            intent.putExtra(RECEIVER, payload.get("senderId"));

                            PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);

                            builder.setContentIntent(pendingIntent);

                            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                notificationManager.createNotificationChannel(createNotificationChannel());
                                builder.setChannelId(CHANNEL_ID);
                            }

                            notificationManager.notify(0, builder.build());


                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });

    }

    @Override
    public NotificationChannel createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            return channel;
        }

        return null;
    }

}
