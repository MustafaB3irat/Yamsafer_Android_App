package com.example.firstapp.models;

import android.view.LayoutInflater;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.databinding.ImageCardviewBinding;


public class ImageViewHolder extends RecyclerView.ViewHolder {


    private ImageCardviewBinding imageCardviewBinding;


    public ImageCardviewBinding getImageCardviewBinding() {
        return imageCardviewBinding;
    }

    public ImageViewHolder(@NonNull ImageCardviewBinding itemView) {
        super(itemView.getRoot());

        imageCardviewBinding = itemView;
    }


}
