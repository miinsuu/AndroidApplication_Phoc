package com.example.phoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class signIn extends AppCompatActivity implements View.OnClickListener{

    Button signInFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ActionBar actionBar = getSupportActionBar();  //제목줄 객체 얻어오기
        actionBar.setTitle("다시 돌아오신 것을 환영해요!");  //액션바 제목설정
        actionBar.setDisplayHomeAsUpEnabled(true);

        signInFinish = findViewById(R.id.signInFinish);
        signInFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, main.class));
    }
}
