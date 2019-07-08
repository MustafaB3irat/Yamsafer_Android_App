package com.example.firstapp.mvpinterfaces.signupinterfaces;

import android.app.ProgressDialog;

public interface SignUpPresenter {

    void signUp(String email, String password, ProgressDialog progressDialog);

    boolean validate(String email , String password);

    boolean completeSignUp();
}
