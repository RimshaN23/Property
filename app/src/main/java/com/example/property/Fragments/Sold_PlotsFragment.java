package com.example.property.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.property.Adapter.PropertyAdapter;
import com.example.property.Adapter.Sold_Property_Adapter;
import com.example.property.R;
import com.example.property.models.Plots;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Sold_PlotsFragment extends Fragment {

    RecyclerView recyclerView;
    Sold_Property_Adapter adapter;
    ProgressDialog progressDialog;
    ArrayList<Plots> arrayList = new ArrayList<>();

    DatabaseReference databaseReference;

    public Sold_PlotsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sold__plots, null);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("loading");
        progressDialog.setCanceledOnTouchOutside(false);

        getData();

        recyclerView = view.findViewById(R.id.recyclerview_sold);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) {
        });

        adapter = new Sold_Property_Adapter(arrayList, getContext(), progressDialog);
        Log.e("working", "adapter working");

        recyclerView.setAdapter(adapter);


        Log.e("working", "rview working");


        return view;
    }

    private void getData() {

        progressDialog.show();
        Log.e("working", "progress working");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Plots");

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                arrayList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {


                    Plots model = data.getValue(Plots.class);

                    Log.e("modeladmin", model.getIs_sold());


                    if (model != null && model.getIs_sold().equals("Yes")) {

                        //check if already exists
                        Log.e("checked", "su");

                        arrayList.add(model);
                        adapter.notifyDataSetChanged();
                        Log.e("model", "" + data.getKey());
                        progressDialog.dismiss();
                    }
                    Log.e("checked", String.valueOf(arrayList.size()));

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        adapter.stopListening();
//    }


}