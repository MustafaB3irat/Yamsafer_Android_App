package com.example.firstapp.models;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.databinding.ImageCardviewBinding;
import com.example.firstapp.interfaces.CardFactory;
import com.google.gson.annotations.SerializedName;

public class HotelImage implements CardFactory<HotelImage> {


    @SerializedName("imageSource")
    private String imageSource;

    public HotelImage(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getImageSource() {
        return imageSource;
    }

//    @BindingAdapter("ImageUrl")
//    public static void loadImage(ImageView view, String url) {
//        Glide.with(view.getContext()).load(url).into(view);
//    }


    @Override
    public int getItemViewType() {
        return CardFactory.IMAGE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, HotelImage hotel_image) {

        ((ImageViewHolder) holder).getImageCardviewBinding().setDetail(hotel_image);
        setHeartClickable(((ImageViewHolder) holder).getImageCardviewBinding());

    }



    private void setHeartClickable(final ImageCardviewBinding cardView) {
        cardView.detailedHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardView.detailedHeart.setActivated(!cardView.detailedHeart.isActivated());

            }
        });
    }
}
