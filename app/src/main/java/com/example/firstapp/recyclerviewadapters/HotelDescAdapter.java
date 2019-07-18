package com.example.firstapp.recyclerviewadapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.interfaces.CardFactory;
import com.example.firstapp.models.HotelViewHolderFactory;

import java.util.List;

public class HotelDescAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CardFactory> hotelsList;

    public HotelDescAdapter(List<CardFactory> hotelsList) {
        this.hotelsList = hotelsList;
    }



    @Override
    public int getItemViewType(int position) {


        return hotelsList.get(position).getItemViewType();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return HotelViewHolderFactory.create(parent, viewType);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        hotelsList.get(position).onBindViewHolder(holder, hotelsList.get(position));


    }


    @Override
    public int getItemCount() {
        return hotelsList.size();
    }
}
