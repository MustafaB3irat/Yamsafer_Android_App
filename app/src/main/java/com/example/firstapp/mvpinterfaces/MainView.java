package com.example.firstapp.mvpinterfaces;

import android.widget.EditText;

import com.example.firstapp.models.data.UserProfile;
import com.google.android.material.navigation.NavigationView;

public interface MainView  extends NavigationView.OnNavigationItemSelectedListener {

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

}
