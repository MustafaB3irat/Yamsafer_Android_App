package com.example.firstapp.interfaces;

import com.example.firstapp.models.Hotel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HotelApi {
    public final String BASE_URL = "https://gist.githubusercontent.com/MustafaB3irat/4ee3858c7757c5b58271d07afda78eda/raw/9655d98ec092d273e94b361528a4d5a95b080507/";


    @GET("HotelApi")
    Call<List<Hotel>> getHotels();


}
