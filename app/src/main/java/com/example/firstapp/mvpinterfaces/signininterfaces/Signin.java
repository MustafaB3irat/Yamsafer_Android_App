package com.example.firstapp.mvpinterfaces.signininterfaces;

import androidx.fragment.app.FragmentActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;

public interface Signin {
    interface SigninModel {

        FirebaseUser getCurrentUser();
        void signInUser(String email, String password);
        LoginManager getFacebookLoginManager();
        PhoneAuthProvider getPhoneProvider();
        FirebaseAuth getFirebaseAuth();
        GoogleSignInClient getGoogleClient();
        CallbackManager getFacebookCallBackManager();
        void initFacebookLogin();
        void checkUser(String email, String password);
        void getGoogleClient(GoogleSignInClient googleSignInClient);

    }

    interface SigninPresenter {


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

    interface SigninView {

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
}
