package com.example.firstapp.models;

import androidx.annotation.NonNull;

import com.example.firstapp.mvpinterfaces.signininterfaces.SigninPresenter;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class SigninModel implements com.example.firstapp.mvpinterfaces.signininterfaces.SigninModel {


    private SigninPresenter presenter;


    public SigninModel(com.example.firstapp.Presenters.singinpresenters.SigninPresenter presenter) {
        this.presenter = presenter;
    }

    //Firebase  Auth Instance
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    //GoogleSignInOption
    private GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
    //SignInClient
    private GoogleSignInClient googleSignInClient;

    //Phone Sign in
    private PhoneAuthCredential phoneAuthCredential;

    private LoginManager loginManager = LoginManager.getInstance();

    //Facebook instance
    private CallbackManager callbackManager = CallbackManager.Factory.create();


    @Override
    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    @Override
    public void signInUser(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                presenter.getSignInState(true);

            } else {

                presenter.wrongEmailOrPassword(true);

            }


        });

    }

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
    public GoogleSignInClient getGoogleClient() {

        return googleSignInClient;
    }

    @Override
    public CallbackManager getFacebookCallBackManager() {
        return callbackManager;
    }

    @Override
    public void initFacebookLogin() {

        getFacebookLoginManager().registerCallback(getFacebookCallBackManager(), presenter.getFacebookLoginResult());

    }


    @Override
    public void checkUser(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task12 -> {


            FirebaseAuth.getInstance().getCurrentUser().reload().addOnCompleteListener(task -> {


                presenter.isUserVerified(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified());

                firebaseAuth.signOut();


            });


        });

    }

    @Override
    public void getGoogleClient(GoogleSignInClient googleSignInClient) {
        this.googleSignInClient = googleSignInClient;
    }
}
