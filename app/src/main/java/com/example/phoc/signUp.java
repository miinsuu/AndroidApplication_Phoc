package com.example.phoc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signUp extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;// ...
    private static final String TAG = "SignUp";
    private FirebaseFirestore db;
    private boolean isSingupSuccessed = false ;
    Button signUpFinish;
    EditText emailText;
    EditText passwordText;
    EditText nickText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();  //제목줄 객체 얻어오기
        actionBar.setTitle("새로 오신것을 환영해요!");  //액션바 제목설정
        actionBar.setDisplayHomeAsUpEnabled(true);

        signUpFinish = findViewById(R.id.signUpFinish);
        emailText = findViewById(R.id.emailEditText);
        passwordText = findViewById(R.id.passwordEditText);
        nickText = findViewById(R.id.nickEditText);

        signUpFinish.setOnClickListener(this);
    }
    private void createAccount(final String email, String password, final String nickname){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(signUp.this, "Account created.",
                                    Toast.LENGTH_SHORT).show();
                            isSingupSuccessed = true;
                            putUserDataToDB(email, nickname);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(signUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            isSingupSuccessed = false;
                        }
                    }
                });
    }
    private void putUserDataToDB(String email, String nickname){
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("nick", nickname);

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }
    @Override
    public void onClick(View v) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String nickname = nickText.getText().toString();
        createAccount(email, password, nickname);
        if(isSingupSuccessed)
            startActivity(new Intent(this, signIn.class));
    }
}
