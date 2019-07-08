package com.example.firstapp.views.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firstapp.R;
import com.example.firstapp.customListeners.MyTextWatcher;
import com.example.firstapp.databinding.ChatUsersBinding;
import com.example.firstapp.models.data.UserProfile;
import com.example.firstapp.mvpinterfaces.chat.ChatUsersPresenter;
import com.example.firstapp.recyclerviewadapters.ChatUsersAdapter;

import java.util.List;

public class ChatFragment extends Fragment implements com.example.firstapp.mvpinterfaces.chat.ChatFragment {

    private ChatUsersPresenter presenter;
    private ChatUsersBinding chatUsersBinding;
    private ChatUsersAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        chatUsersBinding = DataBindingUtil.inflate(inflater, R.layout.chat_users, container, false);

        return chatUsersBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new com.example.firstapp.Presenters.chat.ChatUsersPresenter(this);
        initSearchEditText();

    }

    @Override
    public void getContacts(List<UserProfile> contacts) {
        chatUsersBinding.chatMain.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChatUsersAdapter(contacts);

        chatUsersBinding.watermarkYamsafer.setVisibility(View.GONE);
        chatUsersBinding.watermarkText.setVisibility(View.GONE);


        chatUsersBinding.chatMain.setAdapter(adapter);
    }

    @Override
    public void initSearchEditText() {
        chatUsersBinding.searchUser.addTextChangedListener(new MyTextWatcher(() -> {
            presenter.getContactsByName(chatUsersBinding.searchUser.getText().toString());
        }));

    }
}
