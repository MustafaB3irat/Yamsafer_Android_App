package com.example.firstapp.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Hotel implements Serializable {

    @SerializedName("stars")
    private int stars;

    @SerializedName("hearts")
    private int hearts;

    @SerializedName("rate")
    private String rate;

    @SerializedName("description")
    private String description;

    @SerializedName("footer")
    private String footer;

    @SerializedName("header")
    private String header;

    @SerializedName("imageSource")
    private String imageSource;

    public Hotel(String mImageSource, int stars, String rate, String description, String footer, String header, int hearts) {

        this.imageSource = mImageSource;
        this.stars = stars;
        this.rate = rate;
        this.description = description;
        this.footer = footer;
        this.header = header;
        this.hearts = hearts;
    }

    public int getHearts() {
        return hearts;
    }

    public String getHeader() {
        return header;
    }


    public String getImageSource() {
        return imageSource;
    }

    public int getStars() {
        return stars;
    }

    public String getRate() {
        return rate;
    }

    public String getDescription() {
        return description;
    }

    public String getFooter() {
        return footer;
    }

    @BindingAdapter("ImageUrl")
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }

}
