package com.example.phoc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Upload extends AppCompatActivity implements View.OnClickListener{

    Button upload;
    ImageView upload_image;
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //intent로 갤러리에서 선택한 사진URI 받기
        Intent intent = getIntent(); /*데이터 수신*/
        String imageUriString = intent.getStringExtra("imageUriString");
        selectedImageUri = Uri.parse(imageUriString);
        Log.e("전시화면에서 스트링Uri",imageUriString);
        Log.e("전시화면에서 Uri",selectedImageUri.toString());
        initLayout();
    }

    private void initLayout()
    {
        setContentView(R.layout.activity_upload);

        upload = findViewById(R.id.uploadBtn);
        upload.setOnClickListener(this);

        //URI로 화면에 사진 뿌리기
        upload_image = findViewById(R.id.upload_image);
        upload_image.setImageURI(selectedImageUri);

//        Bitmap bm = Images.Media.getBitmap(getContentResolver(), selectedImageUri);
//        upload_image.setImageBitmap(bm);
    }

    @Override
    public void onClick(View v) {
        if(v == upload){
            startActivity(new Intent(this, main.class));
        }
    }
}
