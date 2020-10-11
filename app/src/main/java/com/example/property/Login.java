package com.example.property;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.property.models.AgentsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText clientId_edt, number_edt;
    Button login_btn;

    String clientId, number;

    ProgressDialog progressDialog;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        clientId_edt = findViewById(R.id.clientId);
        number_edt = findViewById(R.id.number);
        login_btn = findViewById(R.id.loginbtn);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading");
        progressDialog.setCanceledOnTouchOutside(false);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Company").child("agents");

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();

                clientId = clientId_edt.getText().toString();
                number = number_edt.getText().toString();
                Log.e("exception", "try check 3");

                databaseReference.child(clientId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.e("exception", "try check 4");

                        try {
                            Log.e("exception", "try check");

                            AgentsModel model = dataSnapshot.getValue(AgentsModel.class);

                            if (model != null && model.getClient_no().equals(number)) {

                                Log.e("exception", "try check 2");

                                progressDialog.dismiss();
                                Intent intent = new Intent(Login.this, VerifyPhoneActivity.class);
                                intent.putExtra("number", number);
                                startActivity(intent);
                                finish();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Login.this, "Enter A Valid Numbeer", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("exception", e.getLocalizedMessage());
                            progressDialog.dismiss();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Exception", databaseError.getMessage());
                        progressDialog.dismiss();

                    }
                });
            }
        });

    }


}