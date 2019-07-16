package com.example.firstapp.models;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.databinding.RecieverMessageBinding;
import com.example.firstapp.databinding.SenderMessageBinding;

public class MessageFactory {

    public static RecyclerView.ViewHolder create(ViewGroup parent, int viewType) {

        switch (viewType) {

            case CardFactory.SENDER: {
                return new SenderViewHolder(SenderMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }

            case CardFactory.RECEIVER: {
                return new RecieverViewHolder(RecieverMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }

            default:
                return null;
        }

    }
}
