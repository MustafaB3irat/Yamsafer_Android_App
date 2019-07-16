package com.example.firstapp.models.data;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.firstapp.databinding.SenderMessageBinding;
import com.example.firstapp.interfaces.CardFactory;
import com.example.firstapp.models.RecieverViewHolder;
import com.example.firstapp.models.SenderViewHolder;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.PropertyName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements CardFactory<Message> {


    private String body;

    private String sender;

    private String receiver;

    @PropertyName("created_at")
    private Timestamp created_at;

    @PropertyName("message_type")
    private String message_type;

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public Message(String body, Timestamp created_at, String sender, String receiver, String messageType) {
        this.body = body;
        this.created_at = created_at;
        this.sender = sender;
        this.receiver = receiver;
        this.message_type = messageType;
    }

    public Message() {
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @BindingAdapter("created_at")
    public static void setTimeStamp(TextView timeStamp, Timestamp time) {

        long timeInMillis = 0;

        if (time.getSeconds() < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            timeInMillis = time.getSeconds() * 1000;
        }

        long now = (new Date()).getTime();


        if (now < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            now = now * 1000;
        }


        if (timeInMillis > now || timeInMillis <= 0) {
            timeStamp.setText("");
        }

        // TODO: localize
        final long diff = now - timeInMillis;
        if (diff < MINUTE_MILLIS) {
            timeStamp.setText("just now");
        } else if (diff < 2 * MINUTE_MILLIS) {
            timeStamp.setText("a minute ago");

        } else if (diff < 50 * MINUTE_MILLIS) {
            timeStamp.setText(diff / MINUTE_MILLIS + " minutes ago");
        } else if (diff < 90 * MINUTE_MILLIS) {
            timeStamp.setText("an hour ago");
        } else if (diff < 24 * HOUR_MILLIS) {
            timeStamp.setText(diff / HOUR_MILLIS + " hours ago");
        } else if (diff < 48 * HOUR_MILLIS) {
            timeStamp.setText("yesterday");
        } else {
            timeStamp.setText(diff / DAY_MILLIS + " days ago");
        }

    }

    @BindingAdapter("message")
    public static void loadMessage(View view, String message) {

        try {
            if (view instanceof ImageView) {
                if (message.split("\\|")[1].equals("1")) {
                    Glide.with(view.getContext()).load(message.split("\\|")[0]).into((ImageView) view);
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
            }
            if (view instanceof TextView) {

                if (message.split("\\|")[1].equals("0")) {
                    ((TextView) view).setText(message.split("\\|")[0]);
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
            }

        } catch (Exception e) {

            Log.d("Error", e.getMessage());
        }
    }

    @Override
    public int getItemViewType() {

        if (currentUser.getUid().equals(sender))
            return CardFactory.SENDER;
        else

            return CardFactory.RECEIVER;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, Message object) {


        if (currentUser.getUid().equals(sender)) {

            ((SenderViewHolder) holder).getSenderMessageBinding().setSender(object);

        } else {

            ((RecieverViewHolder) holder).getRecieverMessageBinding().setReciever(object);


        }

    }
}
