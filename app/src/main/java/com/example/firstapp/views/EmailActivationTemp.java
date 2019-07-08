package com.example.firstapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.firstapp.R;
import com.example.firstapp.databinding.ActivationTempBinding;
import com.example.firstapp.mvpinterfaces.signupinterfaces.EmailVerificationPresenter;
import com.example.firstapp.mvpinterfaces.signupinterfaces.EmailVerificationView;
import com.example.firstapp.views.fragments.HotelsListFragment;

public class EmailActivationTemp extends AppCompatActivity implements EmailVerificationView {

    private ActivationTempBinding activationTempBinding;
    private AppCompatActivity activity;
    private EmailVerificationPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        activationTempBinding = DataBindingUtil.setContentView(this, R.layout.activation_temp);
        activity = this;
        presenter = new com.example.firstapp.Presenters.signuppresenters.EmailVerificationPresenter(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        onEmailVerified();
    }

    @Override
    public void onEmailVerified() {
        activationTempBinding.navigateToMain.setOnClickListener(view -> presenter.checkUser());
    }

    @Override
    public void isUserVerified(boolean state) {

        if (state) {
            presenter.completeSignup();
        } else {
            Toast.makeText(activity, R.string.please_verify_Email, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void isUserAddedToDatabase(boolean state) {

        if (state) {
            Toast.makeText(activity, R.string.email_activated_welcome_message, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(activity, HotelsListFragment.class);
            startActivity(intent);
            activity.finish();
        }

    }

//    @Override
//    public String getEmail() {
//        return getIntent().getExtras().getString("email");
//    }
//
//    @Override
//    public String getPassword() {
//        return getIntent().getExtras().getString("password");
//    }
}
