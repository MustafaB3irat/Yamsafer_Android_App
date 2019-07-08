package com.example.firstapp.mvpinterfaces.signupinterfaces;

public interface EmailVerificationView {

    void isUserVerified(boolean state);

    void isUserAddedToDatabase(boolean state);

    void onEmailVerified();


//    String getEmail();
//    String getPassword();
}
