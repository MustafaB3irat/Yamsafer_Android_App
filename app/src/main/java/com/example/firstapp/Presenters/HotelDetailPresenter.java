package com.example.firstapp.Presenters;

import com.example.firstapp.mvpinterfaces.Model_Presenter;
import com.example.firstapp.models.data.Hotel;
import com.example.firstapp.models.HotelModel;
import com.example.firstapp.mvpinterfaces.HotelListModel;
import com.example.firstapp.mvpinterfaces.HotelListView;

import java.util.List;


public class HotelDetailPresenter implements com.example.firstapp.mvpinterfaces.HotelDetailPresenter {

    private HotelListModel mainModel;
    private HotelListView mainView;

    private Model_Presenter model_presenter = new Model_Presenter() {
        @Override
        public void getDataFromModel(List<Hotel> data) {
            mainView.getHotels(data);

        }
    };


    public HotelDetailPresenter(HotelListView mainView) {
        this.mainView = mainView;
        this.mainModel = new HotelModel();
    }

    @Override
    public void onRequestHotel() {

        mainModel.getDataFromRetrofit(model_presenter);

    }
}
