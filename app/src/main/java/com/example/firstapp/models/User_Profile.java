package com.example.firstapp.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class User_Profile {


    private String name, email, image_url, id;

    public User_Profile(String name, String email, String image_url, String id) {
        this.name = name;
        this.email = email;
        this.image_url = image_url;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User_Profile() {
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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


    @BindingAdapter("profile_image")
    public static void load(ImageView view, String URL) {

        Glide.with(view.getContext()).load(URL).into(view);

    }
}
