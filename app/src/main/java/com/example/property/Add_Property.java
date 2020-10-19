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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.property.models.AgentsModel;
import com.example.property.models.Plots;
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

    EditText company_id, agent_id, agent_name, price_range, priceFrom, property_type,
             is_constructed;

    TextView spinner_precinct, road,plot_address, tv_stories, tv_rooms;
    EditText plot_no,square_yard,  stories, rooms,plot_name;
    Dialog dialog;

    ProgressDialog progressDialog;

    String id1;
    String name1;
    String roadname,roadid;
    String prprty_type, prprty_type_id, constructed, precinct_id;
    DatabaseReference databaseReference;

    ArrayList<String> precinct_array = new ArrayList<>();

    String plotName,plotId, plotRoom, plotAddress, plotSq_yrd;
    Button enter;

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
                                precinctData(user);
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


     road.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

             progressDialog.show();
             databaseReference = FirebaseDatabase.getInstance().getReference().child("StreetRoads");

             databaseReference.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                     Log.e("database_change", "try check 4");
                     List<SearchItems> searchItemsList = new ArrayList<>();

                     for (DataSnapshot data:dataSnapshot.getChildren()) {
                         try {
                             Log.e("datasnap", "working");

                             StreetRoads model = data.getValue(StreetRoads.class);
                             if (model != null && model.getPrecinct_id().equals(id1)) {

                                 String name = model.getName();

                                 String id = model.getRoad_id();

                                 Log.e("dataSnap", name + "  " + id);

                                 SearchItems searchItems = new SearchItems(name, id);
                                 searchItemsList.add(searchItems);
                                 Log.e("dataSnap2", searchItems.getName());

                             }
                         } catch (Exception e) {
                             Log.e("exception", e.getLocalizedMessage());

                         }
                     }



                     dialog= new Dialog(Add_Property.this);
                     dialog.setContentView(R.layout.searchable_dialog);

                     // dialog.getWindow().setLayout(650, 800);
                     dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                     progressDialog.dismiss();
                     dialog.show();

                     EditText editText = dialog.findViewById(R.id.edit_precinct);
                     ListView listView = dialog.findViewById(R.id.listView);


                     final ArrayAdapter<SearchItems> adapter = new ArrayAdapter<SearchItems> (Add_Property.this,
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
                             roadData(user);
                             dialog.dismiss();
                         }
                     });


//                     plot_name.setOnClickListener(new View.OnClickListener() {
//                         @Override
//                         public void onClick(View view) {
//
//                             progressDialog.show();
//                             databaseReference = FirebaseDatabase.getInstance().getReference().child("Plots");
//
//                             databaseReference.addValueEventListener(new ValueEventListener() {
//                                 @Override
//                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                     Log.e("database_change", "try check 4");
//
//                                     List<Plots> plotList = new ArrayList<>();
//
//                                     for (DataSnapshot data:dataSnapshot.getChildren()) {
//                                         try {
//                                             Log.e("datasnap91", roadid);
//
//                                             Plots model = data.getValue(Plots.class);
//                                             Log.e("datasnap99", model.getRoad_id()+"1");
//
//                                             if (model != null && model.getRoad_id().equals(roadid)) {
//
//                                                 String name = model.getName();
//                                                 String id = model.getPlot_id();
//                                                 String sq_yrd=model.getSq_yrds();
//                                                 String room= model.getRooms();
//                                                 String adress = model.getAddress();
//
//
//                                                 Plots plotItems = new Plots(name, id,sq_yrd,room,adress);
//                                                 plotList.add(plotItems);
//                                                 Log.e("dataSnap21", plotItems.getPlot_id());
//
//                                             }
//                                         } catch (Exception e) {
//                                             Log.e("exception", e.getLocalizedMessage());
//
//                                         }
//                                     }
//
//
//
//                                     dialog= new Dialog(Add_Property.this);
//                                     dialog.setContentView(R.layout.searchable_dialog);
//
//                                     // dialog.getWindow().setLayout(650, 800);
//                                     dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                                     progressDialog.dismiss();
//                                     dialog.show();
//
//                                     EditText editText = dialog.findViewById(R.id.edit_precinct);
//                                     ListView listView = dialog.findViewById(R.id.listView);
//
//
//                                     final ArrayAdapter<Plots> adapter = new ArrayAdapter<Plots> (Add_Property.this,
//                                             R.layout.dropdown_item, plotList);
//
//                                     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                     listView.setAdapter(adapter);
//
//                                     editText.addTextChangedListener(new TextWatcher() {
//                                         @Override
//                                         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                                         }
//
//                                         @Override
//                                         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                                             adapter.getFilter().filter(charSequence);
//                                         }
//
//                                         @Override
//                                         public void afterTextChanged(Editable editable) {
//
//                                         }
//                                     });
//
//                                     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                         @Override
//                                         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                                             Plots user = (Plots) adapterView.getItemAtPosition(i);
//                                             plotData(user);
//                                             dialog.dismiss();
//                                         }
//                                     });
//
//
//
//                                 }
//
//                                 @Override
//                                 public void onCancelled(@NonNull DatabaseError error) {
//
//                                 }
//                             });
//
//                         }
//                     });
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

                 }
             });

         }
     });


     enter.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             String AgentName= agent_name.getText().toString();
             String AgentId = agent_id.getText().toString();
             plotName= plot_name.getText().toString();
             plotId= plot_no.getText().toString();
             plotRoom= rooms.getText().toString();

         }
     });

    }

    private void precinctData(SearchItems user) {

        name1 = user.getName();
        id1 = user.getId();
        Log.e("displayUserData", name1 + "   " + id1);
        spinner_precinct.setText(name1);

    }
    private void roadData(SearchItems user) {

        roadname = user.getName();
        roadid = user.getId();
        road.setText(roadname);

    }

    private void plotData(Plots user) {

        plotName = user.getName();
        plotId = user.getPlot_id();
        plotRoom= user.getRooms();
        plotSq_yrd= user.getSq_yrds();
        plotAddress= user.getAddress();


        plot_name.setText(plotName);
        plot_no.setText(plotId);
        rooms.setText(plotRoom);
        plot_address.setText(plotAddress);
        square_yard.setText(plotSq_yrd);

    }
//    private void plotIdData(SearchItems user) {
//
//        plotId = user.getId();
//
//    }


    private void uId() {

        company_id = findViewById(R.id.company_id);
        agent_id = findViewById(R.id.agent_id);
        agent_name = findViewById(R.id.agent_name);
        price_range = findViewById(R.id.price_range_to);
        priceFrom = findViewById(R.id.price_range_from);
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

        enter= findViewById(R.id.enter_registry);

    }
}