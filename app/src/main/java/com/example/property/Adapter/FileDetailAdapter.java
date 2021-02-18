package com.example.property.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.property.PDFviewer;
import com.example.property.PropertyDetail;
import com.example.property.R;
import com.example.property.models.Plots;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;



public class FileDetailAdapter extends RecyclerView.Adapter<FileDetailAdapter.FileDetail>{
    Context context;
    public List<String> fileNameList;

    public FileDetailAdapter(Context context, List<String> fileNameList) {
        this.context = context;
        this.fileNameList = fileNameList;
    }

    @NonNull
    @Override
    public FileDetail onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemview_file_details, parent, false);
        return new FileDetail(v);

    }

    @Override
    public void onBindViewHolder(@NonNull FileDetail holder, int position) {
        final String fileName = fileNameList.get(position);
        holder.filename.setText(fileName);
        Log.e("filename",fileName);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PDFviewer.class);
                     intent.putExtra("fileUrl",fileName);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return fileNameList.size();
    }

    class FileDetail extends RecyclerView.ViewHolder {

        TextView filename;
        CardView cardView;
        Button button;
        public FileDetail(@NonNull View itemView) {
            super(itemView);

            cardView= itemView.findViewById(R.id.cardview);
            filename= itemView.findViewById(R.id.file_name);

            button= itemView.findViewById(R.id.btn);
        }
    }

}
