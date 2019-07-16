package com.example.firstapp.Presenters;

import com.example.firstapp.models.data.HotDeals;
import com.example.firstapp.models.data.PopoularOnYamsafer;
import com.example.firstapp.mvpinterfaces.MainFragmentInterfaces.Main;
import com.example.firstapp.mvpinterfaces.MainFragmentInterfaces.Main_Model_Presenter;
import com.example.firstapp.models.data.RecentSearches;

import java.util.List;

public class MainPresenters implements Main.MainPresenter {

    private Main.MainFragment fragment;
    private Main.MainModel mainModel;

    public MainPresenters(Main.MainFragment fragment) {
        this.fragment = fragment;
        mainModel = new com.example.firstapp.models.MainModel();
    }


    private Main_Model_Presenter model_presenter = new Main_Model_Presenter() {
        @Override
        public void setRecentSearches(List<RecentSearches> list) {
            fragment.getRecentSearches(list);
        }

        @Override
        public void setHotDeals(List<HotDeals> list) {
            fragment.getHotDeals(list);
        }

        @Override
        public void setPopulars(List<PopoularOnYamsafer> list) {

            fragment.getPopulars(list);

        }
    };

    @Override
    public void getDataFromRetrofit() {

        mainModel.setRecentSearchesData(model_presenter);
        mainModel.setHotDealsData(model_presenter);
        mainModel.setPopularOnYamsaferData(model_presenter);
    }
}
