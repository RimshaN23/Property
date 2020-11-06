package com.example.property;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.property.models.AgentsModel;
import com.example.property.models.Plots;
import com.example.property.models.Precinct;
import com.example.property.models.SearchItems;
import com.example.property.models.StreetRoads;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;


public class Add_Property extends AppCompatActivity implements OnMapReadyCallback {

    EditText company_id, agent_id, agent_name, is_constructed;

    TextView precinct, road, plot_address, tv_stories, tv_rooms;
    EditText plot_no, square_yard, stories, rooms, plot_name, priceTo, priceFrom, property_type;
    Dialog dialog;

    RelativeLayout mainLayout, mapLayout;
    LinearLayout noNetworkLayout;

    String mapVisibility = "gone";

    ProgressDialog progressDialog;

    String precinct_id;
    String precinct_name;
    String roadname, roadid;
    String agentId, companyId, agentName;

    String prprty_type, prprty_type_id, constructed;
    DatabaseReference databaseReference;

    String plotName, plotId, plotRoom, plotStories, plotAddress, plotSq_yrd, price_to, price_from;

    double latitude;
    double longitude;
    Button enter, retry_btn;
    Toolbar toolbar;


    Location mlocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int Request_Code = 101;
    GoogleMap map;

    LatLng newDragPosition;
    Marker marker;
    double lat, lng;
    Button add_btn;

    @Override
    public void onBackPressed() {

        if (mapVisibility.equals("visible")) {

            mainLayout.setVisibility(View.VISIBLE);
            mapLayout.setVisibility(View.GONE);
            mapVisibility = "gone";

        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__property);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        uId();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        toolbar = (Toolbar) findViewById(R.id.addproperty_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_keyboard_backspace_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mapVisibility.equals("visible")) {

                    mainLayout.setVisibility(View.VISIBLE);
                    mapLayout.setVisibility(View.GONE);
                    mapVisibility = "gone";

                } else {
                    Intent intent = new Intent(Add_Property.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                }
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
                    Toast.makeText(Add_Property.this, " Please get Online first. ", Toast.LENGTH_SHORT).show();
                }
            }
        });


        SharedPreferences preferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        companyId = preferences.getString("companyId", null);
        agentName = preferences.getString("agentName", null);
        agentId = preferences.getString("agentId", null);

        company_id.setText(companyId);
        agent_name.setText(agentName);
        agent_id.setText(agentId);

        plot_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addMap();
                mapVisibility = "visible";
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mainLayout.setVisibility(View.VISIBLE);
                mapLayout.setVisibility(View.GONE);
                mapVisibility = "gone";
                latitude = lat;
                longitude = lng;
                plot_address.setText(latitude + "\n" + longitude);

            }
        });

        is_constructed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] choose = {"Yes", "No"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Add_Property.this);
                builder.setTitle("Pick one");
                builder.setItems(choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on colors[which]
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

        property_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] choose = {"Residential", "Commercial"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Add_Property.this);
                builder.setTitle("Pick one");
                builder.setItems(choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on colors[which]
                        prprty_type = choose[which];
                        property_type.setText(prprty_type);


                        if (prprty_type.equals("Residential")) {
                            prprty_type_id = "1";
                        }
                        if (prprty_type.equals("Commercial")) {
                            prprty_type_id = "2";
                        }
                    }
                });
                builder.show();

            }
        });


        precinct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Precinct");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        List<SearchItems> searchItemsList = new ArrayList<>();

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            try {
                                Log.e("datasnap", "working");

                                Precinct model = data.getValue(Precinct.class);

                                if (model != null && model.getProperty_type_id().equals(prprty_type_id)) {
                                    String name = model.getName();

                                    String id = model.getPrecinct_id();

                                    Log.e("dataSnap", name + "  " + id);

                                    SearchItems searchItems = new SearchItems(name, id);
                                    searchItemsList.add(searchItems);
                                    Log.e("dataSnap2", searchItems.getName());

                                }

                            } catch (Exception e) {
                                Log.e("exception", e.getLocalizedMessage());

                            }
                        }


                        dialog = new Dialog(Add_Property.this);
                        dialog.setContentView(R.layout.searchable_dialog);

                        // dialog.getWindow().setLayout(650, 800);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        progressDialog.dismiss();
                        dialog.show();

                        EditText editText = dialog.findViewById(R.id.edit_precinct);
                        ListView listView = dialog.findViewById(R.id.listView);


                        final ArrayAdapter<SearchItems> adapter = new ArrayAdapter<SearchItems>(Add_Property.this,
                                R.layout.dropdown_item, searchItemsList);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        listView.setAdapter(adapter);

                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                adapter.getFilter().filter(charSequence);
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                SearchItems user = (SearchItems) adapterView.getItemAtPosition(i);
                                precinctData(user);
                                dialog.dismiss();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }


        });


        road.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("StreetRoads");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.e("database_change", "try check 4");
                        List<SearchItems> searchItemsList = new ArrayList<>();

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            try {
                                Log.e("datasnap", "working");

                                StreetRoads model = data.getValue(StreetRoads.class);
                                if (model != null && model.getPrecinct_id().equals(precinct_id)) {

                                    String name = model.getName();

                                    String id = model.getRoad_id();

                                    Log.e("dataSnap", name + "  " + id);

                                    SearchItems searchItems = new SearchItems(name, id);
                                    searchItemsList.add(searchItems);
                                    Log.e("dataSnap2", searchItems.getName());

                                }
                            } catch (Exception e) {
                                Log.e("exception", e.getLocalizedMessage());

                            }
                        }


                        dialog = new Dialog(Add_Property.this);
                        dialog.setContentView(R.layout.searchable_dialog);

                        // dialog.getWindow().setLayout(650, 800);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        progressDialog.dismiss();
                        dialog.show();

                        EditText editText = dialog.findViewById(R.id.edit_precinct);
                        ListView listView = dialog.findViewById(R.id.listView);


                        final ArrayAdapter<SearchItems> adapter = new ArrayAdapter<SearchItems>(Add_Property.this,
                                R.layout.dropdown_item, searchItemsList);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        listView.setAdapter(adapter);

                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                adapter.getFilter().filter(charSequence);
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                SearchItems user = (SearchItems) adapterView.getItemAtPosition(i);
                                roadData(user);
                                dialog.dismiss();
                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (haveNetwork()) {

                    plotName = plot_name.getText().toString();
                    plotId = plot_no.getText().toString();
                    plotRoom = rooms.getText().toString();
                    plotStories = stories.getText().toString();
                    plotSq_yrd = square_yard.getText().toString();
                    plotAddress = plot_address.getText().toString();
                    price_from = priceFrom.getText().toString();
                    price_to = priceTo.getText().toString();

                    if (!property_type.getText().toString().isEmpty() && !precinct.getText().toString().isEmpty()
                            && !road.getText().toString().isEmpty() && !plot_name.getText().toString().isEmpty()
                            && !plot_name.getText().toString().isEmpty()) {

                        Plots plots = new Plots(precinct_id, prprty_type_id, roadid, plotName, latitude, longitude, plotSq_yrd, plotRoom, plotStories, companyId,
                                plotId, constructed, "No", agentId, agentName, price_from, price_to);

                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Plots");

                        databaseReference.push().setValue(plots).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    company_id.setText("");
                                    agent_id.setText("");
                                    agent_name.setText("");
                                    property_type.setText("");
                                    is_constructed.setHint("Yes or No");
                                    is_constructed.setText("");
                                    precinct.setText("");
                                    road.setText("");
                                    plot_name.setText("");
                                    plot_no.setText("");
                                    plot_address.setText("");
                                    square_yard.setText("");
                                    stories.setText("");
                                    rooms.setText("");
                                    tv_stories.setText("");
                                    tv_rooms.setText("");
                                    priceTo.setText("");
                                    priceFrom.setText("");

                                    SharedPreferences.Editor preferencesMap = getSharedPreferences("MapSharedPreferences", MODE_PRIVATE).edit();
                                    preferencesMap.clear();
                                    preferencesMap.apply();

                                    Toast.makeText(Add_Property.this, " Plot Register Successful :) ", Toast.LENGTH_LONG).show();


                                } else {
                                    Log.e("Execption2", task.getException().getMessage());
                                    progressDialog.dismiss();

                                }
                            }
                        });
                    } else {
                        Toast.makeText(Add_Property.this, " Please enter data in all fields with *", Toast.LENGTH_LONG).show();
                    }


                } else {

                    mainLayout.setVisibility(View.GONE);
                    noNetworkLayout.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void precinctData(SearchItems user) {

        precinct_name = user.getName();
        precinct_id = user.getId();
        Log.e("displayUserData", precinct_name + "   " + precinct_id);
        precinct.setText(precinct_name);

    }

    private void roadData(SearchItems user) {

        roadname = user.getName();
        roadid = user.getId();
        road.setText(roadname);

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

    private void uId() {


        mainLayout = findViewById(R.id.main_layout1);
        mapLayout = findViewById(R.id.map_layout);
        noNetworkLayout = findViewById(R.id.noNetworkLayout);
        retry_btn = findViewById(R.id.retry);
        add_btn = findViewById(R.id.add_btn);

        company_id = findViewById(R.id.company_id);
        agent_id = findViewById(R.id.agent_id);
        agent_name = findViewById(R.id.agent_name);
        priceTo = findViewById(R.id.price_range_to);
        priceFrom = findViewById(R.id.price_range_from);
        property_type = findViewById(R.id.property_type);
        precinct = findViewById(R.id.precinct);
        plot_name = findViewById(R.id.plot_name);
        plot_no = findViewById(R.id.plot_no);
        square_yard = findViewById(R.id.square_yard);
        road = findViewById(R.id.road);
        plot_address = findViewById(R.id.plot_address);
        is_constructed = findViewById(R.id.is_constructed);
        stories = findViewById(R.id.stories);
        rooms = findViewById(R.id.rooms);
        tv_rooms = findViewById(R.id.tv_rooms);
        tv_stories = findViewById(R.id.tv_stories);
        enter = findViewById(R.id.enter_registry);

    }

    private void addMap() {
        mapVisibility = "visible";
        mapLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.GONE);
        Getlastlocation();


    }

    private void Getlastlocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_Code);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {

                    mlocation = location;

                    Toast.makeText(getApplicationContext(), mlocation.getLatitude() + "" + mlocation.getLongitude(), Toast.LENGTH_SHORT).show();

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.currentmaplocation);
                    mapFragment.getMapAsync(Add_Property.this);


                }
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        map = googleMap;


        LatLng latLng = new LatLng(mlocation.getLatitude(), mlocation.getLongitude());
        marker = googleMap.addMarker(new MarkerOptions().position(latLng).title("Current Location").draggable(true));
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));


        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                if (marker != null && map != null) {
                    marker.remove();
                    newDragPosition = cameraPosition.target;
                    marker = googleMap.addMarker(new MarkerOptions().position(newDragPosition).draggable(true));


                    //add place pe newDragPosition sai latlng miljayega

                }
            }
        });
        map.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if (map != null) {
                    marker.remove();
                }

                newDragPosition = map.getCameraPosition().target;

                lat = map.getCameraPosition().target.latitude;
                lng = map.getCameraPosition().target.longitude;
                marker = googleMap.addMarker(new MarkerOptions().position(newDragPosition).draggable(true));

            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case Request_Code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Getlastlocation();
                }
                break;
        }
    }
}