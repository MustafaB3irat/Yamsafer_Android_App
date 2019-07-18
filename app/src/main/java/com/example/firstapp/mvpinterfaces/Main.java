package com.example.firstapp.mvpinterfaces;

import android.content.Intent;
import android.widget.EditText;

import com.example.firstapp.models.data.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import org.json.JSONObject;

public interface Main {
    interface MainView extends NavigationView.OnNavigationItemSelectedListener {


        void initFragments();

        void fragmentSwitcher();

        void setSearchEditTextClickable(EditText editText);

        void checkIfExtrasFromSignUpActivityExists();

        void switchFragmentsById(int ID);

        void redirectUser();

        void getSignOutState(boolean isSignedOut);

        void getSignInState(boolean isSignedIn);

        void initSideBar();

        void getUserProfile(UserProfile user_profile);

        void initiateRemoteConfig();

        void trackFloatingActionButtonColorValue();

        void shareApp(Intent intent);

    }

    interface ActivityMainPresenter {

        boolean isUserSignedIn();

        void signout();

        void signoutFacebook();

        void signoutGoogle();

        void initUserProfile();

        UserProfile getFacebookData(JSONObject object);

        void logChatButtonClickedEvent();

        void shareApp();

        void setIntentFromDynamicLink(Intent intent);
    }

    interface MainActivityModel {

        void initiateRemoteConfig();

        void fetchNewValues();

        void buildDynamicLink(OnCompleteListener<ShortDynamicLink> onCompleteListener);

        void shareApp();
    }
}
