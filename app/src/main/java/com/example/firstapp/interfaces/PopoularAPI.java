package com.example.firstapp.interfaces;

import com.example.firstapp.models.data.PopoularOnYamsafer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PopoularAPI {

    String BASE_URL = "https://gist.githubusercontent.com/MustafaB3irat/2cb22c1032fd4ddf71f2e3304d67bc69/raw/2f026fda87fa9da383102ea3517f7ab9f71a375b/";

    @GET("popularyamsafer")
    Call<List<PopoularOnYamsafer>> getPopulars();
}
