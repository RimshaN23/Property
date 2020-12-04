package com.example.property.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.property.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Sold_PlotsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sold_PlotsFragment extends Fragment {


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Sold_PlotsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sold__plots, null);


        return view;
    }
}