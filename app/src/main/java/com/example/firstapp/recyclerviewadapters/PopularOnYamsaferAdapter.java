package com.example.firstapp.recyclerviewadapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firstapp.databinding.PopularOnYamsaferCardviewBinding;
import com.example.firstapp.models.data.PopoularOnYamsafer;
import java.util.List;

public class PopularOnYamsaferAdapter extends RecyclerView.Adapter<PopularOnYamsaferAdapter.PopularOnYamsaferViewHolder> {


    private List<PopoularOnYamsafer> list;

    public PopularOnYamsaferAdapter(List<PopoularOnYamsafer> list) {
        this.list = list;
    }


    public class PopularOnYamsaferViewHolder extends RecyclerView.ViewHolder {

        private PopularOnYamsaferCardviewBinding popularOnYamsaferCardviewBinding;

        public PopularOnYamsaferViewHolder(@NonNull PopularOnYamsaferCardviewBinding itemView) {
            super(itemView.getRoot());

            popularOnYamsaferCardviewBinding = itemView;
        }
    }

    @NonNull
    @Override
    public PopularOnYamsaferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        PopularOnYamsaferCardviewBinding popularOnYamsaferCardviewBinding = PopularOnYamsaferCardviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        PopularOnYamsaferViewHolder popularOnYamsaferViewHolder = new PopularOnYamsaferViewHolder(popularOnYamsaferCardviewBinding);
        return popularOnYamsaferViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PopularOnYamsaferViewHolder holder, int position) {

        PopoularOnYamsafer popoular = list.get(position);

        holder.popularOnYamsaferCardviewBinding.setPopular(popoular);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
