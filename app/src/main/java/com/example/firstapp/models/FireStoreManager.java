package com.example.firstapp.models;

import com.example.firstapp.mvpinterfaces.FirestoreManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FireStoreManager implements FirestoreManager {


    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    public void saveToken(String token) {

        if (firebaseAuth.getCurrentUser() != null) {
            Map<String, Object> tokenContainer = new HashMap<>();

            tokenContainer.put("tokenid", token);

            firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update(tokenContainer);
        }

    }
}
