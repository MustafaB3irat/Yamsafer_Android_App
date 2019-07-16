package com.example.firstapp.views.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.firstapp.R;
import com.example.firstapp.customListeners.MyTextWatcher;
import com.example.firstapp.databinding.SignInBinding;
import com.example.firstapp.mvpinterfaces.signininterfaces.Signin;
import com.example.firstapp.views.Dialogs.InternationalPhoneDialog;
import com.example.firstapp.views.MainActivity;
import com.example.firstapp.views.SignUpActivity;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

public class SignInFragment extends Fragment implements Signin.SigninView {

    private SignInBinding signInBinding;
    private Signin.SigninPresenter presenter;
    private ProgressDialog progressDialog;
    private LoginButton loginButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        signInBinding = DataBindingUtil.inflate(inflater, R.layout.sign_in, container, false);
        presenter = new com.example.firstapp.Presenters.singinpresenters.SigninPresenter(this);

        return signInBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initEntriesListeners();

        signInWithEmailAndPassword();

        signInViaGoogle();

        facebookLogin();

        signInViaPhone();

        onClickSignUp();

    }

    @Override
    public void onClickSignUp() {

        signInBinding.signup.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SignUpActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

    }


    @Override
    public void signInWithEmailAndPassword() {

        signInBinding.login.setOnClickListener(view -> {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getContext().getString(R.string.signing_in_waiting_stmt));
            progressDialog.show();
            presenter.validate(signInBinding.email.getText().toString(), signInBinding.password.getText().toString());
        });
    }

    @Override
    public void facebookLogin() {

        signInBinding.facebook.setOnClickListener(view -> {
            loginButton = new LoginButton(getActivity());
            loginButton.setPermissions(Arrays.asList("email", "public_profile"));
            presenter.initFacebookLogin();
            loginButton.performClick();
        });

    }


    @Override
    public void getSignInState(boolean isSignedIn) {

        if (progressDialog != null)
            progressDialog.dismiss();
        if (isSignedIn) {

            ((MainActivity) getActivity()).switchFragmentsById(R.id.hotel);
        }
    }

    @Override
    public void getUserInfoFromFacebook(String name, String email, String id) {

        Toast.makeText(getContext(), getContext().getString(R.string.welcome) +" "+ name, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void signInViaGoogle() {


        signInBinding.google.setOnClickListener(view -> {
            if (presenter.getCurrentUser() == null) {
                Intent signInIntent = presenter.getGoogleClient().getSignInIntent();
                startActivityForResult(signInIntent, SignInFragment.RequestCode);
            } else {
                Toast.makeText(getActivity(), R.string.already_signedin, Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent sample) {


        super.onActivityResult(requestCode, resultCode, sample);

        presenter.getFacebookCallBackManager().onActivityResult(requestCode, resultCode, sample);

        if (requestCode == SignInFragment.RequestCode) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(sample);

            onSuccessGoogleSignin(task);


        }

    }

    @Override
    public GoogleSignInAccount onSuccessGoogleSignin(Task<GoogleSignInAccount> completedTask) {

        try {


            GoogleSignInAccount account = completedTask.getResult();
//            ((MainActivity) getActivity()).activityMainBinding.hotel.performClick();
            Toast.makeText(getContext(), getContext().getString(R.string.welcome) + account.getDisplayName(), Toast.LENGTH_SHORT).show();

            getSignInState(true);


            return account;
        } catch (Exception e) {

            Toast.makeText(getContext(), R.string.Error, Toast.LENGTH_SHORT).show();

        }

        return null;
    }

    @Override
    public void signInViaPhone() {

        signInBinding.phone.setOnClickListener(view -> {
            InternationalPhoneDialog phoneDialog = new InternationalPhoneDialog();
            phoneDialog.show(getFragmentManager(), getContext().getString(R.string.Verify_Phone_Number));
        });
    }

    @Override
    public void isUserVerified(boolean isVerified) {

        if (isVerified) {
            presenter.signInUser(signInBinding.email.getText().toString(), signInBinding.password.getText().toString());
        } else {
            Toast.makeText(getContext(), R.string.please_verify_Email, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void emailIsEmpty(boolean isEmpty) {

        if (isEmpty)
            signInBinding.emailError.setText(R.string.empty_email);

    }

    @Override
    public void passwordisEmpty(boolean isEmpty) {

        if (isEmpty)
            signInBinding.passowrdError.setText(R.string.empty_password);


    }

    @Override
    public void wrongFormatEmail(boolean format) {

        if (format)
            signInBinding.emailError.setText(R.string.wrongFormatEmail);

    }

    @Override
    public void wrongFormatPassword(boolean format) {

        if (format)
            signInBinding.passowrdError.setText(R.string.wrongFormatPassword);

    }

    @Override
    public void wrongEmailOrPassword(boolean isWrong) {

        if (isWrong)
            signInBinding.passowrdError.setText(R.string.Wrong_email_or_pass);

    }

    @Override
    public void isValidEmailOrPassword(boolean isValid) {

        if (isValid) {
            presenter.checkUser(signInBinding.email.getText().toString(), signInBinding.password.getText().toString());
        }

    }

    @Override
    public void initEntriesListeners() {

        signInBinding.email.addTextChangedListener(new MyTextWatcher(() -> {
            signInBinding.emailError.setText("");
        }));
        signInBinding.password.addTextChangedListener(new MyTextWatcher(() -> {
            signInBinding.passowrdError.setText("");
        }));

    }


}
