package com.example.firstapp.recyclerviewadapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firstapp.databinding.ChatUserBinding;
import com.example.firstapp.models.data.UserProfile;

import java.util.List;

public class ChatUsersAdapter extends RecyclerView.Adapter<ChatUsersAdapter.ChatUsersViewHolder> {

    private List<UserProfile> contacts;


    public ChatUsersAdapter(List<UserProfile> contacts) {
        this.contacts = contacts;
    }

    public class ChatUsersViewHolder extends RecyclerView.ViewHolder {

        private ChatUserBinding chatUserBinding;


        public ChatUsersViewHolder(@NonNull ChatUserBinding itemView) {
            super(itemView.getRoot());

            this.chatUserBinding = itemView;
        }
    }


    @NonNull
    @Override
    public ChatUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatUsersViewHolder(ChatUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatUsersViewHolder holder, int position) {

        holder.chatUserBinding.setContact(contacts.get(position));

    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }


}
