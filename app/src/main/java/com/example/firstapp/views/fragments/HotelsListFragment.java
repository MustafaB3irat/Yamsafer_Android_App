package com.example.firstapp.views.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.firstapp.Presenters.HotelListPresenters;
import com.example.firstapp.databinding.HotelsListBinding;
import com.example.firstapp.mvpinterfaces.HotelList;
import com.example.firstapp.models.data.Hotel;
import com.example.firstapp.recyclerviewadapters.HotelsListAdapter;
import com.example.firstapp.R;

import java.util.List;


@BindingMethods({
        @BindingMethod(type = ImageView.class, attribute = "android:src", method = "setImageResource"),
        @BindingMethod(type = RatingBar.class, attribute = "android:numStars", method = "setNumStar")
})
public class HotelsListFragment extends Fragment implements HotelList.HotelListView {


    private androidx.recyclerview.widget.RecyclerView.Adapter adapter;
    private HotelsListBinding hotelsListBinding;
    private HotelList.HotelListPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        hotelsListBinding = DataBindingUtil.inflate(inflater, R.layout.hotels_list, container, false);
        hotelsListBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        presenter = new HotelListPresenters(this);
        presenter.onRequestHotels();
        return hotelsListBinding.getRoot();
    }

    @Override
    public void getHotels(List<Hotel> hotels) {
        adapter = new HotelsListAdapter(hotels);
        hotelsListBinding.recyclerview.setAdapter(adapter);


    }


}
