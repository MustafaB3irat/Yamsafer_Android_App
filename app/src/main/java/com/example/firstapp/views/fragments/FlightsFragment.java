package com.example.firstapp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.firstapp.R;
import com.example.firstapp.databinding.FlightsBinding;

public class FlightsFragment extends Fragment {

    private FlightsBinding flightsBinding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        flightsBinding = DataBindingUtil.inflate(inflater, R.layout.flights, container, false);

        return flightsBinding.getRoot();
    }
}
