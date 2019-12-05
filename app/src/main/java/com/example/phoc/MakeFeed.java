package com.example.phoc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class MakeFeed extends Fragment implements View.OnClickListener {

    Button fromGalleryBtn;
    Button takePhotoNowBtn;
    private final int GET_GALLERY_IMAGE = 200;
    Uri selectedImageUri;
    private final int REQUEST_CODE = 100;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.makefeed, container, false);

        fromGalleryBtn = rootView.findViewById(R.id.fromGalleryBtn);
        takePhotoNowBtn = rootView.findViewById(R.id.takePhotoNowBtn);

        fromGalleryBtn.setOnClickListener(this);
        takePhotoNowBtn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v == fromGalleryBtn) {
            //갤러리앱으로 이동
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, GET_GALLERY_IMAGE);
        } else if (v == takePhotoNowBtn) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData(); //갤러리에서 선택한 사진 URI 획득
            //선택사진URI 가지고 전시화면으로 이동
            Intent intent = new Intent(getActivity(), Upload.class);
            String UriToString = selectedImageUri.toString();
            intent.putExtra("imageUriString", UriToString); /*송신*/
            startActivity(intent);
        }

    }
}
