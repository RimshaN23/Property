package com.example.property;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Add_Property extends AppCompatActivity {

    EditText company_id, agent_id, agent_name,price_range,property_type, precinct,
            plot_name, plot_no, square_yard, road, plot_address, is_constructed, stories, rooms;

    TextView tv_stories, tv_rooms;

//    String  s_company_id, s_agent_id, s_agent_name, s_price_range, s_property_type, s_precinct,
//            plot_name, plot_no, square_yard, road, plot_address, is_constructed, stories, rooms;

    String prprty_type, constructed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__property);

        uId();

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
                    }
                });
                builder.show();

            }
        });
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
                    }
                });
                builder.show();

            }
        });

     if (constructed.equals("Yes")){
         tv_stories.setVisibility(View.VISIBLE);
         stories.setVisibility(View.VISIBLE);

         tv_rooms.setVisibility(View.VISIBLE);
         rooms.setVisibility(View.VISIBLE);
     }




        //this is test comment
    }

    private void uId() {
        company_id.findViewById(R.id.company_id);
        agent_id.findViewById(R.id.agent_id);
        agent_name.findViewById(R.id.agent_name);
        price_range.findViewById(R.id.price_range);
        property_type.findViewById(R.id.property_type);
        precinct.findViewById(R.id.precinct);
        plot_name.findViewById(R.id.plot_name);
        plot_no.findViewById(R.id.plot_no);
        square_yard.findViewById(R.id.square_yard);
        road.findViewById(R.id.road);
        plot_address.findViewById(R.id.plot_address);
        is_constructed.findViewById(R.id.is_constructed);
        stories.findViewById(R.id.stories);
        rooms.findViewById(R.id.rooms);

        tv_rooms.findViewById(R.id.tv_rooms);
        tv_stories.findViewById(R.id.tv_stories);


    }
}