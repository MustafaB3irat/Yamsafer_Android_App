package com.example.firstapp.mvpinterfaces.signininterfaces;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

public interface SigninView {

    int RequestCode = 777;

    void getSignInState(boolean isSignedIn);

    FragmentActivity getActivity();

    void signInViaGoogle();

    void signInWithEmailAndPassword();

    void onClickSignUp();

    void facebookLogin();

    GoogleSignInAccount onSuccessGoogleSignin(Task<GoogleSignInAccount> task);

    void signInViaPhone();

    void getUserInfoFromFacebook(String name, String email, String id);

    void isUserVerified(boolean isVerified);
    void emailIsEmpty(boolean isEmpty);
    void passwordisEmpty(boolean isEmpty);
    void wrongFormatEmail(boolean format);
    void wrongFormatPassword(boolean format);
    void wrongEmailOrPassword(boolean isValid);
    void isValidEmailOrPassword(boolean isValid);

    void initEntriesListeners();


}
