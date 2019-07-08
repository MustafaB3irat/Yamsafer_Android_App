package com.example.firstapp.models.data;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class UserProfile {


    private String name, email, avatar, id;

    public UserProfile(String name, String email, String image_url, String id) {
        this.name = name;
        this.email = email;
        this.avatar = image_url;
        this.id = id;
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


    @BindingAdapter("profile_image")
    public static void load(ImageView view, String URL) {

        Glide.with(view.getContext()).load(URL).into(view);

    }
}
