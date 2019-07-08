package com.example.firstapp.models.recentSearchsModel;

import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RecentSearchesPOJO implements Serializable {

    @SerializedName("image_source")
    private String image_source;

    @SerializedName("search_place")
    private String search_place;

    @SerializedName("from_to")
    private String from_to;

    @SerializedName("numOfGuest")
    private String numOfGuest;


    public RecentSearchesPOJO(String image_source, String search_place, String from_to, String numOfGuest) {
        this.image_source = image_source;
        this.search_place = search_place;
        this.from_to = from_to;
        this.numOfGuest = numOfGuest;
    }


    public String getImage_source() {
        return image_source;
    }

    public String getSearch_place() {
        return search_place;
    }

    public String getFrom_to() {
        return from_to;
    }

    public String getNumOfGuest() {
        return numOfGuest;
    }

    public void setSearch_place(String search_place) {
        this.search_place = search_place;
    }

    public void setFrom_to(String from_to) {
        this.from_to = from_to;
    }

    public void setNumOfGuest(String numOfGuest) {
        this.numOfGuest = numOfGuest;
    }

    @BindingAdapter("ImageURL")
    public static void load(AppCompatImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }
}
