package com.example.firstapp.mvpinterfaces.signupinterfaces;

import android.app.ProgressDialog;

public interface SignUpModel {

    void completeSignUp();
    void signUp(final String email, final String password, ProgressDialog spinner);
}
