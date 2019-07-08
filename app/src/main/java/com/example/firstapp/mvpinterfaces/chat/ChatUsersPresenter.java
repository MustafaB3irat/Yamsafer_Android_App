package com.example.firstapp.mvpinterfaces.chat;

import com.example.firstapp.models.data.UserProfile;

import java.util.List;

public interface ChatUsersPresenter {


    void getContactsByName(String name);
    void setContacts(List<UserProfile> chatters);

}
