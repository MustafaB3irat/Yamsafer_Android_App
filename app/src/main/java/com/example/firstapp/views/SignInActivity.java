package com.example.firstapp.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.firstapp.R;
import com.example.firstapp.databinding.SignInBinding;
import com.example.firstapp.mvpinterfaces.signininterfaces.SigninPresenter;
import com.example.firstapp.mvpinterfaces.signininterfaces.SigninView;
import com.example.firstapp.views.Dialogs.InternationalPhoneDialog;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

public class SignInActivity extends AppCompatActivity implements SigninView {

    private SignInBinding signInBinding;
    private SigninPresenter presenter;
    private ProgressDialog progressDialog;
    private LoginButton loginButton;
    private boolean google_signin = false;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        signInBinding = DataBindingUtil.setContentView(this, R.layout.sign_in);
        presenter = new com.example.firstapp.Presenters.singinpresenters.SigninPresenter(this);

        signIn(presenter, this);
        signOut(presenter);

        backToHome();

    }

    @Override
    protected void onStart() {
        super.onStart();

        signInViaGoogle(this);

        facebookLogin(this);

        signInViaPhone();

        onClickSignUp(this);

    }

    private void onClickSignUp(final AppCompatActivity activity) {

        signInBinding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void signIn(final SigninPresenter presenter, final AppCompatActivity view) {

        signInBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = new ProgressDialog(view.getContext());
                progressDialog.setMessage("Signing in ....");
                progressDialog.show();

                presenter.signInUser(signInBinding.email.getText().toString(), signInBinding.password.getText().toString());


            }
        });
    }

    private void facebookLogin(final AppCompatActivity activity) {

        signInBinding.facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton = new LoginButton(activity);
                loginButton.setPermissions(Arrays.asList("email"));
                presenter.getFacebookLoginManager().registerCallback(presenter.getFacebookCallBackManager(), presenter.getFacebookLoginResult());
                loginButton.performClick();
            }
        });

    }


    private void signOut(final SigninPresenter presenter) {

        signInBinding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (presenter.getFacebookLoginManager() != null) {
                    presenter.signOutFacebook();
                } else if (google_signin)
                    presenter.signOutGoogle();
                else
                    presenter.signOut();


            }
        });

    }

    private void backToHome() {
        signInBinding.hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void getSigninState(boolean state) {

        if (!TextUtils.isEmpty(signInBinding.email.getText()))
            progressDialog.dismiss();

        if (state) {
            signInBinding.signImage.setActivated(true);
            signInBinding.SignText.setText("Sign Out");

        } else {
            Toast.makeText(this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void getSignOutState(boolean state) {

        if (state) {
            signInBinding.signImage.setActivated(false);
            signInBinding.SignText.setText("Sign in");
        } else {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void getUserInfoFromFacebook(String name, String email, String id) {

        signInBinding.SignText.setText("Sign Out Facebook");
        Toast.makeText(this, "Welcome " + name, Toast.LENGTH_SHORT).show();

    }

    private void signInViaGoogle(final AppCompatActivity activity) {


        signInBinding.google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (presenter.getCurrentUser() == null) {
                    Intent signInIntent = presenter.getGoogleClient().getSignInIntent();
                    startActivityForResult(signInIntent, SignInActivity.RequestCode);
                } else {
                    Toast.makeText(activity, "Already Signed In!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent sample) {


        super.onActivityResult(requestCode, resultCode, sample);

        presenter.getFacebookCallBackManager().onActivityResult(requestCode, resultCode, sample);

        if (requestCode == SignInActivity.RequestCode) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(sample);

            onSuccessGoogleSignin(task);


        }

    }


    private GoogleSignInAccount onSuccessGoogleSignin(Task<GoogleSignInAccount> completedTask) {

        try {


            GoogleSignInAccount account = completedTask.getResult();
            google_signin = true;

            Toast.makeText(this, "Hello " + account.getDisplayName(), Toast.LENGTH_SHORT).show();

            signInBinding.SignText.setText("Sign Out");
            signInBinding.signin.setActivated(true);

            return account;
        } catch (Exception e) {

            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();

        }

        return null;
    }


    private void signInViaPhone() {

        signInBinding.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InternationalPhoneDialog phoneDialog = new InternationalPhoneDialog();
                phoneDialog.show(getSupportFragmentManager(), "Verify By Phone Number");
            }
        });
    }

}
