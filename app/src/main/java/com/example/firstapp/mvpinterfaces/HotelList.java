package com.example.firstapp.mvpinterfaces;

import com.example.firstapp.models.data.Hotel;

import java.util.List;

public interface HotelList {
    interface HotelListModel {


        void getDataFromRetrofit(Model_Presenter model_presenter);

       // ResponseHandler<List<Hotel>> getCallBack();

    }

    interface HotelListPresenter {

        void onRequestHotels();




    }

    interface HotelListView {

        void getHotels(List<Hotel> hotels);
    }
}
