package com.example.firstapp.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.firstapp.R;
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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wait a moment");
        signUp();
        clearErrorFields();

    }

    @Override
    public void getSignUpState(boolean state) {

        if (state) {
            Toast.makeText(this, "Created Account Successfully ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed To Create Account Please Try again! | Or Maybe this email is used ", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void getEmailSentState(boolean state) {

        if (state) {
            Toast.makeText(this, "Activation link sent to your email, please check it out ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, EmailActivationTemp.class);
            startActivity(intent);
           // finish();
        } else {
            Toast.makeText(this, "Failed to send Activation Email ", Toast.LENGTH_SHORT).show();
        }

    }

    private void signUp() {

        signUpBinding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean state = validate(signUpBinding.email.getText().toString(), signUpBinding.password.getText().toString());

                if (state) {
                    signUpPresenter.signUp(signUpBinding.email.getText().toString(), signUpBinding.password.getText().toString(), progressDialog);
                }
            }
        });
    }


    private boolean validate(String email, String password) {

        boolean state = true;

        if (TextUtils.isEmpty(email)) {
            signUpBinding.emailError.setText("*Email is Required");
            state = false;
        }

        if (TextUtils.isEmpty(signUpBinding.password.getText())) {
            signUpBinding.passowrdError.setText("*Password is Required");
            state = false;
        }
        if (!state) {
            return state;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signUpBinding.emailError.setText("Invalid Format of Email");
            state = false;
        }
        if (!Pattern.compile("^[A-Z][A-Za-z0-9_\\)\\(\\*\\&\\^\\%\\$\\#@!]{9}$").matcher(password).find()) {
            signUpBinding.passowrdError.setText("Password must be 9 digits starting with Capital Letter");
            state = false;
        }
        if (!state) {
            return state;
        }


        return true;
    }


    private void clearErrorFields() {

        signUpBinding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!TextUtils.isEmpty(signUpBinding.emailError.getText())) {
                    signUpBinding.emailError.setText("");
                }

            }
        });
        signUpBinding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!TextUtils.isEmpty(signUpBinding.passowrdError.getText())) {
                    signUpBinding.passowrdError.setText("");
                }

            }
        });
    }
}
