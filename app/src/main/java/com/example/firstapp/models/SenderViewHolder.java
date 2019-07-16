package com.example.firstapp.models;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.databinding.SenderMessageBinding;

public class SenderViewHolder extends RecyclerView.ViewHolder {

    private SenderMessageBinding senderMessageBinding;

    public SenderMessageBinding getSenderMessageBinding() {
        return senderMessageBinding;
    }

    public SenderViewHolder(@NonNull SenderMessageBinding itemView) {
        super(itemView.getRoot());

        this.senderMessageBinding = itemView;
    }
}
