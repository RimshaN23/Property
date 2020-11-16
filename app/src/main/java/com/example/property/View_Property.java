package com.example.property;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.property.Adapter.PropertyAdapter;
import com.example.property.Adapter.UploadListAdapter;
import com.example.property.models.Plots;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class View_Property extends AppCompatActivity {

    RecyclerView recyclerView;
    PropertyAdapter adapter;
    ProgressDialog progressDialog;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__property);


        toolbar = (Toolbar) findViewById(R.id.viewproperty_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_keyboard_backspace_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(View_Property.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading");
        progressDialog.setCanceledOnTouchOutside(false);




        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this) {
        });

        getData();

    }

    private void getData() {
        progressDialog.show();
        FirebaseRecyclerOptions<Plots> options =
                new FirebaseRecyclerOptions.Builder<Plots>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Plots"), Plots.class)
                        .build();
        adapter = new PropertyAdapter(options, this);
        recyclerView.setAdapter(adapter);
    }

    public void dismissProgressBar() {
        progressDialog.dismiss();

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}