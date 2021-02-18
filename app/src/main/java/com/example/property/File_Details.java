package com.example.property;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.property.Adapter.FileDetailAdapter;
import com.example.property.Adapter.UploadListAdapter;

import java.util.ArrayList;
import java.util.List;

public class File_Details extends AppCompatActivity {

    TextView textView;
    RecyclerView recyclerView;
    String name;
    FileDetailAdapter adapter;
    private ArrayList<String> fileUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file__details);
        name= getIntent().getExtras().getString("filecomp");
        textView= findViewById(R.id.filecomp);

        textView.setText(name);
        fileUrl = new ArrayList<>();
        fileUrl = getIntent().getExtras().getStringArrayList("fileUrl");
        Log.e("filesize", String.valueOf(fileUrl.size()));

        recyclerView= findViewById(R.id.filerv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new FileDetailAdapter(this,fileUrl);
        recyclerView.setAdapter(adapter);
        Log.e("file","work");



    }
}