package com.example.firstapp.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.firstapp.R;
import com.example.firstapp.models.FireStoreManager;
import com.example.firstapp.mvpinterfaces.chat.Chat;
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
        builder.setContentTitle(payload.get(USERNAME));
        builder.setContentText(payload.get(MESSAGE));
        builder.setSmallIcon(R.drawable.ic_yamsafer);
//        NotificationCompat.BigTextStyle
        builder.setPriority(Notification.PRIORITY_MAX);


        Intent intent = new Intent(this, Chat.class);
        intent.putExtra(RECEIVER, payload.get(RECEIVER));

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
