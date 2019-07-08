package com.example.firstapp.mvpinterfaces.chat;

import com.example.firstapp.models.data.UserProfile;

import java.util.List;

public interface ChatFragment {

    void getContacts(List<UserProfile> contacts);
    void initSearchEditText();
}
