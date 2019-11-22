package com.example.phoc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TitleList extends Fragment implements View.OnClickListener{

    RelativeLayout fromTitleList2ParticularTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.titlelist, container, false);

        fromTitleList2ParticularTitle = rootView.findViewById(R.id.fromTitleList2ParticularTitle);
        fromTitleList2ParticularTitle.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(v == fromTitleList2ParticularTitle){
            ((main)getActivity()).onFragmentSelected(6,null);
        }
    }
}
