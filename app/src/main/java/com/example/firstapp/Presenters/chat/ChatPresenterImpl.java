package com.example.firstapp.Presenters.chat;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.models.chat.ChatModelImp;
import com.example.firstapp.models.data.Message;
import com.example.firstapp.mvpinterfaces.chat.Chat;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.List;

public class ChatPresenterImpl implements Chat.ChatPresenter {

    private Chat.ChatModel model;
    private Chat.ChatView view;

    public ChatPresenterImpl(Chat.ChatView view) {
        this.view = view;
        model = new ChatModelImp(this);

    }


    @Override
    public void setCurrentUser(FirebaseUser currentUser) {

        view.getCurrentUser(currentUser);

    }

    @Override
    public void getCurrentUser() {
        model.setCurrentUser();
    }

    @Override
    public void didLoadMessages(List<Message> messages) {

        view.setMessages(messages);
    }

    @Override
    public void onMessageSent(boolean isSent) {
        view.onMessageSent(isSent);
    }

    @Override
    public void sendMessage(String message, boolean isImage) {

        model.addMessage(message, isImage);

    }


    @Override
    public void messageReceived(boolean playSound) {

        view.messageReceived(playSound);
    }

    @Override
    public void messageSent(boolean playSound) {

        view.messageSent(true);
    }

    @Override
    public void checkWhoInitiatedTheChat(String receiver) {

        model.checkWhoInitiatedTheChat(receiver);

    }

    @Override
    public void setFriendsID(String id) {
        model.getFriendsAvatarAndUsername(id);
    }

    @Override
    public void getFriendsAvatarAndUsername(HashMap<String, Object> friendsInfo) {

        view.initFriendsUsernameAndAvatar(friendsInfo);

    }

    @Override
    public void setImageURI(Uri imageURI) {

    }




}
