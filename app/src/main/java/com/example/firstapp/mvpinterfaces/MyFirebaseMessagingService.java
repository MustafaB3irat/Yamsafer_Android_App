package com.example.firstapp.mvpinterfaces;

import android.app.NotificationChannel;

import java.util.Map;

public interface MyFirebaseMessagingService {

    void sendTokenToServer(String token);
    void sendNotification(Map<String , String> payload);
    NotificationChannel createNotificationChannel();
}
