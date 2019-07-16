package com.example.firstapp.models;

import android.util.Log;

import com.example.firstapp.models.data.UserProfile;
import com.example.firstapp.mvpinterfaces.chat.SearchUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Source;


import java.util.ArrayList;
import java.util.List;

public class SearchUsersModel implements SearchUser.SearchUsersModel {

    private FirebaseFirestore database;
    private FirebaseAuth firebaseAuth;
    private CollectionReference usersCollection;
    private DocumentReference currentUser;
    private List<UserProfile> chatters = new ArrayList<>();
    private ListenerRegistration listenerRegistration;

    private SearchUser.SearchUsersPresenter presenter;

    public SearchUsersModel(SearchUser.SearchUsersPresenter presenter) {
        this.presenter = presenter;

        database = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        usersCollection = database.collection("users");

        if (firebaseAuth.getCurrentUser() != null)
            currentUser = database.collection("users").document(firebaseAuth.getCurrentUser().getUid());
    }


    public SearchUsersModel() {

    }

    @Override
    public void getContactsByName(String name) {


        listenerRegistration = usersCollection.addSnapshotListener((queryDocumentSnapshots, e) -> {

            if (queryDocumentSnapshots.getMetadata().isFromCache()) {

                Log.d("cached", "here");
                getDataFromCache(name);


            } else {
                Log.d("Firesotre", "here");
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
                Log.d("Avatar", i.get("avatar").toString());
                if (chatter.getName().toLowerCase().matches(".*" + name + ".*"))
                    chatters.add(chatter);
            }

            listenerRegistration.remove();

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

                if (chatter.getName().toLowerCase().matches(".*" + name + ".*"))
                    chatters.add(chatter);
            }


            listenerRegistration.remove();

            presenter.setContacts(chatters);

        });


    }
}
