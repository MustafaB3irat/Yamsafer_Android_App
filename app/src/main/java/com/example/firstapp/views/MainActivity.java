package com.example.firstapp.views;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.firstapp.R;
import com.example.firstapp.databinding.ActivityMainBinding;
import com.example.firstapp.databinding.SidebarHeaderBinding;
import com.example.firstapp.fragmentsadapters.FragementAdpater;
import com.example.firstapp.models.data.UserProfile;
import com.example.firstapp.mvpinterfaces.ActivityMainPresenter;
import com.example.firstapp.mvpinterfaces.MainView;
import com.example.firstapp.views.fragments.ChatFragment;
import com.example.firstapp.views.fragments.FlightsFragment;
import com.example.firstapp.views.fragments.HotelsListFragment;
import com.example.firstapp.views.fragments.MainFragments;
import com.example.firstapp.views.fragments.SignInFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements MainView {

    public ActivityMainBinding activityMainBinding;
    private FragementAdpater fragementAdpater;
    private ActivityMainPresenter presenter;
    private View sidebar_header;
    private SidebarHeaderBinding sidebarHeaderBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.nav.inflateMenu(R.menu.bottom_nav);
        sidebar_header = activityMainBinding.sidebar.inflateHeaderView(R.layout.sidebar_header);


    }


    @Override
    protected void onStart() {

        presenter = new com.example.firstapp.Presenters.ActivityMainPresenter(this);

        checkIfExtrasFromSignUpActivityExists();

        activityMainBinding.sidebar.setNavigationItemSelectedListener(this);

        initSideBar();

        super.onStart();


        initFragments();


        fragmentSwitcher();


    }

    @Override
    public void initFragments() {
        fragementAdpater = new FragementAdpater(getSupportFragmentManager());
        fragementAdpater.addFragment(new MainFragments());
        fragementAdpater.addFragment(new HotelsListFragment());
        fragementAdpater.addFragment(new SignInFragment());
        fragementAdpater.addFragment(new FlightsFragment());
        fragementAdpater.addFragment(new ChatFragment());
        activityMainBinding.fragments.setAdapter(fragementAdpater);


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

        }
    }

    @Override
    public void redirectUser() {

        if (presenter.isUserSignedIn()) {

            activityMainBinding.sidebar.setVisibility(View.VISIBLE);
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
                activityMainBinding.fragments.setCurrentItem(4);

            }
            break;


        }

        return true;
    }
}
