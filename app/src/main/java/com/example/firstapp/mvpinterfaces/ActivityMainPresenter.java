package com.example.firstapp.mvpinterfaces;

import com.example.firstapp.models.data.UserProfile;

import org.json.JSONObject;

public interface ActivityMainPresenter {

    boolean isUserSignedIn();
    void signout();
    void signoutFacebook();
    void signoutGoogle();
    void initUserProfile();
    UserProfile getFacebookData(JSONObject object);
}
