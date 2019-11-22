package com.example.phoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Gallery extends AppCompatActivity implements View.OnClickListener{

    Button cancelBtn;
    Button choiceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        cancelBtn = findViewById(R.id.cancelBtn);
        choiceBtn = findViewById(R.id.choiceBtn);

        cancelBtn.setOnClickListener(this);
        choiceBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == cancelBtn){
            onBackPressed();
        } else if(v == choiceBtn){
            startActivity(new Intent(this, Upload.class));
        }
    }
}
