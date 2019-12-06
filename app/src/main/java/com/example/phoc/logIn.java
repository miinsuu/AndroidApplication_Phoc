package com.example.phoc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class logIn extends AppCompatActivity implements View.OnClickListener {
    Button signUpBtn;
    Button signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        signUpBtn = findViewById(R.id.signUpBtn);
        signInBtn = findViewById(R.id.signInBtn);

        signUpBtn.setOnClickListener(this);
        signInBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == signUpBtn)
            startActivity(new Intent(this, signUp.class));
        else if(v == signInBtn)
            startActivity(new Intent(this, signIn.class));
    }
}
