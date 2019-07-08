package com.example.firstapp.mvpinterfaces.MainFragmentInterfaces;

import com.example.firstapp.models.hotDealsModels.HotDealsPOJO;
import com.example.firstapp.models.popoularOnYamsaferModels.PopoularOnYamsaferPOJO;
import com.example.firstapp.models.recentSearchsModel.RecentSearchesPOJO;

import java.util.List;

public interface MainFragment {

    void getRecentSearches(List<RecentSearchesPOJO> recentSearchesPOJOS);

    void getHotDeals(List<HotDealsPOJO> hotDealsPOJOS);

    void getPopulars(List<PopoularOnYamsaferPOJO> popoularOnYamsaferPOJOS);

    void initSearchBar();

    void initSpinner();
}
