package com.example.firstapp.mvpinterfaces.MainFragmentInterfaces;

import com.example.firstapp.models.data.HotDeals;
import com.example.firstapp.models.data.PopoularOnYamsafer;
import com.example.firstapp.models.data.RecentSearches;

import java.util.List;

public interface Main_Model_Presenter {

    void setRecentSearches(List<RecentSearches> list);

    void setHotDeals(List<HotDeals> list);

    void setPopulars(List<PopoularOnYamsafer> list);
}
