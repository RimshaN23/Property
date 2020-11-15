package com.example.property;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.property.models.Plots;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProperty extends AppCompatActivity {

    RelativeLayout mainLayout;
    LinearLayout noNetworkLayout, updateSuccessfulLayout;
    Button retry_btn, update, goBack;

    TextView  tv_stories, tv_rooms;
    EditText stories, rooms, plot_name, priceTo, priceFrom, is_constructed;
    String plotName,constructed,  plotRoom, plotStories, price_to, price_from;

    String getIntentKey, getIntentPlotName, getIntentConstructed, getIntentRoom, getIntentStories, getIntentPriceTo, getIntentPriceFrom;

    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_property);

        mainLayout = findViewById(R.id.main_layout1);
        noNetworkLayout = findViewById(R.id.noNetworkLayout);
        updateSuccessfulLayout = findViewById(R.id.updateSuccessful);
        goBack = findViewById(R.id.goBack);
        retry_btn = findViewById(R.id.retry);

        plot_name = findViewById(R.id.plot_name);
        is_constructed = findViewById(R.id.is_constructed);
        stories = findViewById(R.id.stories);
        rooms = findViewById(R.id.rooms);
        tv_rooms = findViewById(R.id.tv_rooms);
        tv_stories = findViewById(R.id.tv_stories);
        priceTo = findViewById(R.id.price_range_to);
        priceFrom = findViewById(R.id.price_range_from);
        update = findViewById(R.id.update_btn);

        Intent intent = getIntent();
        getIntentPlotName = intent.getStringExtra("plotname");
        getIntentConstructed = intent.getStringExtra("constructed");
        getIntentRoom = intent.getStringExtra("rooms");
        getIntentStories = intent.getStringExtra("stories");
        getIntentPriceFrom = intent.getStringExtra("pricerangeFrom");
        getIntentPriceTo = intent.getStringExtra("pricerangeTo");
        getIntentKey = intent.getStringExtra("key");

        plot_name.setText(getIntentPlotName);
        is_constructed.setText(getIntentConstructed);
        rooms.setText(getIntentRoom);
        stories.setText(getIntentStories);
        priceFrom.setText(getIntentPriceFrom);
        priceTo.setText(getIntentPriceTo);


        is_constructed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] choose = {"Yes", "No"};

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProperty.this);
                builder.setTitle("Select One");
                builder.setItems(choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        constructed = choose[which];
                        is_constructed.setText(constructed);

                        if (constructed.equals("Yes")) {
                            tv_stories.setVisibility(View.VISIBLE);
                            stories.setVisibility(View.VISIBLE);
                            tv_rooms.setVisibility(View.VISIBLE);
                            rooms.setVisibility(View.VISIBLE);
                        } else {
                            tv_stories.setVisibility(View.GONE);
                            stories.setVisibility(View.GONE);
                            tv_rooms.setVisibility(View.GONE);
                            rooms.setVisibility(View.GONE);
                        }
                    }

                });
                builder.show();

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plotName = plot_name.getText().toString();
                plotRoom = rooms.getText().toString();
                plotStories = stories.getText().toString();
                price_from = priceFrom.getText().toString();
                price_to = priceTo.getText().toString();

                Plots plots = new Plots(plotName, plotRoom, plotStories, constructed, price_from, price_to);

                databaseReference = FirebaseDatabase.getInstance().getReference().child("Plots").child(getIntentKey);
                databaseReference.setValue(plots).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateSuccessfulLayout.setVisibility(View.VISIBLE);
                        mainLayout.setVisibility(View.GONE);

                    }
                });

            }
        });

        if (haveNetwork()) {
            //Connected to the internet
            mainLayout.setVisibility(View.VISIBLE);
            noNetworkLayout.setVisibility(View.GONE);
        } else {
            mainLayout.setVisibility(View.GONE);
            noNetworkLayout.setVisibility(View.VISIBLE);
        }

        retry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (haveNetwork()) {
                    mainLayout.setVisibility(View.VISIBLE);
                    noNetworkLayout.setVisibility(View.GONE);
                } else {
                    Toast.makeText(UpdateProperty.this, " Please get Online first. ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    public boolean haveNetwork() {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}