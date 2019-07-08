package com.example.firstapp.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.firstapp.R;
import com.example.firstapp.customListeners.MyTextWatcher;
import com.example.firstapp.databinding.SignUpBinding;
import com.example.firstapp.mvpinterfaces.signupinterfaces.SignUpPresenter;
import com.example.firstapp.mvpinterfaces.signupinterfaces.SignUpView;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements SignUpView {

    private SignUpBinding signUpBinding;
    private SignUpPresenter signUpPresenter;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = DataBindingUtil.setContentView(this, R.layout.sign_up);
        signUpPresenter = new com.example.firstapp.Presenters.signuppresenters.SignUpPresenter(this);
    }


    @Override
    protected void onStart() {
        super.onStart();

        initProgressDialog();

        initSignUpButton();

        initEntriesListeners();

        initGoToSignIn();
    }

    @Override
    public void setSignUpStatus(boolean isSuccess) {

        if (isSuccess) {
            Toast.makeText(this, R.string.account_created, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.account_failed, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void setEmailSentStatus(boolean isSuccess) {

        if (isSuccess) {
            Toast.makeText(this, R.string.activation_link_sent, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("from_signup" , "To Sign in");
            startActivity(intent);
             finish();

        } else {
            Toast.makeText(this, R.string.activation_link_failed, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wait a moment");//Send to String.xml

    }

    @Override
    public void initSignUpButton() {

        signUpBinding.signUp.setOnClickListener(view -> {

            signUpPresenter.signUp(signUpBinding.email.getText().toString(), signUpBinding.password.getText().toString(), progressDialog);

        });
    }


    @Override
    public void initEntriesListeners() {

        signUpBinding.email.addTextChangedListener(new MyTextWatcher(() -> {
            signUpBinding.emailError.setText("");
        }));
        signUpBinding.password.addTextChangedListener(new MyTextWatcher(() -> {
            signUpBinding.passowrdError.setText("");
        }));
    }


    @Override
    public void initGoToSignIn() {

        signUpBinding.signin.setOnClickListener(e -> {

            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);

        });
    }

    @Override
    public void emailFieldEmpty(boolean isEmpty) {

        if (isEmpty)
            signUpBinding.emailError.setText(R.string.empty_email);
    }

    @Override
    public void passwordFieldEmpty(boolean isEmpty) {


        if (isEmpty)
            signUpBinding.passowrdError.setText(R.string.empty_password);

    }

    @Override
    public void wrongFormatEmail(boolean format) {

        if (format)
            signUpBinding.emailError.setText(R.string.wrongFormatEmail);

    }

    @Override
    public void wrongFormatPassword(boolean format) {

        if (format)
            signUpBinding.passowrdError.setText(R.string.wrongFormatPassword);

    }

    @Override
    public void alreadyExistEmail(boolean exists) {

        if (exists)
            signUpBinding.emailError.setText(R.string.existsEmail);
    }

    @Override
    public void isUserAddedToDatabase(boolean isAdded) {

        if (isAdded) {
            Toast.makeText(this, R.string.email_activated_welcome_message, Toast.LENGTH_SHORT).show();
        }

    }

}
