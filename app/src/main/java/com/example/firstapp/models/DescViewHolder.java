package com.example.firstapp.models;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.databinding.DescCardviewBinding;


public class DescViewHolder extends RecyclerView.ViewHolder {


    private DescCardviewBinding descCardviewBinding;


    public DescCardviewBinding getDescCardviewBinding() {
        return descCardviewBinding;
    }

    public DescViewHolder(@NonNull DescCardviewBinding itemView) {
        super(itemView.getRoot());
        descCardviewBinding = itemView;
    }


}

