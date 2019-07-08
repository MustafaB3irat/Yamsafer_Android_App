package com.example.firstapp.Presenters.signuppresenters;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.util.Patterns;

import com.example.firstapp.mvpinterfaces.signupinterfaces.SignUpView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.regex.Pattern;

public class SignUpPresenter implements com.example.firstapp.mvpinterfaces.signupinterfaces.SignUpPresenter {


    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private SignUpView signUpView;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


    public SignUpPresenter(SignUpView signUpView) {
        this.signUpView = signUpView;

    }

    @Override
    public void signUp(final String email, final String password, final ProgressDialog progressDialog) {


        if (validate(email, password)) {

            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                progressDialog.dismiss();

                if (task.isSuccessful()) {

                    signUpView.setSignUpStatus(true);

                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task12 -> {
                        FirebaseUser user = firebaseAuth.getCurrentUser();

                        completeSignUp();

                        user.sendEmailVerification().addOnCompleteListener(task1 -> {

                            if (task1.isSuccessful()) {
                                firebaseAuth.signOut();
                                signUpView.setEmailSentStatus(true);
                            } else
                                signUpView.setEmailSentStatus(false);
                        });
                    });

                } else {

                    try {
                        throw task.getException();

                    } catch (FirebaseAuthUserCollisionException exception) {

                        signUpView.alreadyExistEmail(true);


                    } catch (Exception e) {

                        signUpView.setSignUpStatus(false);

                    }
                    return;
                }

            });

        }


    }

    @Override
    public boolean validate(String email, String password) {

        boolean isValid = true;

        if (TextUtils.isEmpty(email)) {
            signUpView.emailFieldEmpty(true);
            isValid = false;
        }

        if (TextUtils.isEmpty(password)) {

            signUpView.passwordFieldEmpty(true);

            isValid = false;
        }

        if (!isValid)
            return isValid;

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signUpView.wrongFormatEmail(true);
            isValid = false;
        }
        if (!Pattern.compile("^[A-Z][A-Za-z0-9_\\)\\(\\*\\&\\^\\%\\$\\#@!]{9}$").matcher(password).find()) {

            signUpView.wrongFormatPassword(true);
            isValid = false;
        }

        return isValid;

    }


    @Override
    public boolean completeSignUp() {
        String id = null;

        if (firebaseAuth.getCurrentUser() != null) {

            id = firebaseAuth.getInstance().getCurrentUser().getUid();

            HashMap<String, String> userTupple = new HashMap<>();

            userTupple.put("Uid", id);

            userTupple.put("Email", firebaseAuth.getInstance().getCurrentUser().getEmail());

            firebaseFirestore.collection("users").document(id).set(userTupple).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {
                    signUpView.isUserAddedToDatabase(true);
                } else {
                    signUpView.isUserAddedToDatabase(false);
                }

            });

        }

        return true;
    }
}
