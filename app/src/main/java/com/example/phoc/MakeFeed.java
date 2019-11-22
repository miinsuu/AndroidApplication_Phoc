package com.example.phoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MakeFeed extends Fragment implements View.OnClickListener {

    Button fromGalleryBtn;
    Button takePhotoNowBtn;

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
            startActivity(new Intent(getActivity(), Gallery.class));
        } else if (v == takePhotoNowBtn) {

        }

    }
}
