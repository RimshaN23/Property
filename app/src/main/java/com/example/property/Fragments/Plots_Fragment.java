package com.example.property.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.property.Adapter.PropertyAdapter;
import com.example.property.R;
import com.example.property.models.Plots;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Plots_Fragment extends Fragment {

    RecyclerView recyclerView;
    PropertyAdapter adapter;
    ProgressDialog progressDialog;
    EditText searchDate;

    final Calendar myCalendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String datesearch;



    public Plots_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_plots, null);

        searchDate = view.findViewById(R.id.datepicker_actions);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("loading");
        progressDialog.setCanceledOnTouchOutside(false);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) {
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                String monthVar = monthOfYear < 9 ? "0" + (monthOfYear + 1) : (monthOfYear + 1) + "";
                String datevar = dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth + "";
                datesearch = datevar + "-" + monthVar + "-" + year;
                searchDate.setText(datesearch);

            }

        };

        searchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
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
                                .orderByChild("is_sold").equalTo("No"),Plots.class)
                                .build();
        adapter = new PropertyAdapter(options, getContext(),progressDialog);
        Log.e("working","adapter working");

        recyclerView.setAdapter(adapter);
        Log.e("working","rview working");

    }



    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.stopListening();
    }


}
