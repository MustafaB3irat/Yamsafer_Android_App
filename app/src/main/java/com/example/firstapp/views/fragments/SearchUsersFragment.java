package com.example.firstapp.views.fragments;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.firstapp.mvpinterfaces.chat.SearchUser;
import com.example.firstapp.recyclerviewadapters.ChatUsersAdapter;
import com.example.firstapp.views.MainActivity;

import java.util.List;

public class SearchUsersFragment extends Fragment implements SearchUser.SearchUsersFragment {

    private SearchUser.SearchUsersPresenter presenter;
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

        presenter = new com.example.firstapp.Presenters.SearchUsersPresenter(this);
        initSearchEditText();

    }

    @Override
    public void getContacts(List<UserProfile> contacts) {
        chatUsersBinding.chatMain.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChatUsersAdapter(contacts, ((MainActivity) getActivity()));

        chatUsersBinding.watermarkYamsafer.setVisibility(View.GONE);
        chatUsersBinding.watermarkText.setVisibility(View.GONE);


        chatUsersBinding.chatMain.setAdapter(adapter);
    }

    @Override
    public void initSearchEditText() {
        chatUsersBinding.searchUser.addTextChangedListener(new MyTextWatcher(() -> {

            if (chatUsersBinding.chatMain.getAdapter() != null) {

                ((ChatUsersAdapter) chatUsersBinding.chatMain.getAdapter()).clearList();
                chatUsersBinding.chatMain.getAdapter().notifyDataSetChanged();
            }

            if (!TextUtils.isEmpty(chatUsersBinding.searchUser.getText()))
                presenter.getContactsByName(chatUsersBinding.searchUser.getText().toString());

            else {
                chatUsersBinding.watermarkYamsafer.setVisibility(View.VISIBLE);
                chatUsersBinding.watermarkText.setVisibility(View.VISIBLE);
            }
        }));

    }
}
