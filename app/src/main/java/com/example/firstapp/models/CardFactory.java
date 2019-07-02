package com.example.firstapp.models;


import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.annotations.SerializedName;

public class CardFactory implements com.example.firstapp.interfaces.CardFactory<CardFactory> {


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


    public CardFactory(int stars, String rate, String description, String footer, String header) {
        this.stars = stars;
        this.rate = rate;
        this.description = description;
        this.footer = footer;
        this.header = header;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getHearts() {
        return hearts;
    }

    public void setHearts(int hearts) {
        this.hearts = hearts;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public int getItemViewType() {
        return com.example.firstapp.interfaces.CardFactory.DESC;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, CardFactory hotel_desc) {

        ((DescViewHolder) holder).getDescCardviewBinding().setDetail(hotel_desc);

    }
}
