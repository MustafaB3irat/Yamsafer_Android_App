package com.example.firstapp.models;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.databinding.RecieverMessageBinding;

public class RecieverViewHolder extends RecyclerView.ViewHolder {

    private RecieverMessageBinding recieverMessageBinding;

    public RecieverMessageBinding getRecieverMessageBinding() {
        return recieverMessageBinding;
    }

    public RecieverViewHolder(@NonNull RecieverMessageBinding itemView) {
        super(itemView.getRoot());

        this.recieverMessageBinding = itemView;
    }
}
