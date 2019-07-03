package com.example.firstapp.Presenters.singinpresenters;


import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.firstapp.mvpinterfaces.signininterfaces.SigninView;
import com.example.firstapp.views.Dialogs.InternationalPhoneDialog;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class SigninPresenter implements com.example.firstapp.mvpinterfaces.signininterfaces.SigninPresenter {

    private SigninView signinView;

    //Firebase  Auth Instance
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    //GoogleSignInOption
    private GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
    //SignInClient
    private GoogleSignInClient googleSignInClient;

    //Phone Sign in
    private PhoneAuthCredential phoneAuthCredential;

    public SigninPresenter(SigninView signinView) {
        this.signinView = signinView;
        googleSignInClient = GoogleSignIn.getClient(signinView.getActivity(), googleSignInOptions);
    }


    @Override
    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    @Override
    public void signInUser(String Email, String password) {

        if (TextUtils.isEmpty(Email.trim()) || TextUtils.isEmpty(password.trim())) {
            signinView.getSigninState(false);
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                    signinView.getSigninState(true);
                else signinView.getSigninState(false);

            }
        });
    }


    @Override
    public void signOut() {

        try {
            firebaseAuth.signOut();
            signinView.getSignOutState(true);
        } catch (Exception e) {
            signinView.getSignOutState(false);
        }

    }

    @Override
    public void signOutGoogle() {
        googleSignInClient.signOut();
        firebaseAuth.getInstance().signOut();
        signinView.getSignOutState(true);
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
    public void signOutFacebook() {
        firebaseAuth.signOut();
        loginManager.logOut();
        signinView.getSignOutState(true);
    }

    @Override
    public GoogleSignInAccount checkIfUserSignedInGoogle() {
        return GoogleSignIn.getLastSignedInAccount(signinView.getActivity());
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
    public FacebookCallback<LoginResult> getFacebookLoginResult() {

        FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                AccessToken accessToken = loginResult.getAccessToken();
                handleFacebookAccessToken(accessToken);

//                GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
//
//                    @Override
//                    public void onCompleted(JSONObject object, GraphResponse response) {
//
//                        try {
//                            String name = object.getString("display_name");
//                            String email = object.getString("email");
//
//
//
//                            signinView.getUserInfoFromFacebook(name,email,"");
//
//                        } catch (Exception e) {
//
//                        }
//                    }
//                });
//
//                Bundle bundle = new Bundle();
//
//                bundle.putString("fields", "display_name ,email , uid");
//                graphRequest.setParameters(bundle);
//                graphRequest.executeAsync();

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

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    signinView.getUserInfoFromFacebook(user.getDisplayName(), user.getEmail(), user.getUid());
                    signinView.getSigninState(true);
                } else {

                }

            }
        });
    }


}
