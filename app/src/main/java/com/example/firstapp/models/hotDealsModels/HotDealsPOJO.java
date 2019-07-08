package com.example.firstapp.models.hotDealsModels;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HotDealsPOJO implements Serializable {

    @SerializedName("image_source")
    private String image_source;

    @SerializedName("discount")
    private String discount;

    @SerializedName("hotel_place")
    private String hotel_place;

    @SerializedName("hotel_title")
    private String hotel_title;

    @SerializedName("save_stmt")
    private String save_stmt;

    @SerializedName("price")
    private String price;

    public HotDealsPOJO(String image_source, String discount, String hotel_place, String hotel_title, String save_stmt, String price) {
        this.image_source = image_source;
        this.discount = discount;
        this.hotel_place = hotel_place;
        this.hotel_title = hotel_title;
        this.save_stmt = save_stmt;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getHotel_place() {
        return hotel_place;
    }

    public void setHotel_place(String hotel_place) {
        this.hotel_place = hotel_place;
    }

    public String getHotel_title() {
        return hotel_title;
    }

    public void setHotel_title(String hotel_title) {
        this.hotel_title = hotel_title;
    }

    public String getSave_stmt() {
        return save_stmt;
    }

    public void setSave_stmt(String save_stmt) {
        this.save_stmt = save_stmt;
    }

    @BindingAdapter("image_url")
    public static void load(ImageView view, String URL) {

        Glide.with(view.getContext()).load(URL).into(view);
    }
}
