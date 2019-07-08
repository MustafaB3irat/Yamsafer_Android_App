package com.example.firstapp.models;

import com.example.firstapp.interfaces.HotelApi;
import com.example.firstapp.mvpinterfaces.Model_Presenter;
import com.example.firstapp.mvpinterfaces.HotelListModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HotelModel implements HotelListModel {

    //private ResponseHandler<List<Hotel>> callBack;


//    @Override
//    public ResponseHandler<List<Hotel>> getCallBack() {
//        return callBack;
//    }

//    public void setCallBack(ResponseHandler<List<Hotel>> callBack) {
//        this.callBack = callBack;
//    }

    private static Gson gson = new GsonBuilder().setLenient().create();

    public HotelModel() {
    }

    @Override
    public void getDataFromRetrofit(final Model_Presenter model_presenter) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(HotelApi.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();

        HotelApi hotelApi = retrofit.create(HotelApi.class);
        Call<List<Hotel>> call = hotelApi.getHotels();
        call.enqueue(new ResponseHandler<List<Hotel>>() {
            @Override
            public void onResult(Boolean isSuccessful, List<Hotel> result) {
                if (isSuccessful) {

                    // this.setData(result);
                    model_presenter.getDataFromModel(result);
                }
            }
        });

    }

}
