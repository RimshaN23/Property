package com.example.property;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {

        FirebaseAuth firebaseAuth;
        FirebaseUser currentUser;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);


            firebaseAuth = FirebaseAuth.getInstance();
            currentUser = firebaseAuth.getCurrentUser();


        }

        @Override
        protected void onStart() {
            super.onStart();

            if (currentUser == null) {

                startActivity(new Intent(Splash.this, Login.class));
                finish();
            } else {
                startActivity(new Intent(Splash.this, Dashboard.class));
                finish();
            }
        }


    }

//            holder.mShare.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//
//        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//        sharingIntent.setType("text/bold");
//        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Plot Name: " + model.getName()
//        + "\nPlot No: " + model.getplot_no()
//        + "\nRoad: " + model.getRoad_id()
//        + "\nSq/yrd: " + model.getSq_yrds()
//        + "\nPrice: Rs." + model.getPlot_price_range_to() + " to Rs." + model.getPlot_price_range_from());
//        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
//        }
//        });
