package com.example.phoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.phoc.DatabaseConnection.DataListener;
import com.example.phoc.DatabaseConnection.DatabaseQueryClass;
import com.google.firebase.storage.FirebaseStorage;

public class Upload extends AppCompatActivity implements View.OnClickListener{

    Button upload;
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://phoc-50746.appspot.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        upload = findViewById(R.id.uploadBtn);
        upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == upload){
           // DatabaseQueryClass.Post.createPost();
            /*
            final String cameraSettingJson,
            final String content,
            final String imgUrl,
            final String theme)

             */
            startActivity(new Intent(this, main.class));
        }
    }
}
