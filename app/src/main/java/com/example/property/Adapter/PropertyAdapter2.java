package com.example.property.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.property.PropertyDetail;
import com.example.property.R;
import com.example.property.models.Plots;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PropertyAdapter2 extends RecyclerView.Adapter<PropertyAdapter2.ViewHolder>{

    ArrayList<Plots> arrayList= new ArrayList<>();
    ArrayList<String> keyList= new ArrayList<>();
    Context context;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;

    public PropertyAdapter2(ArrayList<Plots> arrayList, ArrayList<String> keyList, Context context, ProgressDialog progressDialog) {
        this.arrayList = arrayList;
        this.keyList = keyList;
        this.context = context;
        this.progressDialog = progressDialog;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemview, parent, false);

        return new  ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Plots model= arrayList.get(position);
        final String key= keyList.get(position);
        final ArrayList<String> imageUrl = model.getImageUrl();
        final ArrayList<String> cnicUrl = model.getCnic_images();
        final ArrayList<String> fileUrl= model.getFileUrl();
        if (imageUrl != null && imageUrl.size() > 0) {
            Log.e("imagePosition", imageUrl.get(0));
            Picasso.get().load(imageUrl.get(0)).into(holder.plotImage);
        } else {
            Picasso.get().load(R.drawable.no_image).into(holder.plotImage);
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Plots");


  //      final String key =   databaseReference.child(model.getName()).getKey();

        final String sold = model.getIs_sold();

        String prprty_type_id = "";
        final String constructed = model.getConstructed();

        if (model.getProperty_type_id().equals("1")) {
            prprty_type_id = "Residential";
        }
        if (model.getProperty_type_id().equals("2")) {
            prprty_type_id = "Commercial";
        }

        holder.propertyType.setText(prprty_type_id);
        holder.plotname.setText("Location, " + model.getName());
        holder.square_yard.setText(model.getSq_yrds() + " Sq. Ft.");
        holder.pricerangeFrom.setText("PKR. " + model.getPlot_price_range_from());
        holder.date.setText("Added On: "+model.getDate());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PropertyDetail.class);
                intent.putExtra("key", key);
                intent.putExtra("sold", sold);
                intent.putExtra("imageUrl", imageUrl);
                intent.putExtra("cnicUrl", cnicUrl);
                intent.putExtra("fileUrl",fileUrl);
                context.startActivity(intent);
            }
        });
        Log.e("working2", "bind working");
        Log.e("cnic",String.valueOf(cnicUrl));
    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        TextView propertyType, plotname, square_yard, pricerangeFrom, sold,date;
        ImageView plotImage;
        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            cardView = itemView.findViewById(R.id.plotcardview);
            plotImage = itemView.findViewById(R.id.plotImage);
            propertyType = itemView.findViewById(R.id.tv_property_type);
            plotname = itemView.findViewById(R.id.tv_plot_name);
            square_yard = itemView.findViewById(R.id.tv_square_yard);
            pricerangeFrom = itemView.findViewById(R.id.priceRange);
            date= itemView.findViewById(R.id.addedDate);

        }

    }

}
