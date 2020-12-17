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

