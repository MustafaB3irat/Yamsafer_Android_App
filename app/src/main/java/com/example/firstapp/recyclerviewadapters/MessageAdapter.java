package com.example.firstapp.recyclerviewadapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.models.MessageFactory;
import com.example.firstapp.models.data.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {

    List<Message> messages;

    public MessageAdapter(List<Message> contacts) {
        this.messages = contacts;
    }

    public void clearMessages() {
        messages.clear();
    }


    public void addMessage(List<Message> messages) {
       this.messages.addAll(messages);
    }

    public List<Message> getMessagesList(){
        return messages;
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getItemViewType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MessageFactory.create(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        messages.get(position).onBindViewHolder(holder, messages.get(position));

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
