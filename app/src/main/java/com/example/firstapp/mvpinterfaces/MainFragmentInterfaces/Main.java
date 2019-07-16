package com.example.firstapp.mvpinterfaces.MainFragmentInterfaces;

import com.example.firstapp.models.data.HotDeals;
import com.example.firstapp.models.data.PopoularOnYamsafer;
import com.example.firstapp.models.data.RecentSearches;

import java.util.List;

public interface Main {
    interface MainFragment {

        void getRecentSearches(List<RecentSearches> recentSearches);

        void getHotDeals(List<HotDeals> hotDeals);

        void getPopulars(List<PopoularOnYamsafer> popoularOnYamsafers);

        void initSearchBar();

        void initSpinner();
    }

    interface MainModel {

        void setRecentSearchesData(final Main_Model_Presenter presenter);

        void setHotDealsData(final Main_Model_Presenter presenter);

        void setPopularOnYamsaferData(final Main_Model_Presenter presenter);

    }

    interface MainPresenter {

        void getDataFromRetrofit();
    }
}
