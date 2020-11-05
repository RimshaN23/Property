package com.example.property;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class Add_Map extends FragmentActivity implements OnMapReadyCallback {

    Location mlocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int Request_Code = 101;
    GoogleMap map;

    LatLng newDragPosition;
    Marker marker;
    String newLat, newLng ;

    Button add_btn;

    public static String sharedPrefsMapString = "MapSharedPreferences";
    SharedPreferences.Editor preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_map);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        preferences = getSharedPreferences(sharedPrefsMapString, MODE_PRIVATE).edit();

        Getlastlocation();

        add_btn = findViewById(R.id.add_btn);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                preferences.putString("latitude", newLat);
                preferences.putString("longitude", newLng);
                preferences.apply();


                Intent intent = new Intent(Add_Map.this, Add_Property.class);
                startActivity(intent);
                finish();

            }
        });
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
                    mapFragment.getMapAsync(Add_Map.this);


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

                double lat =  map.getCameraPosition().target.latitude;
                double lng =  map.getCameraPosition().target.longitude;
                marker = googleMap.addMarker(new MarkerOptions().position(newDragPosition).draggable(true));

                newLat = ""+lat;
                newLng = ""+lng;

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