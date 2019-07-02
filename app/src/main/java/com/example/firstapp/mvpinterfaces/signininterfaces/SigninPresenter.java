package com.example.firstapp.mvpinterfaces.signininterfaces;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseUser;

public interface SigninPresenter {


    void signInUser(String email , String password);
    FirebaseUser getCurrentUser();
    void signOut();
    void signOutGoogle();
    void signOutFacebook();
    LoginManager getFacebookLoginManager();

    GoogleSignInAccount checkIfUserSignedInGoogle();

    GoogleSignInClient getGoogleClient();

    CallbackManager getFacebookCallBackManager();
    FacebookCallback<LoginResult> getFacebookLoginResult();

}
