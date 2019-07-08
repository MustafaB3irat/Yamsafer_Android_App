package com.example.firstapp.Presenters;

import com.example.firstapp.models.hotDealsModels.HotDealsPOJO;
import com.example.firstapp.models.popoularOnYamsaferModels.PopoularOnYamsaferPOJO;
import com.example.firstapp.mvpinterfaces.MainFragmentInterfaces.Main_Model_Presenter;
import com.example.firstapp.models.recentSearchsModel.RecentSearchesPOJO;
import com.example.firstapp.mvpinterfaces.MainFragmentInterfaces.MainPresenter;
import com.example.firstapp.mvpinterfaces.MainFragmentInterfaces.MainFragment;
import com.example.firstapp.mvpinterfaces.MainFragmentInterfaces.MainModel;

import java.util.List;

public class MainPresenters implements com.example.firstapp.mvpinterfaces.MainFragmentInterfaces.MainPresenter {

    private MainFragment fragment;
    private MainModel mainModel;

    public MainPresenters(MainFragment fragment) {
        this.fragment = fragment;
        mainModel = new com.example.firstapp.models.MainModel();
    }


    private Main_Model_Presenter model_presenter = new Main_Model_Presenter() {
        @Override
        public void setRecentSearches(List<RecentSearchesPOJO> list) {
            fragment.getRecentSearches(list);
        }

        @Override
        public void setHotDeals(List<HotDealsPOJO> list) {
            fragment.getHotDeals(list);
        }

        @Override
        public void setPopulars(List<PopoularOnYamsaferPOJO> list) {

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
