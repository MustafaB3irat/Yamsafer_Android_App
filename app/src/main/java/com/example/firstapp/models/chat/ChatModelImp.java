package com.example.firstapp.models.chat;


import android.content.ContentResolver;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;

import com.example.firstapp.models.data.Message;
import com.example.firstapp.mvpinterfaces.chat.Chat;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class ChatModelImp implements Chat.ChatModel {

    private Chat.ChatPresenter presenter;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore database;
    private final String sender;
    private final static String MESSAGE_COLLECTION = "messages";
    private final static String CHATS_COLLECTION = "chats";
    private final static String CREATED_AT = "created_at";
    private final static String SENDER = "sender";
    private final static String MESSAGE_BODY = "body";
    private final static String MESSAGE_TYPE = "message_type";
    private final static String Receiver = "receiver";
    private String receiverId;
    private DocumentReference chat;
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference storageReference = firebaseStorage.getReference();
   // private Crashlytics crashlytics = Crashlytics.getInstance();


    public ChatModelImp(Chat.ChatPresenter presenter) {
        this.presenter = presenter;
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        sender = firebaseAuth.getCurrentUser().getUid();
    }

    @Override
    public void setCurrentUser() {

        presenter.setCurrentUser(firebaseAuth.getCurrentUser());

    }

    @Override
    public void getMessagesFromChat() {

        chat.collection(MESSAGE_COLLECTION).orderBy("created_at").addSnapshotListener((queryDocumentSnapshots, e) -> {

            getNewestMessages(queryDocumentSnapshots);

        });
    }

    @Override
    public void addMessage(String message, boolean isImage) {

        Map<String, Object> messagesCollection = new HashMap<>();

        OnCompleteListener<Uri> onCompleteListener = task -> {
            if (task.isSuccessful()) {
                messagesCollection.put(CREATED_AT, FieldValue.serverTimestamp());
                messagesCollection.put(SENDER, sender);
                messagesCollection.put(Receiver, receiverId);
                messagesCollection.put(MESSAGE_BODY, task.getResult().toString());
                messagesCollection.put(MESSAGE_TYPE, "1");

                chat.collection(MESSAGE_COLLECTION).document().set(messagesCollection).addOnSuccessListener(aVoid -> {

                    Map<String, Object> temp = new HashMap<>();
                    temp.put("exist", true);
                    chat.set(temp, SetOptions.merge());

                    presenter.messageSent(true);

                    presenter.onMessageSent(true);

                });
            }
        };

        if (isImage) {
            try {
                getImageURIAndUploadIt(message, onCompleteListener);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            messagesCollection.put(CREATED_AT, FieldValue.serverTimestamp());
            messagesCollection.put(SENDER, sender);
            messagesCollection.put(Receiver, receiverId);
            messagesCollection.put(MESSAGE_TYPE, "0");
            messagesCollection.put(MESSAGE_BODY, message);


            chat.collection(MESSAGE_COLLECTION).document().set(messagesCollection).addOnSuccessListener(aVoid -> {

                Map<String, Object> temp = new HashMap<>();
                temp.put("exist", true);
                chat.set(temp, SetOptions.merge());

                presenter.messageSent(true);

                presenter.onMessageSent(true);

            });

        }


    }


    @Override
    public void getNewestMessages(QuerySnapshot queryDocumentSnapshots) {

        List<Message> messages = new ArrayList<>();
        List<DocumentChange> documentChanges;

        documentChanges = queryDocumentSnapshots.getDocumentChanges();

        if (documentChanges.size() >= 1 && documentChanges.size() <= 2 && !documentChanges.get(0).getDocument().get("sender").equals(firebaseAuth.getCurrentUser().getUid()))
            presenter.messageReceived(true);

        for (DocumentChange documentChange : documentChanges) {

            if (documentChange.getDocument().getTimestamp("created_at") != null)

                messages.add(documentChange.getDocument().toObject(Message.class));
        }
        presenter.didLoadMessages(messages);
    }

    @Override
    public void checkWhoInitiatedTheChat(String B) {

        receiverId = B;

        database.collection(CHATS_COLLECTION).document(firebaseAuth.getCurrentUser().getUid() + "-" + B).get().addOnCompleteListener(task -> {

            if (task.isSuccessful() && task.getResult().exists()) {

                chat = database.collection(CHATS_COLLECTION).document(firebaseAuth.getCurrentUser().getUid() + "-" + B);

            } else {
                chat = database.collection(CHATS_COLLECTION).document(B + "-" + firebaseAuth.getCurrentUser().getUid());
            }
            getMessagesFromChat();
        });


    }

    @Override
    public void getFriendsAvatarAndUsername(String id) {

        database.collection("users").document(id).get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                HashMap<String, Object> friendsInfo = new HashMap<>();

                Object username = task.getResult().get("name");
                Object avatar = task.getResult().get("avatar");

                if (username != null)
                    friendsInfo.put("username", username.toString());
                else

                    friendsInfo.put("username", "");


                if (avatar != null)
                    friendsInfo.put("avatar", avatar.toString());
                else
                    friendsInfo.put("avatar", "default");


                presenter.getFriendsAvatarAndUsername(friendsInfo);

            }
        });

    }

    @Override
    public void getImageURIAndUploadIt(String imageUri, OnCompleteListener<Uri> onCompleteListener) throws URISyntaxException {


        try {

            StorageMetadata metadata = new StorageMetadata.Builder().setContentType("image/jpg").build();

            Uri image = Uri.fromFile(new File(imageUri));

            StorageReference imageReference = storageReference.child("Uploads/" + UUID.randomUUID() + ".jpg");


            UploadTask uploadTask = imageReference.putFile(image, metadata);

            uploadTask.continueWithTask(task -> {

                if (task.isSuccessful())
                    return imageReference.getDownloadUrl();

                return null;
            }).addOnCompleteListener(onCompleteListener);


        } catch (Exception e) {
            //crashlytics.core.logException(e);
        }

    }


}
