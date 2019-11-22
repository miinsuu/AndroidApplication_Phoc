package com.example.phoc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SubscribeUser extends Fragment implements View.OnClickListener{

    TextView fromSubscribeUser2ParticularTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.subscribeuser, container, false);

        fromSubscribeUser2ParticularTitle = rootView.findViewById(R.id.fromSubscribeUser2ParticularTitle);
        fromSubscribeUser2ParticularTitle.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(v == fromSubscribeUser2ParticularTitle){
            ((main)getActivity()).onFragmentSelected(6,null);
        }
    }
}
