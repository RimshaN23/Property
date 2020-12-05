package com.example.property.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.property.Adapter.PropertyAdapter;
import com.example.property.R;
import com.example.property.models.Plots;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class Sold_PlotsFragment extends Fragment {

    RecyclerView recyclerView;
    PropertyAdapter adapter;
    ProgressDialog progressDialog;


    public Sold_PlotsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sold__plots, null);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("loading");
        progressDialog.setCanceledOnTouchOutside(true);

        recyclerView = view.findViewById(R.id.recyclerview_sold);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) {
        });

        getData();



        return view;
    }

    private void getData() {

        progressDialog.show();
        Log.e("working","progress working");

        FirebaseRecyclerOptions<Plots> options =
                new FirebaseRecyclerOptions.Builder<Plots>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Plots")
                                .child("is_sold").child("yes"), Plots.class)
                        .build();
        adapter = new PropertyAdapter(options, getContext());
        Log.e("working","adapter working");

        recyclerView.setAdapter(adapter);
        Log.e("working","rview working");

    }
}