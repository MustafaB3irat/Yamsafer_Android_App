package com.example.firstapp.mvpinterfaces.chat;

import com.example.firstapp.models.data.UserProfile;

import java.util.List;

public interface SearchUser {
    interface SearchUsersFragment {

        void getContacts(List<UserProfile> contacts);
        void initSearchEditText();
    }

    interface SearchUsersModel {

        void getContactsByName(String name);

        void getDataFromFireStore(String name);
        void getDataFromCache(String name);
    }

    interface SearchUsersPresenter {


        void getContactsByName(String name);
        void setContacts(List<UserProfile> chatters);

    }
}
