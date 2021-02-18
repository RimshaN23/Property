package com.example.property.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.property.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {


    public List<String> fileNameList;
    public List<String> fileDoneList;

    public FileAdapter(List<String> fileNameList, List<String> fileDoneList) {
        this.fileNameList = fileNameList;
        this.fileDoneList = fileDoneList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_itemview, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String fileName = fileNameList.get(position);
        holder.fileName.setText(fileName);

        String fileDone = fileDoneList.get(position);

        if(fileDone.equals("uploading")){

            holder.fileDone.setImageResource(R.mipmap.iconloading);

        } else {

            holder.fileDone.setImageResource(R.mipmap.checked);

        }
    }

    @Override
    public int getItemCount() {
        return fileNameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public TextView fileName;
        public ImageView fileDone;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            fileName =  mView.findViewById(R.id.upload_filename);
            fileDone =  mView.findViewById(R.id.upload_loading);


        }

    }

}
