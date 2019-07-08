package com.example.firstapp.Presenters.signuppresenters;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.util.Patterns;

import com.example.firstapp.mvpinterfaces.signupinterfaces.SignUpModel;
import com.example.firstapp.mvpinterfaces.signupinterfaces.SignUpView;

import java.util.regex.Pattern;

public class SignUpPresenter implements com.example.firstapp.mvpinterfaces.signupinterfaces.SignUpPresenter {


    private SignUpView signUpView;
    private SignUpModel signUpModel;


    public SignUpPresenter(SignUpView signUpView) {
        this.signUpModel = new com.example.firstapp.models.SignUpModel(this);
        this.signUpView = signUpView;

    }


    @Override
    public void signUp(final String email, final String password, final ProgressDialog progressDialog) {

        progressDialog.show();
        signUpModel.signUp(email, password, progressDialog);

    }

    @Override
    public boolean validate(String email, String password) {

        boolean isValid = true;

        if (TextUtils.isEmpty(email)) {
            signUpView.emailFieldEmpty(true);
            isValid = false;
        }

        if (TextUtils.isEmpty(password)) {

            signUpView.passwordFieldEmpty(true);

            isValid = false;
        }

        if (!isValid)
            return isValid;

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signUpView.wrongFormatEmail(true);
            isValid = false;
        }
        if (!Pattern.compile("^[A-Z][A-Za-z0-9_\\)\\(\\*\\&\\^\\%\\$\\#@!]{9}$").matcher(password).find()) {

            signUpView.wrongFormatPassword(true);
            isValid = false;
        }

        return isValid;

    }

    @Override
    public void isSignUpCompleted(boolean isCompleted) {


    }

    @Override
    public void setSignUpStatus(boolean isSignedUp) {

        signUpView.setSignUpStatus(true);

    }

    @Override
    public void setEmailSentStatus(boolean isSent) {

        signUpView.setEmailSentStatus(isSent);

    }

    @Override
    public void alreadyExistEmail(boolean exists) {

        signUpView.alreadyExistEmail(exists);
    }


}
