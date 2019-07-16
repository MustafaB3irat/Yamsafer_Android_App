package com.example.firstapp.models;

import android.app.ProgressDialog;

import com.example.firstapp.mvpinterfaces.signupinterfaces.Signup;
import com.example.firstapp.views.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class SignUpModel implements Signup.SignUpModel {


    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private Signup.SignUpPresenter presenter;

    public SignUpModel(com.example.firstapp.Presenters.signuppresenters.SignUpPresenter presenter) {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        this.presenter = presenter;
    }


    @Override
    public void completeSignUp() {
        String id = null;

        if (firebaseAuth.getCurrentUser() != null) {

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            id = currentUser.getUid();

            HashMap<String, Object> userTupple = new HashMap<>();

            userTupple.put("id", id);

            userTupple.put("email", currentUser.getEmail());

            if (currentUser.getDisplayName() != null) {
                userTupple.put("name", currentUser.getDisplayName());
            } else {
                userTupple.put("name", "User");
            }

            if (currentUser.getPhotoUrl() != null) {
                userTupple.put("avatar", currentUser.getPhotoUrl().toString());
            } else {
                userTupple.put("avatar", "http://s3.amazonaws.com/37assets/svn/765-default-avatar.png");
            }


            firebaseFirestore.collection("users").document(id).set(userTupple).addOnCompleteListener(task -> {

                presenter.isSignUpCompleted(task.isSuccessful());

            });

        }
    }


    @Override
    public void signUp(String email, String password, ProgressDialog progressDialog) {


        if (presenter.validate(email, password)) {


            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                progressDialog.dismiss();

                if (task.isSuccessful()) {

                    presenter.setSignUpStatus(true);

                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task12 -> {
                        FirebaseUser user = firebaseAuth.getCurrentUser();

                        completeSignUp();

                        user.sendEmailVerification().addOnCompleteListener(task1 -> {

                            if (task1.isSuccessful()) {
                                firebaseAuth.signOut();
                                presenter.setEmailSentStatus(true);
                            } else
                                presenter.setEmailSentStatus(false);
                        });
                    });

                } else {

                    try {
                        throw task.getException();

                    } catch (FirebaseAuthUserCollisionException exception) {

                        presenter.alreadyExistEmail(true);


                    } catch (Exception e) {

                        presenter.setSignUpStatus(false);

                    }
                    return;
                }

            });

        } else {

            progressDialog.dismiss();
        }

    }
}
