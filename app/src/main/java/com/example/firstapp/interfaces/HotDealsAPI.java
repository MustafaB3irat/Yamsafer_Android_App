package com.example.firstapp.interfaces;

import com.example.firstapp.models.data.HotDeals;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HotDealsAPI {

    String BASE_URL = "https://gist.githubusercontent.com/MustafaB3irat/3d2e8aa80c1c278b2090caaf837844f6/raw/650f205a2385cf6dac67cb80636e5ffaa1d38a33/";

    @GET("hotdeals")
    Call<List<HotDeals>> getHotDeals();
}
