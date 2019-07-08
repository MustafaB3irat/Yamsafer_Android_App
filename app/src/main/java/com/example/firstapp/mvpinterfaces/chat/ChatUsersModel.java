package com.example.firstapp.mvpinterfaces.chat;

public interface ChatUsersModel {

    void getContactsByName(String name);

    void getDataFromFireStore(String name);
    void getDataFromCache(String name);
}
