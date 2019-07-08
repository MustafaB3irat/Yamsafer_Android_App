package com.example.firstapp.recyclerviewadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.databinding.RecentSearchCardviewBinding;
import com.example.firstapp.models.recentSearchsModel.RecentSearchesPOJO;

import java.util.List;
import java.util.zip.Inflater;

public class RecentSearchesAdapter extends RecyclerView.Adapter<RecentSearchesAdapter.RecentSearchesViewHolder> {


    List<RecentSearchesPOJO> list;

    public RecentSearchesAdapter(List<RecentSearchesPOJO> list) {
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

        RecentSearchesPOJO recent_search = list.get(position);

        holder.recentSearchCardviewBinding.setRecent(recent_search);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
