package com.example.property;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.property.models.AgentsModel;
import com.example.property.models.Precinct;
import com.example.property.models.SearchItems;
import com.example.property.models.StreetRoads;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;


public class Add_Property extends AppCompatActivity {

    EditText company_id, agent_id, agent_name, price_range, property_type,
            plot_name, plot_no, square_yard, plot_address, is_constructed, stories, rooms;

    TextView spinner_precinct, road;
    TextView tv_stories, tv_rooms;
    Dialog dialog;

    ProgressDialog progressDialog;


//    String  s_company_id, s_agent_id, s_agent_name, s_price_range, s_property_type, s_precinct,
//            plot_name, plot_no, square_yard, road, plot_address, is_constructed, stories, rooms;

    String prprty_type, prprty_type_id, constructed, precinct_id;
    DatabaseReference databaseReference;

    ArrayList<String> precinct_array = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__property);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);


        uId();

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


        spinner_precinct.setOnClickListener(new View.OnClickListener() {
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
                                displayUserData(user);
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


//     road.setOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View view) {
//             databaseReference = FirebaseDatabase.getInstance().getReference().child("StreetRoads");
//
//             databaseReference.addValueEventListener(new ValueEventListener() {
//                 @Override
//                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                     Log.e("database_change", "try check 4");
//                     final List<String> precinct_array = new ArrayList<String>();
//
//
//                     for (DataSnapshot data:dataSnapshot.getChildren()) {
//                         try {
//                             Log.e("datasnap", "working");
//
//                             StreetRoads model = data.getValue(StreetRoads.class);
//
//                             if (model != null && model.getPrecinct_id().equals(prprty_type_id)) {
//                                 String name = model.getName();
//                                 precinct_array.add(name);
//                             }
//
//                         } catch (Exception e) {
//                             Log.e("exception", e.getLocalizedMessage());
//
//                         }
//                     }
//
//
//
//                     dialog= new Dialog(Add_Property.this);
//                     dialog.setContentView(R.layout.searchable_dialog);
//
//                     // dialog.getWindow().setLayout(650, 800);
//                     dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                     dialog.show();
//
//                     EditText editText = dialog.findViewById(R.id.edit_precinct);
//                     ListView listView = dialog.findViewById(R.id.listView);
//
//
//                     final ArrayAdapter<String> adapter = new ArrayAdapter<String> (Add_Property.this,
//                             R.layout.dropdown_item, precinct_array);
//
//                     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                     listView.setAdapter(adapter);
//
//                     editText.addTextChangedListener(new TextWatcher() {
//                         @Override
//                         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                         }
//
//                         @Override
//                         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                             adapter.getFilter().filter(charSequence);
//                         }
//
//                         @Override
//                         public void afterTextChanged(Editable editable) {
//
//                         }
//                     });
//
//                     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                         @Override
//                         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                             spinner_precinct.setText(adapter.getItem(i));
//                             dialog.dismiss();
//                         }
//                     });
//
//
//                 }
//
//                 @Override
//                 public void onCancelled(@NonNull DatabaseError error) {
//
//                 }
//             });
//
//         }
//     });



        //this is test comment
    }

    private void displayUserData(SearchItems user) {

        String name1 = user.getName();
        String id1 = user.getId();
        Log.e("displayUserData", name1 + "   " + id1);
        spinner_precinct.setText(name1);

    }

    private void uId() {

        company_id = findViewById(R.id.company_id);
        agent_id = findViewById(R.id.agent_id);
        agent_name = findViewById(R.id.agent_name);
        price_range = findViewById(R.id.price_range);
        property_type = findViewById(R.id.property_type);
        spinner_precinct = findViewById(R.id.precinct);
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

    }
}