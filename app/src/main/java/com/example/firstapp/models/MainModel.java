package com.example.firstapp.models;

import com.example.firstapp.interfaces.HotDealsAPI;
import com.example.firstapp.interfaces.PopoularAPI;
import com.example.firstapp.interfaces.RecentApi;
import com.example.firstapp.models.data.HotDeals;
import com.example.firstapp.models.data.PopoularOnYamsafer;
import com.example.firstapp.models.data.RecentSearches;
import com.example.firstapp.mvpinterfaces.MainFragmentInterfaces.Main;
import com.example.firstapp.mvpinterfaces.MainFragmentInterfaces.Main_Model_Presenter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainModel implements Main.MainModel {


    private Gson gson = new GsonBuilder().setLenient().create();


    @Override
    public void setRecentSearchesData(final Main_Model_Presenter presenter) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RecentApi.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();

        RecentApi recentApi = retrofit.create(RecentApi.class);

        Call<List<RecentSearches>> call = recentApi.getRecent();

        call.enqueue(new ResponseHandler<List<RecentSearches>>() {
            @Override
            public void onResult(Boolean isSuccessful, List<RecentSearches> result) {

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

        Call<List<HotDeals>> call = hotDealsAPI.getHotDeals();

        call.enqueue(new ResponseHandler<List<HotDeals>>() {
            @Override
            public void onResult(Boolean isSuccessful, List<HotDeals> result) {

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

        Call<List<PopoularOnYamsafer>> call = popoularAPI.getPopulars();

        call.enqueue(new ResponseHandler<List<PopoularOnYamsafer>>() {
            @Override
            public void onResult(Boolean isSuccessful, List<PopoularOnYamsafer> result) {

                if (isSuccessful) {
                    presenter.setPopulars(result);
                }
            }
        });

    }
}
