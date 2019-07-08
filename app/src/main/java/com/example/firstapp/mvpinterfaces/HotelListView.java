package com.example.firstapp.mvpinterfaces;

import com.example.firstapp.models.data.Hotel;

import java.util.List;

public interface HotelListView {

    void getHotels(List<Hotel> hotels);
}
