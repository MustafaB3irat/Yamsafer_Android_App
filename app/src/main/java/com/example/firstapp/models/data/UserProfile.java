package com.example.firstapp.models.data;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.firstapp.R;
import com.google.firebase.firestore.PropertyName;

public class UserProfile {


    private String name, email, avatar, id;

    @PropertyName("tokenid")
    private String tokenId;

    public UserProfile(String name, String email, String avatar, String id, String tokenId) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.id = id;
        this.tokenId = tokenId;
    }


    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserProfile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String image_url) {
        this.avatar = image_url;
    }


    @BindingAdapter("avatar")
    public static void load(ImageView view, String URL) {

        if (URL.equals("default"))
            view.setImageResource(R.drawable.default_avatar);

        else
            Glide.with(view.getContext()).load(URL).into(view);

    }
}
