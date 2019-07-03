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

            //reference = FirebaseDatabase.getInstance().getReference("Users").child(id);

            HashMap<String, String> userTupple = new HashMap<>();

            userTupple.put("Uid", id);
            // userTupple.put("Name", firebaseAuth.getCurrentUser().getDisplayName());
            userTupple.put("Email", firebaseAuth.getInstance().getCurrentUser().getEmail());
            //  userTupple.put("Phone" , firebaseAuth)


//            reference.setValue(userTupple).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if (task.isSuccessful()) {
//                        verificationView.isUserAddedToDatabase(true);
//                    } else {
//                        verificationView.isUserAddedToDatabase(false);
//                    }
//                }
//            });
//
            firebaseFirestore.collection("users").document(id).set(userTupple).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        verificationView.isUserAddedToDatabase(true);
                    } else {
                        verificationView.isUserAddedToDatabase(false);
                    }

                }
            });

        }

        return true;
    }

    @Override
    public void checkUser() {
        FirebaseAuth.getInstance().getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                verificationView.isUserVerified(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified());

            }
        });

    }
}
