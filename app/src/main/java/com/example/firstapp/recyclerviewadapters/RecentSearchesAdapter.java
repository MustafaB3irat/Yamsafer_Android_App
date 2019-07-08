package com.example.firstapp.recyclerviewadapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.databinding.RecentSearchCardviewBinding;
import com.example.firstapp.models.data.RecentSearches;

import java.util.List;

public class RecentSearchesAdapter extends RecyclerView.Adapter<RecentSearchesAdapter.RecentSearchesViewHolder> {


    List<RecentSearches> list;

    public RecentSearchesAdapter(List<RecentSearches> list) {
        this.list = list;
    }

    public class RecentSearchesViewHolder extends RecyclerView.ViewHolder {

        private RecentSearchCardviewBinding recentSearchCardviewBinding;

        public RecentSearchesViewHolder(@NonNull RecentSearchCardviewBinding itemView) {
            super(itemView.getRoot());
            recentSearchCardviewBinding = itemView;
        }
    }

    @NonNull
    @Override
    public RecentSearchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecentSearchCardviewBinding recentSearchCardviewBinding = RecentSearchCardviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        RecentSearchesViewHolder recentSearchesViewHolder = new RecentSearchesViewHolder(recentSearchCardviewBinding);
        return recentSearchesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentSearchesViewHolder holder, int position) {

        RecentSearches recent_search = list.get(position);

        holder.recentSearchCardviewBinding.setRecent(recent_search);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
