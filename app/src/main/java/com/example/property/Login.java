package com.example.property;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.property.models.AgentsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText clientId_edt, companyId_edt;
    Button login_btn, retry_btn;

    RelativeLayout main_layout;
    LinearLayout noNetworkLayout;

    String agentId, companyId, agent_name;

    public static String sharedPrefsString = "SharedPreferences";
    SharedPreferences.Editor preferences;

    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        companyId_edt = findViewById(R.id.companyId);
        clientId_edt = findViewById(R.id.clientId);
        login_btn = findViewById(R.id.loginbtn);
        retry_btn = findViewById(R.id.retry);

        main_layout = findViewById(R.id.main_layout);
        noNetworkLayout = findViewById(R.id.noNetworkLayout);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading");
        progressDialog.setCanceledOnTouchOutside(false);

        preferences = getSharedPreferences(sharedPrefsString, MODE_PRIVATE).edit();

        if (haveNetwork()) {
            //Connected to the internet
            main_layout.setVisibility(View.VISIBLE);
            noNetworkLayout.setVisibility(View.GONE);
        } else {
            main_layout.setVisibility(View.GONE);
            noNetworkLayout.setVisibility(View.VISIBLE);
        }

        retry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (haveNetwork()) {
                    main_layout.setVisibility(View.VISIBLE);
                    noNetworkLayout.setVisibility(View.GONE);
                } else {
                    Toast.makeText(Login.this, " Please get Online first. ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (haveNetwork()) {
                    //Connected to the internet

                    agentId = clientId_edt.getText().toString();
                    companyId = companyId_edt.getText().toString();

                    if (!agentId.isEmpty() && !companyId.isEmpty()) {
                        progressDialog.show();


                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Company").child(companyId).child("agents");

                        valueEventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                Log.e("exception", "try check 4");

                                String logging = "";

                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    try {
                                        Log.e("exception", "try check");

                                        AgentsModel model = data.getValue(AgentsModel.class);

                                        if (model != null && model.getAgent_id().equals(agentId)) {

                                            Log.e("exception", "try check 2");
                                            agent_name = model.getAgent_name();

                                            logging = "done";

                                            preferences.putString("agentId", agentId);
                                            preferences.putString("agentName", agent_name);
                                            preferences.putString("agentNum", model.getAgent_no());
                                            preferences.putString("companyId", companyId);
                                            preferences.apply();

                                            progressDialog.dismiss();
                                            Intent intent = new Intent(Login.this, VerifyPhoneActivity.class);
                                            intent.putExtra("number", model.getAgent_no());
                                            startActivity(intent);
                                            finish();

                                            return;

                                        }


                                    } catch (Exception e) {
                                        Log.e("exception", e.getLocalizedMessage());
                                        progressDialog.dismiss();
                                        Toast.makeText(Login.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


                                    }
                                }

                                if (!logging.equals("done")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Login.this, "Enter Valid Company Id and Agent Id", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.e("Exception", databaseError.getMessage());
                                progressDialog.dismiss();
                                Toast.makeText(Login.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();


                            }
                        };


                        databaseReference.addValueEventListener(valueEventListener);

                    } else {
                        progressDialog.dismiss();

                        Toast.makeText(Login.this, "Enter Valid Company Id and Agent Id", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    main_layout.setVisibility(View.GONE);
                    noNetworkLayout.setVisibility(View.VISIBLE);
                }


            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (valueEventListener != null)
            databaseReference.removeEventListener(valueEventListener);
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