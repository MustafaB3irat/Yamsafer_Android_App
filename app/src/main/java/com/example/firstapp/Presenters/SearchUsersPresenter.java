package com.example.firstapp.Presenters;

import com.example.firstapp.models.data.UserProfile;
import com.example.firstapp.mvpinterfaces.chat.SearchUser;
import com.example.firstapp.views.fragments.SearchUsersFragment;

import java.util.List;

public class SearchUsersPresenter implements SearchUser.SearchUsersPresenter {

    private SearchUser.SearchUsersFragment searchUsersFragment;

    private SearchUser.SearchUsersModel model;


    public SearchUsersPresenter(SearchUsersFragment searchUsersFragment) {

        this.searchUsersFragment = searchUsersFragment;
        this.model = new com.example.firstapp.models.SearchUsersModel(this);
    }


    @Override
    public void getContactsByName(String name) {

        model.getContactsByName(name);
    }

    @Override
    public void setContacts(List<UserProfile> contacts) {

        searchUsersFragment.getContacts(contacts);
    }
}
