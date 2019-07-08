package com.example.firstapp.recyclerviewadapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.databinding.HotDealsCardviewBinding;
import com.example.firstapp.models.data.HotDeals;

import java.util.List;

public class HotDealsAdapter extends RecyclerView.Adapter<HotDealsAdapter.HotDealsViewHolder> {

    private List<HotDeals> list;

    public HotDealsAdapter(List<HotDeals> list) {
        this.list = list;
    }

    public class HotDealsViewHolder extends RecyclerView.ViewHolder {

        private HotDealsCardviewBinding hotDealsCardviewBinding;

        public HotDealsViewHolder(@NonNull HotDealsCardviewBinding itemView) {
            super(itemView.getRoot());

            hotDealsCardviewBinding = itemView;
        }
    }


    @NonNull
    @Override
    public HotDealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        HotDealsCardviewBinding hotDealsCardviewBinding = HotDealsCardviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        HotDealsViewHolder viewHolder = new HotDealsViewHolder(hotDealsCardviewBinding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HotDealsViewHolder holder, int position) {

        HotDeals hotDeals = list.get(position);

        holder.hotDealsCardviewBinding.setHotDeals(hotDeals);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
