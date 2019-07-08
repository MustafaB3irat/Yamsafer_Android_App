package com.example.firstapp.mvpinterfaces.signininterfaces;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public interface SigninPresenter {


    void signInUser(String email, String password);

    FirebaseUser getCurrentUser();

    LoginManager getFacebookLoginManager();

    PhoneAuthProvider getPhoneProvider();

    FirebaseAuth getFirebaseAuth();

    GoogleSignInAccount checkIfUserSignedInGoogle();

    GoogleSignInClient getGoogleClient();

    CallbackManager getFacebookCallBackManager();

    void initFacebookLogin();

    FacebookCallback<LoginResult> getFacebookLoginResult();

    void checkUser(String email, String password);

    void validate(String email, String password);

    void getSignInState(boolean isSignedIn);

    void wrongEmailOrPassword(boolean isWrong);
    void handleFacebookAccessToken(AccessToken token);

    void isUserVerified(boolean isVerified);


}
