package com.example.firstapp.recyclerviewadapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.databinding.ChatUserBinding;
import com.example.firstapp.models.data.UserProfile;
import com.example.firstapp.views.ChatActivity;

import java.util.List;

public class ChatUsersAdapter extends RecyclerView.Adapter<ChatUsersAdapter.ChatUsersViewHolder> {

    private List<UserProfile> contacts;
    private AppCompatActivity activity;


    public ChatUsersAdapter(List<UserProfile> contacts, AppCompatActivity activity) {
        this.contacts = contacts;
        this.activity = activity;
    }

    public class ChatUsersViewHolder extends RecyclerView.ViewHolder {

        private ChatUserBinding chatUserBinding;


        public ChatUsersViewHolder(@NonNull ChatUserBinding itemView) {
            super(itemView.getRoot());

            this.chatUserBinding = itemView;
        }
    }


    public void clearList() {
        contacts.clear();
    }

    @NonNull
    @Override
    public ChatUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatUsersViewHolder(ChatUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatUsersViewHolder holder, int position) {

        holder.chatUserBinding.setContact(contacts.get(position));
        initContactCard(holder.chatUserBinding , contacts.get(position));

    }

    private void initContactCard(ChatUserBinding chatUserBinding, UserProfile userProfile) {

        chatUserBinding.contact.setOnClickListener(e -> {

            Intent intent = new Intent(activity, ChatActivity.class);
            intent.putExtra("receiver", userProfile.getId());
            activity.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }


}
