package com.example.phoc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class SearchUser extends Fragment implements View.OnClickListener{

    Button fromSearchUser2UserFeed;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.searchuser, container, false);

        fromSearchUser2UserFeed = rootView.findViewById(R.id.fromSearchUser2UserFeed);
        fromSearchUser2UserFeed.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(v==fromSearchUser2UserFeed){
            ((main)getActivity()).onFragmentSelected(7,null);
        }
    }
}
