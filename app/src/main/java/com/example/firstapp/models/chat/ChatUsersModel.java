package com.example.firstapp.models.chat;

import android.text.TextUtils;
import android.util.Log;

import com.example.firstapp.models.data.UserProfile;
import com.example.firstapp.mvpinterfaces.chat.ChatUsersPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class ChatUsersModel implements com.example.firstapp.mvpinterfaces.chat.ChatUsersModel {

    private FirebaseFirestore database;
    private FirebaseAuth firebaseAuth;
    private CollectionReference usersCollection;
    private DocumentReference currentUser;
    private List<UserProfile> chatters = new ArrayList<>();

    private ChatUsersPresenter presenter;

    public ChatUsersModel(ChatUsersPresenter presenter) {
        this.presenter = presenter;

        database = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        usersCollection = database.collection("users");
        currentUser = database.collection("users").document(firebaseAuth.getCurrentUser().getUid());
    }


    public ChatUsersModel() {

    }

    @Override
    public void getContactsByName(String name) {


        usersCollection.addSnapshotListener(MetadataChanges.INCLUDE, (queryDocumentSnapshots, e) -> {

            if (queryDocumentSnapshots.getMetadata().isFromCache()) {

                getDataFromCache(name);

            } else {
                getDataFromFireStore(name);
            }
        });

    }

    @Override
    public void getDataFromFireStore(String name) {

        chatters.clear();


        usersCollection.get().addOnCompleteListener(task1 -> {


            for (QueryDocumentSnapshot i : task1.getResult()) {

                UserProfile chatter = i.toObject(UserProfile.class);
                if (chatter.getName().matches(".*" + name + ".*"))
                    chatters.add(chatter);
            }


            presenter.setContacts(chatters);

        });


    }

    @Override
    public void getDataFromCache(String name) {

        chatters.clear();

        Source source = Source.CACHE;


        usersCollection.get(source).addOnCompleteListener(task -> {


            for (DocumentSnapshot i : task.getResult()) {

                UserProfile chatter = i.toObject(UserProfile.class);

                if (chatter.getName().matches(".*" + name + ".*"))
                    chatters.add(chatter);
            }

            presenter.setContacts(chatters);

        });


    }
}
