package com.example.firstapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.firstapp.R;
import com.example.firstapp.databinding.ActivityMainBinding;
import com.example.firstapp.databinding.SidebarHeaderBinding;
import com.example.firstapp.fragmentsadapters.FragementAdpater;
import com.example.firstapp.models.data.UserProfile;
import com.example.firstapp.mvpinterfaces.Main;
import com.example.firstapp.views.fragments.SearchUsersFragment;
import com.example.firstapp.views.fragments.FlightsFragment;
import com.example.firstapp.views.fragments.HotelsListFragment;
import com.example.firstapp.views.fragments.MainFragments;
import com.example.firstapp.views.fragments.SignInFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements Main.MainView {

    public ActivityMainBinding activityMainBinding;
    private FragementAdpater fragementAdpater;
    private Main.ActivityMainPresenter presenter;
    private View sidebar_header;
    private SidebarHeaderBinding sidebarHeaderBinding;
    public static Map<String, Object> defaultValuesForRemoteConfig = new HashMap<>();
    private FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    private static String REMOTE_CONFIG_TEST_TEXT = "remote_config_test_text";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.nav.inflateMenu(R.menu.bottom_nav);
        sidebar_header = activityMainBinding.sidebar.inflateHeaderView(R.layout.sidebar_header);

        presenter = new com.example.firstapp.Presenters.ActivityMainPresenter(this);

        checkIfExtrasFromSignUpActivityExists();

        activityMainBinding.sidebar.setNavigationItemSelectedListener(this);

        initSideBar();

        initFragments();

        fragmentSwitcher();

        defaultValuesForRemoteConfig.put(REMOTE_CONFIG_TEST_TEXT, "Wassaab");
        initiateRemoteConfig();
        trackFloatingActionButtonColorValue();


    }


    @Override
    public void initFragments() {
        fragementAdpater = new FragementAdpater(getSupportFragmentManager());
        fragementAdpater.addFragment(new MainFragments());
        fragementAdpater.addFragment(new HotelsListFragment());
        fragementAdpater.addFragment(new SignInFragment());
        fragementAdpater.addFragment(new FlightsFragment());
        fragementAdpater.addFragment(new SearchUsersFragment());
        activityMainBinding.fragments.setAdapter(fragementAdpater);

        activityMainBinding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


    }

    @Override
    public void fragmentSwitcher() {

        activityMainBinding.nav.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {

                case R.id.hotel: {
                    activityMainBinding.fragments.setCurrentItem(0, true);
                }
                break;


                case R.id.flights: {

                    activityMainBinding.fragments.setCurrentItem(3, true);
                }
                break;

            }


            return true;
        });
        activityMainBinding.signin.setOnClickListener(e -> {

            redirectUser();

        });


    }

    @Override
    public void setSearchEditTextClickable(EditText editText) {

        editText.setOnClickListener(e -> {
            activityMainBinding.fragments.setCurrentItem(1);
        });

    }

    @Override
    public void checkIfExtrasFromSignUpActivityExists() {

        if (getIntent().getExtras() != null && getIntent().getExtras().getString("from_signup") != null && getIntent().getExtras().getString("from_signup").equals("to sign in")) {

            activityMainBinding.fragments.setCurrentItem(2);

        }
    }

    @Override
    public void switchFragmentsById(int ID) {

        switch (ID) {

            case R.id.hotel: {
                activityMainBinding.fragments.setCurrentItem(0);

            }
            break;

            case R.id.signin: {
                activityMainBinding.fragments.setCurrentItem(2);
            }
            break;

            case R.id.chat: {
                activityMainBinding.fragments.setCurrentItem(4);
            }
            break;

        }
    }

    @Override
    public void redirectUser() {
        presenter.logChatButtonClickedEvent();

        if (presenter.isUserSignedIn()) {

            activityMainBinding.sidebar.setVisibility(View.VISIBLE);
            activityMainBinding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            activityMainBinding.drawer.openDrawer(GravityCompat.START);
            presenter.initUserProfile();
        } else {

            activityMainBinding.fragments.setCurrentItem(2);
        }
    }

    @Override
    public void getSignOutState(boolean isSignedOut) {

        if (isSignedOut) {
            activityMainBinding.drawer.closeDrawer(GravityCompat.START);
            activityMainBinding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            activityMainBinding.fragments.setCurrentItem(2);

        } else {
            Toast.makeText(this, R.string.signout_erro, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getSignInState(boolean isSignedIn) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("android:switcher:" + activityMainBinding.fragments.getId() + ":" + activityMainBinding.fragments.getCurrentItem());
        ((SignInFragment) fragment).getSignInState(isSignedIn);

    }

    @Override
    public void initSideBar() {

        activityMainBinding.sidebar.setVisibility(View.GONE);
    }

    @Override
    public void getUserProfile(UserProfile user_profile) {

        try {
            ((TextView) sidebar_header.findViewById(R.id.email_address)).setText(user_profile.getEmail());
            ((TextView) sidebar_header.findViewById(R.id.profile_name)).setText(user_profile.getName());
            //((ProfilePictureView) sidebar_header.findViewById(R.id.profile_pic)).setProfileId(user_profile.getId());

            // Bitmap mIcon = BitmapFactory.decodeStream(new URL(user_profile.getImage_url()).openConnection().getInputStream());
            Glide.with((sidebar_header.findViewById(R.id.profile_pic)).getContext()).load(user_profile.getAvatar()).into(((CircleImageView) sidebar_header.findViewById(R.id.profile_pic)));
        } catch (Exception e) {

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("android:switcher:" + activityMainBinding.fragments.getId() + ":" + activityMainBinding.fragments.getCurrentItem());
        fragment.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onBackPressed() {


        if (activityMainBinding.drawer.isDrawerOpen(GravityCompat.START)) {
            activityMainBinding.drawer.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.signout: {
                presenter.signout();
                switchFragmentsById(R.id.signin);
                activityMainBinding.drawer.closeDrawer(GravityCompat.START);

            }
            break;

            case R.id.chat: {

                activityMainBinding.drawer.closeDrawer(GravityCompat.START);
                switchFragmentsById(R.id.chat);

            }
            break;

            case R.id.share: {
                activityMainBinding.drawer.closeDrawer(GravityCompat.START);
                presenter.shareApp();

            }
            break;


        }

        return true;
    }

    @Override
    public void initiateRemoteConfig() {

        FirebaseRemoteConfigSettings remoteConfigSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true).setFetchTimeoutInSeconds(3600)
                .build();

        firebaseRemoteConfig.setConfigSettings(remoteConfigSettings);
        firebaseRemoteConfig.setDefaults(defaultValuesForRemoteConfig);
    }

    @Override
    public void trackFloatingActionButtonColorValue() {

        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(task -> {

//            if (task.isSuccessful())
//                activityMainBinding.remoteConfig.setText(firebaseRemoteConfig.getString(REMOTE_CONFIG_TEST_TEXT));
            // activityMainBinding.signin.setSupportBackgroundTintList(getResources().getColorStateList(Integer.parseInt(firebaseRemoteConfig.getString("floating_action_button"))));
        });

    }

    @Override
    public void shareApp(Intent intent) {
        startActivity(intent);
    }

}
