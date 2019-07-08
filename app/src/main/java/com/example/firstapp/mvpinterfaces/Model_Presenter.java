package com.example.firstapp.mvpinterfaces;

import com.example.firstapp.models.data.Hotel;

import java.util.List;

public interface Model_Presenter {


    void getDataFromModel(List<Hotel> data);
}
