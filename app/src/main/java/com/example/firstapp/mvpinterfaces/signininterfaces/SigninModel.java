package com.example.firstapp.mvpinterfaces.signininterfaces;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;

public interface SigninModel {

    FirebaseUser getCurrentUser();
    void signInUser(String email ,String password);
    LoginManager getFacebookLoginManager();
    PhoneAuthProvider getPhoneProvider();
    FirebaseAuth getFirebaseAuth();
    GoogleSignInClient getGoogleClient();
    CallbackManager getFacebookCallBackManager();
    void initFacebookLogin();
    void checkUser(String email , String password);
    void getGoogleClient(GoogleSignInClient googleSignInClient);

}
