package com.example.phoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Upload extends AppCompatActivity implements View.OnClickListener{

    Button upload;

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
            startActivity(new Intent(this, main.class));
        }
    }
}
