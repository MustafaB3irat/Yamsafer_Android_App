package com.example.firstapp.models;


import com.example.firstapp.models.data.Hotel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ResponseHandler<T> implements Callback<T> {

    private List<Hotel> data;

    public List<Hotel> getData() {
        return data;
    }

    public void setData(List<Hotel> data) {
        this.data = data;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if (response.isSuccessful())
            onResult(true, response.body());
        else onResult(false, null);

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

        onResult(false, null);

    }

    public abstract void onResult(Boolean isSuccessful, T result);
}
