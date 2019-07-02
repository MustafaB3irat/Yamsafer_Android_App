package com.example.firstapp.Presenters;

import com.example.firstapp.interfaces.Model_Presenter;
import com.example.firstapp.models.Hotel;
import com.example.firstapp.models.HotelModel;
import com.example.firstapp.mvpinterfaces.MainModel;
import com.example.firstapp.mvpinterfaces.MainView;

import java.util.List;


public class HotelDetailPresenter implements com.example.firstapp.mvpinterfaces.HotelDetailPresenter {

    private MainModel mainModel;
    private MainView mainView;

    private Model_Presenter model_presenter = new Model_Presenter() {
        @Override
        public void getDataFromModel(List<Hotel> data) {
            mainView.onHotelsRequestResponse(data);

        }
    };


    public HotelDetailPresenter(MainView mainView) {
        this.mainView = mainView;
        this.mainModel = new HotelModel();
    }

    @Override
    public void onRequestHotel() {

        mainModel.getDataFromRetrofit(model_presenter);

    }
}
