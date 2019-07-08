package com.example.firstapp.mvpinterfaces.signupinterfaces;

public interface SignUpView {

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
