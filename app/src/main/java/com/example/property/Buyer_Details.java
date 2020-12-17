package com.example.property;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import me.relex.circleindicator.CircleIndicator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.property.Adapter.CnicAdapter;
import com.example.property.Adapter.SlidingImage_Adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Buyer_Details extends AppCompatActivity {

    TextView name,number,nic;
    String bname,bnum,bnic,
    key;

    CnicAdapter adapter;
    private static ViewPager mPager;
    ArrayList<String> imageUrl = new ArrayList<>();

    DatabaseReference databaseReference;
    Query query;
    private StorageReference mStorage;
    private static int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer__details);

        name= findViewById(R.id.tv_buyer_name);
        number= findViewById(R.id.buyerNum);
        nic= findViewById(R.id.buyerNic);
        mPager = findViewById(R.id.pageradapter);

        bname = getIntent().getExtras().getString("name");
        bnum = getIntent().getExtras().getString("num");
       bnic = getIntent().getExtras().getString("nic");

        name.setText(bname);
        number.setText("Number: "+bnum);
        nic.setText("Cnic: "+bnic);

        imageUrl = getIntent().getExtras().getStringArrayList("cnic");
        Log.e("nicarray",String.valueOf(imageUrl));
        key = getIntent().getExtras().getString("key");


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Plots");
        query = databaseReference.orderByKey().equalTo(key);

        try {
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (imageUrl!=null && imageUrl.size()>0){
                        adapter = new CnicAdapter(Buyer_Details.this, imageUrl);
                        mPager.setAdapter(adapter);
                    }
                    else {
                        mPager.setVisibility(View.GONE);
                    }
                    CircleIndicator indicator = findViewById(R.id.indicator);

                    indicator.setViewPager(mPager);

                    indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                        @Override
                        public void onPageSelected(int position) {
                            currentPage = position;

                        }

                        @Override
                        public void onPageScrolled(int pos, float arg1, int arg2) {

                        }

                        @Override
                        public void onPageScrollStateChanged(int pos) {

                        }
                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } catch (Exception e) {
            Log.e("exception", e.getLocalizedMessage());
        }


    }
}