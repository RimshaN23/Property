package com.example.property;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.property.Adapter.UploadListAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class Sold_Property extends AppCompatActivity {

    EditText client_name, client_number, client_cnic, sold_price;
    String clientName, clientNumber, clientCnic, soldPrice;
    Button submit_registry, goBack;
    String key;
    DatabaseReference databaseReference;

    RelativeLayout mainLayout;
    LinearLayout updateSuccessfulLayout;

    private  Button mSelectBtn;
    private RecyclerView mUploadList;

    Uri fileUri;
    String fileName, result;
    private static final int RESULT_LOAD_IMAGE = 1;
    private StorageReference mStorage;


    String imageUri;

    ArrayList<String> imagesUrl = new ArrayList<>();



    private List<String> fileNameList;
    private List<String> fileDoneList;
    private List<Uri> fileUriList;

    private UploadListAdapter uploadListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sold__property_activity);
        mStorage = FirebaseStorage.getInstance().getReference();

        client_name = findViewById(R.id.client_name);
        client_number = findViewById(R.id.client_no);
        client_cnic = findViewById(R.id.cnic_no);
        sold_price = findViewById(R.id.final_price);
        submit_registry = findViewById(R.id.submit_registry);
        goBack = findViewById(R.id.goBack);

        mSelectBtn = findViewById(R.id.imgBtn);
         mUploadList= findViewById(R.id.imageRecyclerview);

        mainLayout = findViewById(R.id.main_layout);
        updateSuccessfulLayout = findViewById(R.id.updateSuccessful);

        key = getIntent().getExtras().getString("key");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Plots").child(key);

        //image upload work

        fileNameList = new ArrayList<>();
        fileDoneList = new ArrayList<>();
        fileUriList = new ArrayList<>();


        uploadListAdapter = new UploadListAdapter(fileNameList, fileDoneList,fileUriList);

        //   RecyclerView

        mUploadList.setLayoutManager(new LinearLayoutManager(this));
        mUploadList.setHasFixedSize(true);
        mUploadList.setAdapter(uploadListAdapter);

        mSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mUploadList.setVisibility(View.VISIBLE);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), RESULT_LOAD_IMAGE);

            }
        });




        submit_registry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientName = client_name.getText().toString();
                clientNumber = client_number.getText().toString();
                clientCnic = client_cnic.getText().toString();
                soldPrice = sold_price.getText().toString();

                databaseReference.child("client_name").setValue(clientName);
                databaseReference.child("client_number").setValue(clientNumber);
                databaseReference.child("client_cnic").setValue(clientCnic);
                databaseReference.child("sold_price").setValue(soldPrice);
                databaseReference.child("is_sold").setValue("yes");

                databaseReference.child("cnic_images").setValue(imagesUrl);
                updateSuccessfulLayout.setVisibility(View.VISIBLE);
                mainLayout.setVisibility(View.GONE);
            }
        });


        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sold_Property.this, PropertyDetail.class);
                intent.putExtra("key", key);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Sold_Property.this, PropertyDetail.class);
        intent.putExtra("key", key);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            if(data.getClipData() != null) {
                int totalItemsSelected = data.getClipData().getItemCount();
                for(int i = 0; i < totalItemsSelected; i++){

                    fileUri = data.getClipData().getItemAt(i).getUri();
                    fileName = getFileName(fileUri);
                    Log.e("imageName","fileName "+fileName+"\nfileUri "+fileUri);
                    fileNameList.add(fileName);
                    fileDoneList.add("uploading");
                    fileUriList.add(fileUri);

                    uploadListAdapter.notifyDataSetChanged();
                    final StorageReference fileToUpload = mStorage.child("Images").child(fileName);

                    final int finalI = i;
                    fileToUpload.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileDoneList.remove(finalI);
                            fileDoneList.add(finalI, "done");
                            uploadListAdapter.notifyDataSetChanged();

                            fileToUpload.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    imageUri= String.valueOf(uri);
                                    imagesUrl.add(imageUri);
                                }
                            });

                        }
                    });

                }

            }  else if (data.getData() != null){

                Toast.makeText(Sold_Property.this, "Select more than one image", Toast.LENGTH_SHORT).show();

            }

        }

    }
    private String getFileName(Uri uri) {
        result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


}