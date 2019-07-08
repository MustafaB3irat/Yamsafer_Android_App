package com.example.firstapp.models.popoularOnYamsaferModels;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PopoularOnYamsaferPOJO implements Serializable {


    @SerializedName("image_source")
    private String image_source;


    @SerializedName("header")
    private String header;

    @SerializedName("footer")
    private String footer;


    public PopoularOnYamsaferPOJO(String image_source, String header, String footer) {
        this.image_source = image_source;
        this.header = header;
        this.footer = footer;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    @BindingAdapter("popImageSource")
    public static void load(ImageView view, String URL) {
        Glide.with(view).load(URL).into(view);
    }
}
