package com.example.property;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.property.Fragments.Plots_Fragment;
import com.example.property.Fragments.Sold_PlotsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity_view extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    androidx.appcompat.widget.Toolbar toolbar;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        toolbar =  findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        textView = findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Typeface typeface= Typeface.createFromAsset(getAssets(),"fonts/Montserrat-Italic.ttf");
        textView.setTypeface(typeface);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_keyboard_backspace_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(new Plots_Fragment());


    }

    private boolean loadFragment(Fragment fragment) {
        if(fragment != null){

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout, fragment)
                .commit();


        return true;
    }


        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()){

            case R.id.navigation_sold:
                fragment = new Sold_PlotsFragment();
                break;

            case R.id.navigation_allplots:
                fragment = new Plots_Fragment();
                break;


        }

        return loadFragment(fragment);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}