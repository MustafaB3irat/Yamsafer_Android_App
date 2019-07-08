package com.example.firstapp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firstapp.Presenters.MainPresenters;
import com.example.firstapp.R;
import com.example.firstapp.databinding.MainBinding;
import com.example.firstapp.models.hotDealsModels.HotDealsPOJO;
import com.example.firstapp.models.popoularOnYamsaferModels.PopoularOnYamsaferPOJO;
import com.example.firstapp.models.recentSearchsModel.RecentSearchesPOJO;
import com.example.firstapp.mvpinterfaces.MainFragmentInterfaces.MainFragment;
import com.example.firstapp.recyclerviewadapters.HotDealsAdapter;
import com.example.firstapp.recyclerviewadapters.PopularOnYamsaferAdapter;
import com.example.firstapp.recyclerviewadapters.RecentSearchesAdapter;
import com.example.firstapp.views.MainActivity;

import java.util.List;

public class MainFragment extends Fragment implements com.example.firstapp.mvpinterfaces.MainFragmentInterfaces.MainFragment {

    private RecentSearchesAdapter recentSearchesAdapter;
    private HotDealsAdapter hotDealsAdapter;
    private PopularOnYamsaferAdapter popularOnYamsaferAdapter;
    private MainBinding mainBinding;
    private com.example.firstapp.mvpinterfaces.MainFragmentInterfaces.MainPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mainBinding = DataBindingUtil.inflate(inflater, R.layout.main, container, false);
        presenter = new MainPresenters(this);

        return mainBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initSpinner();

        presenter.getDataFromRetrofit();


        initSearchBar();
    }

    @Override
    public void getRecentSearches(List<RecentSearchesPOJO> recentSearchesPOJOS) {

        mainBinding.recentSearches.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recentSearchesAdapter = new RecentSearchesAdapter(recentSearchesPOJOS);
        mainBinding.recentSearches.setAdapter(recentSearchesAdapter);

    }

    @Override
    public void getHotDeals(List<HotDealsPOJO> hotDealsPOJOS) {

        mainBinding.hotDeals.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        hotDealsAdapter = new HotDealsAdapter(hotDealsPOJOS);
        mainBinding.hotDeals.setAdapter(hotDealsAdapter);

    }

    @Override
    public void getPopulars(List<PopoularOnYamsaferPOJO> popoularOnYamsaferPOJOS) {


        mainBinding.popularOnYamsafer.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularOnYamsaferAdapter = new PopularOnYamsaferAdapter(popoularOnYamsaferPOJOS);
        mainBinding.popularOnYamsafer.setAdapter(popularOnYamsaferAdapter);

        mainBinding.spinnerBg.setVisibility(View.GONE);
        mainBinding.spinner.setVisibility(View.GONE);

    }

    @Override
    public void initSearchBar() {

        ((MainActivity) getActivity()).setSearchEditTextClickable(mainBinding.searchEditText);
    }

    @Override
    public void initSpinner() {

        mainBinding.spinnerBg.setVisibility(View.VISIBLE);
        mainBinding.spinner.setVisibility(View.VISIBLE);

    }
}
