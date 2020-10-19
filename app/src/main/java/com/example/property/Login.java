package com.example.property;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.property.models.AgentsModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText clientId_edt, companyId_edt;
    Button login_btn;

    String clientId, companyId;

    ProgressDialog progressDialog;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//
//        if(haveNetwork()){
//            //Connected to the internet
//        }
//        else{
//            final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
//                    "No internet connection.",
//                    Snackbar.LENGTH_SHORT);
//            snackbar.setActionTextColor(ContextCompat.getColor(getApplicationContext(),
//                    R.color.white));
//
//        }
        companyId_edt = findViewById(R.id.companyId);
        clientId_edt = findViewById(R.id.clientId);
        login_btn = findViewById(R.id.loginbtn);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading");
        progressDialog.setCanceledOnTouchOutside(false);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(haveNetwork()){
                    //Connected to the internet
                    progressDialog.show();

                    clientId = clientId_edt.getText().toString();
                    companyId = companyId_edt.getText().toString();
                    Log.e("exception", "try check 3");
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Company").child(companyId).child("agents");

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Log.e("exception", "try check 4");


                            for (DataSnapshot data:dataSnapshot.getChildren()) {
                                try {
                                    Log.e("exception", "try check");

                                    AgentsModel model = data.getValue(AgentsModel.class);

                                    if (model != null && model.getAgent_id().equals(clientId)) {

                                        Log.e("exception", "try check 2");

                                        progressDialog.dismiss();
                                        Intent intent = new Intent(Login.this, VerifyPhoneActivity.class);
                                        intent.putExtra("number", model.getAgent_no());
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(Login.this, "Enter Valid Company Id and Agent Id", Toast.LENGTH_SHORT).show();
                                        Log.e("exception", "mymymy");

                                    }
                                } catch (Exception e) {
                                    Log.e("exceptiono", e.getLocalizedMessage());
                                    progressDialog.dismiss();
                                    Toast.makeText(Login.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


                                }
                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("Exception", databaseError.getMessage());
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    });
                }
                else{
                    progressDialog.dismiss();
                    final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                            "No internet connection.",
                            Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(ContextCompat.getColor(getApplicationContext(),
                            R.color.white));
                    snackbar.setAction(R.string.try_again, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //recheck internet connection and call DownloadJson if there is internet
                        }
                    }).show();
                }


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