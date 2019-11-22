package com.example.phoc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ParticularTitle extends Fragment implements View.OnClickListener{

    TextView fromParticularTitle2UserFeed;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.particulartitle, container, false);

        fromParticularTitle2UserFeed = rootView.findViewById(R.id.fromParticularTitle2UserFeed);
        fromParticularTitle2UserFeed.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(v == fromParticularTitle2UserFeed){
            ((main)getActivity()).onFragmentSelected(7,null);
        }
    }
}
