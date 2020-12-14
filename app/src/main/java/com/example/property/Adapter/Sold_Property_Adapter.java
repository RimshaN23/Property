package com.example.property.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.property.R;
import com.example.property.models.Plots;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class Sold_Property_Adapter extends FirebaseRecyclerAdapter<Plots, Sold_Property_Adapter.SoldPropertyViewHolder> {

    Context context;
    ProgressDialog progressDialog;

      public Sold_Property_Adapter(@NonNull FirebaseRecyclerOptions<Plots> options, Context context, ProgressDialog progressDialog) {
          super(options);
          this.context = context;
          this.progressDialog = progressDialog;
      }

    @Override
    protected void onBindViewHolder(@NonNull Sold_Property_Adapter.SoldPropertyViewHolder holder, int position, @NonNull Plots model) {

          if (model.getIs_sold().equals("Yes")){
              final ArrayList<String> imageUrl = model.getImageUrl();
              if (imageUrl != null && imageUrl.size() > 0) {
                  Log.e("imagePosition", imageUrl.get(0));
                  Picasso.get().load(imageUrl.get(0)).into(holder.plotImage);
              } else {
                  Picasso.get().load(R.drawable.no_image).into(holder.plotImage);
              }

              final String key = getRef(position).getKey();

              String prprty_type_id = "";
              final String constructed = model.getConstructed();

              if (model.getProperty_type_id().equals("1")) {
                  prprty_type_id = "Residential";
              }
              if (model.getProperty_type_id().equals("2")) {
                  prprty_type_id = "Commercial";
              }

              holder.sold.setText(model.getIs_sold());
              holder.propertyType.setText(prprty_type_id);
              holder.plotname.setText("Location, " + model.getName());
              holder.square_yard.setText(model.getSq_yrds() + " Sq. Ft.");


              holder.pricerangeFrom.setText("PKR. " + model.getPlot_price_range_from());

          }
    } @Override
    public void onDataChanged() {
        progressDialog.dismiss();

    }


    @NonNull
    @Override
    public Sold_Property_Adapter.SoldPropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemview, parent, false);
        Log.e("working", "inflate working");

        return new SoldPropertyViewHolder(view);
    }

    class SoldPropertyViewHolder extends RecyclerView.ViewHolder {

        TextView propertyType, plotname, square_yard, pricerangeFrom, sold;
        ImageView plotImage;
        CardView cardView;

        public SoldPropertyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.plotcardview);
            plotImage = itemView.findViewById(R.id.plotImage);
            propertyType = itemView.findViewById(R.id.tv_property_type);
            plotname = itemView.findViewById(R.id.tv_plot_name);
            square_yard = itemView.findViewById(R.id.tv_square_yard);
            pricerangeFrom = itemView.findViewById(R.id.priceRange);
            sold = itemView.findViewById(R.id.tv_sold);


        }
    }
}
