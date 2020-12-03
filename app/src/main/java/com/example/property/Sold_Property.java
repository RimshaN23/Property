package com.example.property;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sold_Property extends AppCompatActivity {

    EditText client_name, client_number, client_cnic, sold_price;
    String clientName, clientNumber, clientCnic, soldPrice;
    Button submit_registry, goBack;
    String key;
    DatabaseReference databaseReference;

    RelativeLayout mainLayout;
    LinearLayout updateSuccessfulLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sold__property_activity);

        client_name = findViewById(R.id.client_name);
        client_number = findViewById(R.id.client_no);
        client_cnic = findViewById(R.id.cnic_no);
        sold_price = findViewById(R.id.final_price);
        submit_registry = findViewById(R.id.submit_registry);
        goBack = findViewById(R.id.goBack);

        mainLayout = findViewById(R.id.main_layout1);
        updateSuccessfulLayout = findViewById(R.id.updateSuccessful);

        key = getIntent().getExtras().getString("key");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Plots").child(key);

        submit_registry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientName = client_name.getText().toString();
                clientNumber = client_number.getText().toString();
                clientCnic = client_cnic.getText().toString();
                soldPrice = sold_price.getText().toString();

                databaseReference.child("client_name").setValue(clientName);
                databaseReference.child("client_number").setValue(clientNumber);
                databaseReference.child("client_cnic").setValue(clientCnic);
                databaseReference.child("sold_price").setValue(soldPrice);

                updateSuccessfulLayout.setVisibility(View.VISIBLE);
                mainLayout.setVisibility(View.GONE);
            }
        });


        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sold_Property.this, PropertyDetail.class);
                intent.putExtra("key", key);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Sold_Property.this, PropertyDetail.class);
        intent.putExtra("key", key);
        startActivity(intent);
        finish();
    }
}