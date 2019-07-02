package com.example.firstapp.mvpinterfaces.signininterfaces;

import androidx.appcompat.app.AppCompatActivity;

public interface SigninView {

    int RequestCode = 777;

    void getSigninState(boolean state);

    void getSignOutState(boolean state);

    AppCompatActivity getActivity();

    void getUserInfoFromFacebook(String name, String email, String id);

}
