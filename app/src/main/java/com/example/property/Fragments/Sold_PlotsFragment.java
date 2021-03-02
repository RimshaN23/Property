package com.example.property.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.property.Adapter.PropertyAdapter;
import com.example.property.Adapter.PropertyAdapter2;
import com.example.property.Adapter.Sold_Property_Adapter;
import com.example.property.R;
import com.example.property.models.Plots;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Sold_PlotsFragment extends Fragment {

    RecyclerView recyclerView;
    Sold_Property_Adapter adapter;
    ProgressDialog progressDialog;
    ArrayList<Plots> arrayList = new ArrayList<>();
    EditText searchDate;
    Button button;
    final Calendar myCalendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String datesearch;
    ArrayList<Plots> plotList = new ArrayList<>();
    ArrayList<Plots> plotList2 = new ArrayList<>();
    ArrayList<String> keyList = new ArrayList<>();
    PropertyAdapter2 propertyAdapter2;

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

        searchDate = view.findViewById(R.id.datepicker_actions);
        button = view.findViewById(R.id.search);

        recyclerView = view.findViewById(R.id.recyclerview_sold);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) {
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                String monthVar = monthOfYear < 9 ? "0" + (monthOfYear + 1) : (monthOfYear + 1) + "";
                String datevar = dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth + "";
                datesearch = year + "-" + monthVar + "-" + datevar;
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datesearch != null) {
                    Log.e("date",datesearch);
                    //Firebase database referejce
                    getDatabyDate(datesearch);
                    Log.e("working2", "progress working");

                } else {

                    Toast.makeText(getContext(), "search field is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });


        getData();

        return view;
    }

    private void getData() {

        progressDialog.show();
        Log.e("working", "progress working");

        FirebaseRecyclerOptions<Plots> options =
                new FirebaseRecyclerOptions.Builder<Plots>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Plots")
                              .orderByChild("is_sold").equalTo("Yes")
                                , Plots.class)
                        .build();
        adapter = new Sold_Property_Adapter(options, getContext(),progressDialog);
        Log.e("working","adapter working");

        recyclerView.setAdapter(adapter);
        Log.e("working","rview working");
    }

    private void getDatabyDate(final String date) {
        Log.e("working2", "method working");
        Log.e("working2 datxe", date);
        progressDialog.show();


        Query databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Plots")
                .orderByChild("is_sold")
                .equalTo("Yes");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("working2", "data working3");
                plotList2.clear();
                for (DataSnapshot data : snapshot.getChildren()
                ) {
                    Log.e("working66", "data working");

                    Plots plots = data.getValue(Plots.class);
                    Log.e("working date", date);

                    data.getKey();
                    keyList.add(data.getKey());
                    if (plots != null) {
                        if (TextUtils.isEmpty(datesearch)) {
                            plotList2.add(plots);
                        } else {
                            if (plots.getDate() != null) {
                                if (date.equals(plots.getDate())) {
                                    plotList2.add(plots);
                                }
                            }

                        }
                    }
                }

                recyclerView.setAdapter(propertyAdapter2);
                propertyAdapter2 = new PropertyAdapter2(plotList2,keyList,getContext(),progressDialog);
                Log.e("working", "adapter working");

                //HERE SET THE ADAPTER
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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