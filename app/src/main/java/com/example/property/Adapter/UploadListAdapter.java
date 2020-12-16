package com.example.property.Adapter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.property.R;
import com.example.property.models.Plots;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UploadListAdapter extends RecyclerView.Adapter<UploadListAdapter.ViewHolder>{

    public List<String> fileNameList;
    public List<String> fileDoneList;
    private List<Uri> fileUriList;

    public UploadListAdapter(List<String> fileNameList, List<String>fileDoneList, List<Uri> fileUriList){


        this.fileDoneList = fileDoneList;
        this.fileNameList = fileNameList;
        this.fileUriList= fileUriList;

    }


    @NonNull
    @Override
    public UploadListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull UploadListAdapter.ViewHolder holder, int position) {

//        Plots model= arrayList.get(position);
//        ArrayList<String> imageUrl= model.getImageUrl();
        Uri fileuri = fileUriList.get(position);
        holder.pic.setImageURI(fileuri);


        String fileName = fileNameList.get(position);
        holder.fileNameView.setText(fileName);

        String fileDone = fileDoneList.get(position);

        if(fileDone.equals("uploading")){

            holder.fileDoneView.setImageResource(R.mipmap.iconloading);

        } else {

            holder.fileDoneView.setImageResource(R.mipmap.checked);

        }
    }

    @Override
    public int getItemCount() {
        return fileNameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public TextView fileNameView;
        public ImageView fileDoneView;
        ImageView pic;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            fileNameView =  mView.findViewById(R.id.upload_filename);
            fileDoneView =  mView.findViewById(R.id.upload_loading);
            pic= mView.findViewById(R.id.upload_icon);


        }

    }



}
