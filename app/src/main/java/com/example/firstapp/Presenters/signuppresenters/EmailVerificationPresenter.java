package com.example.firstapp.Presenters.signuppresenters;

import androidx.annotation.NonNull;

import com.example.firstapp.mvpinterfaces.signupinterfaces.EmailVerificationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class EmailVerificationPresenter implements com.example.firstapp.mvpinterfaces.signupinterfaces.EmailVerificationPresenter {


    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    //private DatabaseReference reference;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private EmailVerificationView verificationView;


    public EmailVerificationPresenter(EmailVerificationView verificationView) {
        this.verificationView = verificationView;
    }

    @Override
    public boolean completeSignup() {
        String id = null;

        if (firebaseAuth.getCurrentUser() != null) {

            id = firebaseAuth.getInstance().getCurrentUser().getUid();

            HashMap<String, String> userTupple = new HashMap<>();

            userTupple.put("Uid", id);

            userTupple.put("Email", firebaseAuth.getInstance().getCurrentUser().getEmail());

            firebaseFirestore.collection("users").document(id).set(userTupple).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {
                    verificationView.isUserAddedToDatabase(true);
                } else {
                    verificationView.isUserAddedToDatabase(false);
                }

            });

        }

        return true;
    }

    @Override
    public void checkUser() {
        FirebaseAuth.getInstance().getCurrentUser().reload().addOnCompleteListener(task -> verificationView.isUserVerified(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()));

    }
}
