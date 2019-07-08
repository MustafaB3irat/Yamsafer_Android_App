package com.example.firstapp.mvpinterfaces;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.models.Hotel;

import java.util.List;

public interface HotelListView {

    void getHotels(List<Hotel> hotels);
}
