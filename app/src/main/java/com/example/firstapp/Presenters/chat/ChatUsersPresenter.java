package com.example.firstapp.Presenters.chat;

import com.example.firstapp.models.data.UserProfile;
import com.example.firstapp.mvpinterfaces.chat.ChatUsersModel;
import com.example.firstapp.views.fragments.ChatFragment;

import java.util.List;

public class ChatUsersPresenter implements com.example.firstapp.mvpinterfaces.chat.ChatUsersPresenter {

    private com.example.firstapp.mvpinterfaces.chat.ChatFragment chatFragment;

    private ChatUsersModel model;


    public ChatUsersPresenter(ChatFragment chatFragment) {

        this.chatFragment = chatFragment;
        this.model = new com.example.firstapp.models.chat.ChatUsersModel(this);
    }


    @Override
    public void getContactsByName(String name) {

        model.getContactsByName(name);
    }

    @Override
    public void setContacts(List<UserProfile> contacts) {

        chatFragment.getContacts(contacts);
    }
}
