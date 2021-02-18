package com.example.property;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PDFviewer extends AppCompatActivity {

    PDFView pdfView;
    Uri pdfuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        pdfView= findViewById(R.id.pdfView);

        pdfuri= Uri.parse(getIntent().getStringExtra("fileUrl"));
        Log.e("fileuri", String.valueOf(pdfuri));
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, pdfuri);
        startActivity(browserIntent);
    //    pdfView.fromUri((pdfuri)).load();
   }
}