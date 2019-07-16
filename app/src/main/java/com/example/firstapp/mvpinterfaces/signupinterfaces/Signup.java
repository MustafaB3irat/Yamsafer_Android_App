package com.example.firstapp.mvpinterfaces.signupinterfaces;

import android.app.ProgressDialog;

public interface Signup {

    interface SignUpModel {

        void completeSignUp();
        void signUp(final String email, final String password, ProgressDialog spinner);
    }

    interface SignUpPresenter {

        void signUp(String email, String password, ProgressDialog progressDialog);

        boolean validate(String email, String password);

        void isSignUpCompleted(boolean isCompleted);

        void setSignUpStatus(boolean isSignedUp);

        void setEmailSentStatus(boolean isSent);

        void alreadyExistEmail(boolean exists);

    }

    interface SignUpView {

        void setSignUpStatus(boolean state);

        void setEmailSentStatus(boolean state);

        void initProgressDialog();

        void initSignUpButton();

        void initEntriesListeners();

        void initGoToSignIn();

        void emailFieldEmpty(boolean isEmpty);

        void passwordFieldEmpty(boolean isEmpty);

        void wrongFormatEmail(boolean format);

        void wrongFormatPassword(boolean format);

        void alreadyExistEmail(boolean exsists);
        void isUserAddedToDatabase(boolean isAdded);


    }
}
