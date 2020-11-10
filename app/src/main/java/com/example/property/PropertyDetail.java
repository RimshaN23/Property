package com.example.property;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.property.models.Plots;
import com.example.property.models.StreetRoads;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PropertyDetail extends AppCompatActivity {

    ImageView plotImage;
    FloatingActionButton floatingActionButton;
    TextView plotName, price;
    ImageButton mapImage;
    TextView plotNameAgain, address;
    TextView precinct, road, properyType, plotNo, sqyrds, isConstructed, tv_stories, tv_rooms,sellrDetails, roomsHeading, storyHeading;
String plot_name,plot_no,road_no, priceRange,sqyrd, constructed;
    String key;

    DatabaseReference databaseReference;
    Query query;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);
        mStorage = FirebaseStorage.getInstance().getReference();
        Ui();
        key= getIntent().getExtras().getString("key");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Plots");
        query = databaseReference.orderByKey().equalTo(key);
        Log.e("key",key);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {


                    try {
                        Plots model = data.getValue(Plots.class);

                        String prprty_type_id = "";

                        if (model.getProperty_type_id().equals("1")) {
                            prprty_type_id = "Residential";
                        }
                        if (model.getProperty_type_id().equals("2")) {
                            prprty_type_id = "Commercial";
                        }
                        plotName.setText(model.getName());
                        plot_name= model.getName();
                        price.setText("PKR." + model.getPlot_price_range_to() + " to PKR." + model.getPlot_price_range_from());
                        priceRange= "PKR." + model.getPlot_price_range_to() + " to PKR." + model.getPlot_price_range_from();
                        plotNameAgain.setText(model.getName());
                        address.setText("Lat: "+model.getLatitude()+"\nLng: "+model.getLongitude());
                        isConstructed.setText(model.getConstructed());
                        constructed = model.getConstructed();
                        precinct.setText(model.getPrecinct_id());
                        road.setText(model.getRoad_id());
                        road_no= model.getRoad_id();
                        properyType.setText(prprty_type_id);
                        plotNo.setText(model.getplot_no());
                        plot_no= model.getplot_no();
                        sqyrds.setText(model.getSq_yrds());
                        sqyrd= model.getSq_yrds();
                        tv_stories.setText(model.getStories());
                        tv_rooms.setText(model.getRooms());
                        sellrDetails.setText("Company Id: "+model.getCompany_id()
                        +"\nAget Name: "+model.getAgent_name()
                        +"\nAgent Id: "+model.getAgent_id());


                        if (constructed.equals("Yes")) {
                            tv_stories.setVisibility(View.VISIBLE);
                            storyHeading.setVisibility(View.VISIBLE);

                            tv_rooms.setVisibility(View.VISIBLE);
                            roomsHeading.setVisibility(View.VISIBLE);
                        }
                        if (constructed.equals("No")) {
                            tv_stories.setVisibility(View.GONE);
                            storyHeading.setVisibility(View.GONE);

                            tv_rooms.setVisibility(View.GONE);
                            roomsHeading.setVisibility(View.GONE);
                        }
                    }catch(Exception e) {
                        Log.e("exception", e.getLocalizedMessage());

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/bold");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Plot Name: "+plot_name
                + "\nPlot No: " + plot_no
        + "\nRoad: " +road_no
        + "\nSq/yrd: " +sqyrd
        + "\nPrice: Rs." +priceRange);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });
    }

    private void Ui() {
        plotImage= findViewById(R.id.image);
        floatingActionButton=  findViewById(R.id.sharebtn);
        plotName=  findViewById(R.id.tv_plot_name);
        price= findViewById(R.id.price);
        mapImage= findViewById(R.id.viewmap);
        plotNameAgain= findViewById(R.id.goldenhouse);
        address=  findViewById(R.id.tv_address_text);
        precinct= findViewById(R.id.tv_precinct);
        road= findViewById(R.id.tv_road);
        properyType=  findViewById(R.id.tv_property_type);
        plotNo= findViewById(R.id.tv_plot_no);
       sqyrds= findViewById(R.id.tv_square_yard);
        isConstructed= findViewById(R.id.is_constructed);
        tv_stories=  findViewById(R.id.stories_no);
        tv_rooms= findViewById(R.id.rooms);
        sellrDetails= findViewById(R.id.tv_addedBy);
        roomsHeading= findViewById(R.id.room_heading);
        storyHeading= findViewById(R.id.story_heading);

    }
}