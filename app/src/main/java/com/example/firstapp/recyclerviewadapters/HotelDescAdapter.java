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


//    public class ImageViewHolder extends RecyclerView.ViewHolder {
//        ImageCardviewBinding imageCardviewBinding;
//
//        public ImageViewHolder(@NonNull ImageCardviewBinding itemView) {
//            super(itemView.getRoot());
//
//            imageCardviewBinding = itemView;
//
//        }
//    }
//
//
//    public class DescViewHolder extends RecyclerView.ViewHolder {
//        DescCardviewBinding descCardviewBinding;
//
//        public DescViewHolder(@NonNull DescCardviewBinding itemView) {
//            super(itemView.getRoot());
//
//            descCardviewBinding = itemView;
//
//        }
//    }

    @Override
    public int getItemViewType(int position) {
//
//        if (hotelsList.get(position) instanceof HotelImage) {
//            return CardFactory.IMAGE;
//        } else if (hotelsList.get(position) instanceof com.example.firstapp.models.CardFactory) {
//            return CardFactory.DESC;
//        }
//        return -1;

        return hotelsList.get(position).getItemViewType();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        if (viewType == 0) {
//            ImageCardviewBinding imageCardviewBinding = ImageCardviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
//            return new ImageViewHolder(imageCardviewBinding);
//        } else {
//            DescCardviewBinding descCardviewBinding = DescCardviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
//            return new DescViewHolder(descCardviewBinding);
//        }

        return HotelViewHolderFactory.create(parent, viewType);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

//        if (getItemViewType(position) == 0) {
//
//            ((ImageViewHolder) holder).imageCardviewBinding.setDetail(hotelsList.get(position));
//            setHeartClickable(((ImageViewHolder) holder).imageCardviewBinding);
//        } else {
//            ((DescViewHolder) holder).descCardviewBinding.setDetail(hotelsList.get(position));
//        }

        hotelsList.get(position).onBindViewHolder(holder, hotelsList.get(position));


    }

//    private void setHeartClickable(final ImageCardviewBinding cardView) {
//        cardView.detailedHeart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cardView.detailedHeart.setActivated(!cardView.detailedHeart.isActivated());
//
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        return hotelsList.size();
    }
}
