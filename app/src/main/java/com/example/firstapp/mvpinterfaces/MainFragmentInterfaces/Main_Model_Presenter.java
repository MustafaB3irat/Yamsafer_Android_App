package com.example.firstapp.mvpinterfaces.MainFragmentInterfaces;

import com.example.firstapp.models.hotDealsModels.HotDealsPOJO;
import com.example.firstapp.models.popoularOnYamsaferModels.PopoularOnYamsaferPOJO;
import com.example.firstapp.models.recentSearchsModel.RecentSearchesPOJO;

import java.util.List;

public interface Main_Model_Presenter {

    void setRecentSearches(List<RecentSearchesPOJO> list);

    void setHotDeals(List<HotDealsPOJO> list);

    void setPopulars(List<PopoularOnYamsaferPOJO> list);
}
