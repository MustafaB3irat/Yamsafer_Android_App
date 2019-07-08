package com.example.firstapp.Presenters.singinpresenters;


import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.example.firstapp.mvpinterfaces.signininterfaces.SigninModel;
import com.example.firstapp.mvpinterfaces.signininterfaces.SigninView;
import com.example.firstapp.views.fragments.SignInFragment;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.regex.Pattern;

public class SigninPresenter implements com.example.firstapp.mvpinterfaces.signininterfaces.SigninPresenter {

    private SigninView signInFragment;
    private SigninModel model;


    //GoogleSignInOption
    private GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
    //SignInClient
    private GoogleSignInClient googleSignInClient;


    public SigninPresenter(SignInFragment signInFragment) {
        this.signInFragment = signInFragment;
        googleSignInClient = GoogleSignIn.getClient(signInFragment.getActivity(), googleSignInOptions);
        this.model = new com.example.firstapp.models.SigninModel(this);
        model.getGoogleClient(googleSignInClient);
    }


    @Override
    public FirebaseUser getCurrentUser() {
        return model.getCurrentUser();
    }

    @Override
    public void signInUser(String Email, String password) {

        model.signInUser(Email, password);

    }


    @Override
    public LoginManager getFacebookLoginManager() {
        return model.getFacebookLoginManager();
    }


    @Override
    public PhoneAuthProvider getPhoneProvider() {
        return model.getPhoneProvider();
    }

    @Override
    public FirebaseAuth getFirebaseAuth() {
        return model.getFirebaseAuth();
    }


    @Override
    public GoogleSignInAccount checkIfUserSignedInGoogle() {
        return GoogleSignIn.getLastSignedInAccount(signInFragment.getActivity());
    }

    @Override
    public GoogleSignInClient getGoogleClient() {
        return model.getGoogleClient();
    }


    @Override
    public CallbackManager getFacebookCallBackManager() {
        return model.getFacebookCallBackManager();
    }

    @Override
    public void initFacebookLogin() {
        model.initFacebookLogin();
    }


    @Override
    public FacebookCallback<LoginResult> getFacebookLoginResult() {

        FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                AccessToken accessToken = loginResult.getAccessToken();
                handleFacebookAccessToken(accessToken);

            }

            @Override
            public void onCancel() {


            }

            @Override
            public void onError(FacebookException error) {

            }
        };

        return facebookCallback;
    }

    @Override
    public void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        model.getFirebaseAuth().signInWithCredential(credential).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                FirebaseUser user = model.getFirebaseAuth().getCurrentUser();
                signInFragment.getUserInfoFromFacebook(user.getDisplayName(), user.getEmail(), user.getUid());
                signInFragment.getSignInState(true);

            } else {

                Log.d("Error", task.getException().getMessage());

            }

        });
    }

    @Override
    public void isUserVerified(boolean isVerified) {

        signInFragment.isUserVerified(isVerified);

    }

    @Override
    public void checkUser(String email, String password) {

        model.checkUser(email, password);

    }


    @Override
    public void validate(String email, String password) {

        boolean isValid = true;

        if (TextUtils.isEmpty(email)) {
            signInFragment.emailIsEmpty(true);
            isValid = false;
        }

        if (TextUtils.isEmpty(password)) {

            signInFragment.passwordisEmpty(true);

            isValid = false;
        }

        if (!isValid) {
            signInFragment.getSignInState(false);
            signInFragment.isValidEmailOrPassword(isValid);
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signInFragment.wrongFormatEmail(true);
            isValid = false;
        }
        if (!Pattern.compile("^[A-Z][A-Za-z0-9_\\)\\(\\*\\&\\^\\%\\$\\#@!]{9}$").matcher(password).find()) {

            signInFragment.wrongFormatPassword(true);
            isValid = false;
        }

        if (!isValid)
            signInFragment.getSignInState(false);


        signInFragment.isValidEmailOrPassword(isValid);

    }

    @Override
    public void getSignInState(boolean isSignedIn) {

        if (isSignedIn)
            signInFragment.getSignInState(true);

    }

    @Override
    public void wrongEmailOrPassword(boolean isWrong) {

        if (isWrong)
            signInFragment.wrongEmailOrPassword(true);


    }


}
