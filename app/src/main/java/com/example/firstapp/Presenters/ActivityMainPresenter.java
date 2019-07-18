package com.example.firstapp.Presenters;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.firstapp.models.MainModel;
import com.example.firstapp.models.data.UserProfile;
import com.example.firstapp.mvpinterfaces.Main;
import com.example.firstapp.views.MainActivity;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class ActivityMainPresenter implements Main.ActivityMainPresenter {


    private MainActivity activity;
    private Main.MainActivityModel model;

    private GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
    private GoogleSignInClient googleSignInClient;

    private FirebaseAnalytics firebaseAnalytics;
    private final static String CHAT_BUTTON_CLICK_EVENT = "chat_button_click_event";

    public ActivityMainPresenter(MainActivity activity) {
        this.activity = activity;
        googleSignInClient = GoogleSignIn.getClient(activity, googleSignInOptions);
        firebaseAnalytics = FirebaseAnalytics.getInstance(activity);
        model = new MainModel(this);
    }

    @Override
    public boolean isUserSignedIn() {

        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }


    @Override
    public void signout() {

        try {

            if (AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired()) {
                signoutFacebook();
            } else if (googleSignInClient != null) {
                signoutGoogle();
            }

            FirebaseAuth.getInstance().signOut();

            activity.getSignOutState(true);

        } catch (Exception e) {
            activity.getSignOutState(false);

        }

    }

    @Override
    public void signoutFacebook() {

        LoginManager.getInstance().logOut();

    }

    @Override
    public void signoutGoogle() {
        googleSignInClient.signOut();

    }

    @Override
    public void initUserProfile() {

        if (AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired()) {

            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(),
                    (object, response) -> {
                        activity.getUserProfile(getFacebookData(object));
                    });
            request.executeAsync();
        } else if (GoogleSignIn.getLastSignedInAccount(activity) != null) {

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(activity);
            activity.getUserProfile(new UserProfile(acct.getDisplayName(), acct.getEmail(), acct.getPhotoUrl().toString(), "", null));
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            activity.getUserProfile(new UserProfile(user.getEmail(), "", "", "", null));
        }


    }


    @Override
    public UserProfile getFacebookData(JSONObject object) {

        UserProfile user_profile = new UserProfile();

        try {

            String id = object.getString("id");
            URL profile_pic;
            try {
                profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?type=large");

                user_profile.setAvatar(profile_pic.toString());

                user_profile.setName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

                user_profile.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());

                user_profile.setId(id);

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }


        } catch (Exception e) {
            Log.d("Error", "BUNDLE Exception : " + e.toString());
        }

        return user_profile;
    }

    @Override
    public void logChatButtonClickedEvent() {
        Bundle bundle = new Bundle();
        bundle.putString("test", "test");
        firebaseAnalytics.logEvent(CHAT_BUTTON_CLICK_EVENT, bundle);
    }

    @Override
    public void shareApp() {
        model.shareApp();
    }

    @Override
    public void setIntentFromDynamicLink(Intent intent) {
        activity.shareApp(intent);
    }
}
