package com.example.firstapp.mvpinterfaces;

import com.example.firstapp.models.User_Profile;

import org.json.JSONObject;

public interface ActivityMainPresenter {

    boolean isUserSignedIn();
    void signout();
    void signoutFacebook();
    void signoutGoogle();
    void initUserProfile();
    User_Profile getFacebookData(JSONObject object);
}
