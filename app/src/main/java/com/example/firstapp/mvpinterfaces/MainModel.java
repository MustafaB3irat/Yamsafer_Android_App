package com.example.firstapp.mvpinterfaces;

import com.example.firstapp.interfaces.Model_Presenter;
import com.example.firstapp.models.Hotel;
import com.example.firstapp.models.ResponseHandler;

import java.util.List;

import retrofit2.Callback;

public interface MainModel {


    void getDataFromRetrofit(Model_Presenter model_presenter);

   // ResponseHandler<List<Hotel>> getCallBack();

}
