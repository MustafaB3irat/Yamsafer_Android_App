package com.example.firstapp.views;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.firstapp.Presenters.chat.ChatPresenterImpl;
import com.example.firstapp.R;
import com.example.firstapp.databinding.MessageBinding;
import com.example.firstapp.models.data.Message;
import com.example.firstapp.mvpinterfaces.chat.Chat;
import com.example.firstapp.recyclerviewadapters.MessageAdapter;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements Chat.ChatView {

    private static final int STORAGE_PERMISSION_CODE = 9;
    private MessageBinding messageBinding;
    private Chat.ChatPresenter presenter;
    private String sender;
    private String receiver;
    private MessageAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private boolean isImage = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageBinding = DataBindingUtil.setContentView(this, R.layout.message);

        initStoragePermission();

        presenter = new ChatPresenterImpl(this);
        presenter.getCurrentUser();
        receiver = getIntent().getStringExtra("receiver");

        presenter.checkWhoInitiatedTheChat(receiver);


        setActionBar(messageBinding.toolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("");


        adapter = new MessageAdapter(new ArrayList<>());
        linearLayoutManager = new LinearLayoutManager(this);
        messageBinding.messagesRecyclerview.setLayoutManager(linearLayoutManager);
        messageBinding.messagesRecyclerview.setAdapter(adapter);

        presenter.setFriendsID(receiver);


    }


    @Override
    protected void onStart() {

        super.onStart();

        initSendButton();

        initBackButton();

        initUploadPhotoButton();


    }


    @Override
    public void setMessages(List<Message> messages) {

        ((MessageAdapter) messageBinding.messagesRecyclerview.getAdapter()).addMessage(messages);
        messageBinding.messagesRecyclerview.getAdapter().notifyDataSetChanged();
        messageBinding.messagesRecyclerview.scrollToPosition((messageBinding.messagesRecyclerview.getAdapter()).getItemCount() - 1);

    }


    @Override
    public void onMessageSent(boolean isSent) {

        if (isSent) {

        } else {
            Toast.makeText(this, getString(R.string.chat_message_failed), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initSendButton() {
        messageBinding.sendMessage.setOnClickListener(e -> {

            messageBinding.imageUploaded.setVisibility(View.GONE);
            sendMessage(messageBinding.messageText.getText().toString(), isImage);
            messageBinding.messageText.setText("");
            messageBinding.messageText.setTextColor(getResources().getColor(R.color.searchBar));


        });
    }

    @Override
    public void sendMessage(String message, boolean isImage) {

        presenter.sendMessage(message, isImage);
        this.isImage = false;

    }

    @Override
    public void getCurrentUser(FirebaseUser user) {
        sender = user.getUid();
    }


    @Override
    public void messageReceived(boolean playSound) {


        if (playSound) {
            final MediaPlayer messageReceived = MediaPlayer.create(this, R.raw.chatsound);
            messageReceived.start();
        }

    }

    @Override
    public void messageSent(boolean playSound) {

        if (playSound) {
            final MediaPlayer messageReceived = MediaPlayer.create(this, R.raw.sendmessage);
            messageReceived.start();

        }
    }

    @Override
    public void initBackButton() {

        messageBinding.toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }

    @Override
    public void initFriendsUsernameAndAvatar(HashMap<String, Object> friendsInfo) {

        messageBinding.username.setText(friendsInfo.get("username").toString());

        String avatarUrl = friendsInfo.get("avatar").toString();

        if (avatarUrl.equals("default")) {
            messageBinding.avatar.setImageResource(R.drawable.default_avatar);
        } else {
            Glide.with(messageBinding.avatar.getContext()).load(avatarUrl).into(messageBinding.avatar);
        }

    }

    @Override
    public void initUploadPhotoButton() {

        messageBinding.uploadImage.setOnClickListener(task -> {
//
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(intent, Chat.ChatView.PICK_IMAGE_CODE);

            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("image/*");

            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

            startActivityForResult(chooserIntent, Chat.ChatView.PICK_IMAGE_CODE);


        });

    }

    @Override
    public String getFileExtension(Uri file) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(file));
    }

    @Override
    public void loadImageFromDevice(String filePath) {

        Glide.with(messageBinding.messageText.getContext())
                .load(filePath)
                .into(new CustomTarget<Drawable>(100, 100) {

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        messageBinding.messageText.setCompoundDrawablesWithIntrinsicBounds(null, resource, null, null);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        messageBinding.messageText.setCompoundDrawablesWithIntrinsicBounds(null, placeholder, null, null);
                    }
                });

    }

    @Override
    public void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }

    }

    @Override
    public void initStoragePermission() {
        if (ContextCompat.checkSelfPermission(ChatActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        } else {
            requestStoragePermission();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.permissionGranted), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.permissionDenied), Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == Chat.ChatView.PICK_IMAGE_CODE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            if (ContextCompat.checkSelfPermission(this.getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                Bitmap photo = null;

                //  photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());

                messageBinding.imageUploaded.setVisibility(View.VISIBLE);
                messageBinding.imageUploaded.setImageURI(data.getData());
                isImage = true;

                // Uri tempUri = getImageUri(getApplicationContext(), photo);

                Uri uri = data.getData();

                messageBinding.messageText.setTextColor(Color.alpha(0));
                String path = getRealPathFromURI(uri);
                messageBinding.messageText.setText(path);


            }

        }


    }

    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
