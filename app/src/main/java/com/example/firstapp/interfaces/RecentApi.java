package com.example.firstapp.interfaces;

import com.example.firstapp.models.recentSearchsModel.RecentSearchesPOJO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecentApi {

    String BASE_URL = "https://gist.githubusercontent.com/MustafaB3irat/9394c8a31149c434c8844f6755889c58/raw/22b0ab1b805b59559640c07f3a72999f54be913d/";

    @GET("recentsearches")
    Call<List<RecentSearchesPOJO>> getRecent();
}
