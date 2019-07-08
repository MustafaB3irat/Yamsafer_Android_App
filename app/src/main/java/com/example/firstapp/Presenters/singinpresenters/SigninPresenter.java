package com.example.firstapp.Presenters.singinpresenters;


import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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

    //Firebase  Auth Instance
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    //GoogleSignInOption
    private GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
    //SignInClient
    private GoogleSignInClient googleSignInClient;

    //Phone Sign in
    private PhoneAuthCredential phoneAuthCredential;

    public SigninPresenter(SignInFragment signInFragment) {
        this.signInFragment = signInFragment;
        googleSignInClient = GoogleSignIn.getClient(signInFragment.getActivity(), googleSignInOptions);
    }


    @Override
    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    @Override
    public void signInUser(String Email, String password) {

        firebaseAuth.signInWithEmailAndPassword(Email, password).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                signInFragment.getSignInState(true);

            } else {
                signInFragment.wrongEmailOrPassword(true);
            }


        });

    }
    private LoginManager loginManager = LoginManager.getInstance();


    @Override
    public LoginManager getFacebookLoginManager() {
        return loginManager;
    }


    @Override
    public PhoneAuthProvider getPhoneProvider() {
        return PhoneAuthProvider.getInstance();
    }

    @Override
    public FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }


    @Override
    public GoogleSignInAccount checkIfUserSignedInGoogle() {
        return GoogleSignIn.getLastSignedInAccount(signInFragment.getActivity());
    }

    @Override
    public GoogleSignInClient getGoogleClient() {
        return googleSignInClient;
    }

    //Facebook instance
    private CallbackManager callbackManager = CallbackManager.Factory.create();


    @Override
    public CallbackManager getFacebookCallBackManager() {
        return callbackManager;
    }

    @Override
    public void initFacebookLogin() {
        getFacebookLoginManager().registerCallback(getFacebookCallBackManager(), getFacebookLoginResult());
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

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                signInFragment.getUserInfoFromFacebook(user.getDisplayName(), user.getEmail(), user.getUid());
                signInFragment.getSignInState(true);

            } else {

                Log.d("Error", task.getException().getMessage());

            }

        });
    }

    @Override
    public void checkUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task12 -> {


            FirebaseAuth.getInstance().getCurrentUser().reload().addOnCompleteListener(task -> {


                signInFragment.isUserVerified(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified());

                firebaseAuth.signOut();


            });


        });


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


}
