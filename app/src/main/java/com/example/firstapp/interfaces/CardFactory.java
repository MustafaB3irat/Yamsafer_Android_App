package com.example.firstapp.interfaces;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public interface CardFactory<T> {

    int IMAGE = 0, DESC = 1;
    int SENDER = 0 , RECEIVER=1;

    int getItemViewType();

    void onBindViewHolder(RecyclerView.ViewHolder holder, T object);

}
