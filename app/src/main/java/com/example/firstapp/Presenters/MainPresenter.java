package com.example.firstapp.Presenters;

import com.example.firstapp.interfaces.Model_Presenter;
import com.example.firstapp.mvpinterfaces.MainModel;
import com.example.firstapp.mvpinterfaces.MainView;
import com.example.firstapp.models.Hotel;
import com.example.firstapp.models.HotelModel;
import com.example.firstapp.models.ResponseHandler;

import java.util.List;

public class MainPresenter implements com.example.firstapp.mvpinterfaces.MainPresenter {

    private MainModel model;
    private MainView view;

    private Model_Presenter model_presenter = new Model_Presenter() {
        @Override
        public void getDataFromModel(List<Hotel> data) {
            view.onHotelsRequestResponse(data);

        }
    };

    public MainPresenter(MainView view) {
        this.view = view;
        this.model = new HotelModel();
    }

    @Override
    public void onRequestHotels() {

//        ResponseHandler<List<Hotel>> callback = null;
//
//        model.getDataFromRetrofit(callback);
//
//        callback = model.getCallBack();
//
//        view.onHotelsRequestResponse(callback.getData());

        model.getDataFromRetrofit(model_presenter);

    }

}
