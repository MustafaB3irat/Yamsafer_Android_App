package com.example.firstapp.mvpinterfaces;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.models.Hotel;

import java.util.List;

public interface MainView {

    void onHotelsRequestResponse(List<Hotel> hotels);

}
