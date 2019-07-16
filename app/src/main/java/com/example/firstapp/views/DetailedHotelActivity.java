package com.example.firstapp.views;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firstapp.R;
import com.example.firstapp.databinding.DetailedHotelBinding;
import com.example.firstapp.interfaces.CardFactory;
import com.example.firstapp.models.data.Hotel;
import com.example.firstapp.models.HotelImage;
import com.example.firstapp.mvpinterfaces.HotelDetailPresenter;
import com.example.firstapp.mvpinterfaces.HotelList;
import com.example.firstapp.recyclerviewadapters.HotelDescAdapter;

import java.util.ArrayList;
import java.util.List;


@BindingMethods({
        @BindingMethod(type = ImageView.class, attribute = "android:src", method = "setImageResource"),
        @BindingMethod(type = RatingBar.class, attribute = "android:numStars", method = "setNumStar")
})
public class DetailedHotelActivity extends AppCompatActivity implements HotelList.HotelListView {

    private DetailedHotelBinding detailedHotelBinding;
    private HotelDescAdapter adapter;
    private HotelDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        detailedHotelBinding = DataBindingUtil.setContentView(this, R.layout.detailed_hotel);

        detailedHotelBinding.detailedRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        presenter = new com.example.firstapp.Presenters.HotelDetailPresenter(this);
        presenter.onRequestHotel();

        backToHome();


    }


    private void backToHome() {

        detailedHotelBinding.back.setOnClickListener(view -> finish());
    }

    @Override
    public void getHotels(List<Hotel> hotels) {

        Intent intent = getIntent();

        Bundle extra = intent.getExtras();


        List<CardFactory> cardFactory = new ArrayList<>();


        if (extra != null) {


            Hotel hotel = (Hotel) extra.getSerializable("Hotel");
            cardFactory.add(new HotelImage(hotel.getImageSource()));
            cardFactory.add(new com.example.firstapp.models.CardFactory(hotel.getStars(), hotel.getRate(), hotel.getDescription(), hotel.getFooter(), hotel.getHeader()));

            adapter = new HotelDescAdapter(cardFactory);
            detailedHotelBinding.detailedRecyclerview.setAdapter(adapter);


        }
    }
}
