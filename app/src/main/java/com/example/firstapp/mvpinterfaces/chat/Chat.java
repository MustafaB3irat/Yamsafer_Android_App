package com.example.firstapp.mvpinterfaces.chat;

import android.net.Uri;

import com.example.firstapp.models.data.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

public interface Chat {

    interface ChatModel {

        void setCurrentUser();

        void getMessagesFromChat();

        void addMessage(String message , boolean isImage);

        void getNewestMessages(QuerySnapshot queryDocumentSnapshots);

        void checkWhoInitiatedTheChat(String receiver);

        void getFriendsAvatarAndUsername(String id);

        void getImageURIAndUploadIt(String imageUri , OnCompleteListener<Uri> onCompleteListener) throws URISyntaxException;
    }

    interface ChatPresenter {

        void setCurrentUser(FirebaseUser CurrentUsser);

        void getCurrentUser();

        void didLoadMessages(List<Message> messages);

        void onMessageSent(boolean isSent);

        void sendMessage(String message , boolean isImage);

        void messageReceived(boolean playSound);

        void messageSent(boolean playSound);

        void checkWhoInitiatedTheChat(String receiver);

        void setFriendsID(String id);

        void getFriendsAvatarAndUsername(HashMap<String, Object> friendsInfo);

        void setImageURI(Uri imageURI);

    }

    interface ChatView {

        int PICK_IMAGE_CODE = 11;

        void setMessages(List<Message> messages);

        void onMessageSent(boolean isSent);

        void initSendButton();

        void sendMessage(String message , boolean isImage);

        void getCurrentUser(FirebaseUser user);

        void messageReceived(boolean playSound);

        void messageSent(boolean playSound);

        void initBackButton();

        void initFriendsUsernameAndAvatar(HashMap<String, Object> friendsInfo);

        void initUploadPhotoButton();

        String getFileExtension(Uri file);

        void loadImageFromDevice(String filePath);

        void requestStoragePermission();
        void initStoragePermission();

    }
}
