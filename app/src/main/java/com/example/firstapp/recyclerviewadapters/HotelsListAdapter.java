package com.example.firstapp.recyclerviewadapters;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.models.Hotel;

import com.example.firstapp.databinding.CardviewitemBinding;
import com.example.firstapp.mvpinterfaces.MainView;
import com.example.firstapp.views.DetailedHotelActivity;
import com.example.firstapp.views.MainActivity;

import java.util.List;

public class HotelsListAdapter extends RecyclerView.Adapter<HotelsListAdapter.HotelItemViewHolder> {

    private List<Hotel> hotels;

    public static class HotelItemViewHolder extends RecyclerView.ViewHolder {

//        public ImageView wallpaper, stars;
//        public AppCompatImageView hearts;
//        public TextView footer, description, header, rating;

        CardviewitemBinding cardViewBinding;

        public HotelItemViewHolder(@NonNull CardviewitemBinding itemView) {
            super(itemView.getRoot());

            cardViewBinding = itemView;

        }
    }


    public HotelsListAdapter(List<Hotel> hotels) {

        this.hotels = hotels;
    }


    @Override
    public HotelItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //CardviewitemBinding cardViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cardviewitem, parent, false);
        CardviewitemBinding cardViewBinding = CardviewitemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

//        cardViewBinding.hearts.setTag(R.drawable.ic_favorite);
        HotelItemViewHolder evh = new HotelItemViewHolder(cardViewBinding);

        return evh;
    }


    @Override
    public void onBindViewHolder(@NonNull final HotelItemViewHolder holder, int position) {

        Hotel currentHotel = hotels.get(position);


        holder.cardViewBinding.setHotel(currentHotel);
        setHeartClickable(holder.cardViewBinding);
        setCardClickable(holder.cardViewBinding, position);

    }

    private void setHeartClickable(final CardviewitemBinding cardView) {
        cardView.hearts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (cardView.hearts.getTag().toString().equals(R.drawable.ic_favorite + "")) {
//                    cardView.hearts.setImageResource(R.drawable.heart);
//                    cardView.hearts.setTag(R.drawable.heart);
//                    cardView.hearts.setColorFilter(Color.argb(255, 255, 0, 0));
//                } else {
//                    cardView.hearts.setImageResource(R.drawable.ic_favorite);
//                    cardView.hearts.setTag(R.drawable.ic_favorite);
//                }
                cardView.hearts.setActivated(!cardView.hearts.isActivated());


            }
        });
    }

    private void setCardClickable(final CardviewitemBinding cardView, final int position) {


        cardView.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newActivity = new Intent(cardView.getRoot().getContext(), DetailedHotelActivity.class);

                newActivity.putExtra("Hotel", hotels.get(position));

                cardView.getRoot().getContext().startActivity(newActivity);

            }
        });
    }


    @Override
    public int getItemCount() {
        return hotels.size();
    }


}
