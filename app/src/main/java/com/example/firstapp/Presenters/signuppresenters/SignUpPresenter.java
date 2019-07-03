package com.example.firstapp.Presenters.signuppresenters;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.example.firstapp.mvpinterfaces.signupinterfaces.SignUpView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class SignUpPresenter implements com.example.firstapp.mvpinterfaces.signupinterfaces.SignUpPresenter {


    private FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
    private SignUpView signUpView;

    public SignUpPresenter(SignUpView signUpView) {
        this.signUpView = signUpView;

    }

    @Override
    public void signUp(final String email, final String password, final ProgressDialog progressDialog) {

        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()) {

                    signUpView.getSignUpState(true);

                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful())
                                        signUpView.getEmailSentState(true);

                                    else
                                        signUpView.getEmailSentState(false);
                                }
                            });
                        }
                    });

                } else {
                    signUpView.getSignUpState(false);

//                    try {
//                        throw  task.getException();
//
//                    }catch (FirebaseAuthUserCollisionException exception){
//
//
//
//                    }catch (Exception e){
//
//                    }
//                    return;
                }

            }
        });


    }
}
