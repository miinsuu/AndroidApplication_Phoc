package com.example.phoc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Home extends Fragment implements View.OnClickListener {

    Button fromHome2MakeFeedBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home, container, false);

        fromHome2MakeFeedBtn = rootView.findViewById(R.id.fromHome2MakeFeedBtn);
        fromHome2MakeFeedBtn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        ((main)getActivity()).onFragmentSelected(5,null);
    }
}
