package com.example.firstapp.interfaces;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public interface CardFactory<T> {

    public static final int IMAGE = 0, DESC = 1;

    int getItemViewType();

    void onBindViewHolder(RecyclerView.ViewHolder holder, T object);

}
