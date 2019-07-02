package com.example.firstapp.models;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.databinding.DescCardviewBinding;
import com.example.firstapp.databinding.ImageCardviewBinding;

public class HotelViewHolderFactory {

    public static RecyclerView.ViewHolder create(ViewGroup parent, int viewType) {

        switch (viewType) {
            case CardFactory.IMAGE:
                ImageCardviewBinding imageCardviewBinding = ImageCardviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ImageViewHolder(imageCardviewBinding);

            case CardFactory.DESC:

                DescCardviewBinding descCardviewBinding = DescCardviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new DescViewHolder(descCardviewBinding);

            default:
                return null;
        }
    }
}
