package com.example.firstapp.models;

import android.util.Log;

import com.example.firstapp.interfaces.HotDealsAPI;
import com.example.firstapp.interfaces.PopoularAPI;
import com.example.firstapp.interfaces.RecentApi;
import com.example.firstapp.models.hotDealsModels.HotDealsPOJO;
import com.example.firstapp.models.popoularOnYamsaferModels.PopoularOnYamsaferPOJO;
import com.example.firstapp.models.recentSearchsModel.RecentSearchesPOJO;
import com.example.firstapp.mvpinterfaces.MainFragmentInterfaces.Main_Model_Presenter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainModel implements com.example.firstapp.mvpinterfaces.MainFragmentInterfaces.MainModel {


    private Gson gson = new GsonBuilder().setLenient().create();


    @Override
    public void setRecentSearchesData(final Main_Model_Presenter presenter) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RecentApi.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();

        RecentApi recentApi = retrofit.create(RecentApi.class);

        Call<List<RecentSearchesPOJO>> call = recentApi.getRecent();

        call.enqueue(new ResponseHandler<List<RecentSearchesPOJO>>() {
            @Override
            public void onResult(Boolean isSuccessful, List<RecentSearchesPOJO> result) {

                if (isSuccessful) {
                    presenter.setRecentSearches(result);
                }
            }
        });

    }

    @Override
    public void setHotDealsData(Main_Model_Presenter presenter) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(HotDealsAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();

        HotDealsAPI hotDealsAPI = retrofit.create(HotDealsAPI.class);

        Call<List<HotDealsPOJO>> call = hotDealsAPI.getHotDeals();

        call.enqueue(new ResponseHandler<List<HotDealsPOJO>>() {
            @Override
            public void onResult(Boolean isSuccessful, List<HotDealsPOJO> result) {

                if (isSuccessful) {
                    presenter.setHotDeals(result);
                }
            }
        });
    }

    @Override
    public void setPopularOnYamsaferData(Main_Model_Presenter presenter) {


        Retrofit retrofit = new Retrofit.Builder().baseUrl(PopoularAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();

        PopoularAPI popoularAPI = retrofit.create(PopoularAPI.class);

        Call<List<PopoularOnYamsaferPOJO>> call = popoularAPI.getPopulars();

        call.enqueue(new ResponseHandler<List<PopoularOnYamsaferPOJO>>() {
            @Override
            public void onResult(Boolean isSuccessful, List<PopoularOnYamsaferPOJO> result) {

                if (isSuccessful) {
                    presenter.setPopulars(result);
                }
            }
        });

    }
}
