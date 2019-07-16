package com.example.firstapp.Presenters;

import com.example.firstapp.mvpinterfaces.HotelList;
import com.example.firstapp.mvpinterfaces.Model_Presenter;
import com.example.firstapp.models.data.Hotel;
import com.example.firstapp.models.HotelModel;
import com.example.firstapp.mvpinterfaces.MainView;

import java.util.List;

public class HotelListPresenters implements HotelList.HotelListPresenter {

    private HotelList.HotelListModel model;
    private HotelList.HotelListView view;
    private MainView mainActivity;

    private Model_Presenter model_presenter = new Model_Presenter() {
        @Override
        public void getDataFromModel(List<Hotel> data) {
            view.getHotels(data);

        }
    };

    public HotelListPresenters(MainView mainActivity) {
        this.mainActivity = mainActivity;
    }

    public HotelListPresenters(HotelList.HotelListView view) {
        this.view = view;
        this.model = new HotelModel();
    }

    @Override
    public void onRequestHotels() {

        model.getDataFromRetrofit(model_presenter);

    }


}
