package com.example.phoc;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phoc.DatabaseConnection.DataListener;
import com.example.phoc.DatabaseConnection.DatabaseQueryClass;
import com.example.phoc.MySession.MySession;

public class SubscribeUser extends Fragment implements View.OnClickListener{

    TextView fromSubscribeUser2ParticularTitle;
    TextView fromSubscribeUser2UserFeed;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.subscribeuser, container, false);

        fromSubscribeUser2ParticularTitle = rootView.findViewById(R.id.fromSubscribeUser2ParticularTitle);
        fromSubscribeUser2ParticularTitle.setOnClickListener(this);

        fromSubscribeUser2UserFeed = rootView.findViewById(R.id.fromSubscribeUser2UserFeed);
        fromSubscribeUser2UserFeed.setOnClickListener(this);
        DatabaseQueryClass.Post.getPostBySubscribing(MySession.getSession().getUserId(), new DataListener() {
            @Override
            public void getData(Object data) {
                Log.d("subsc", data.toString());
            }
        });
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(v == fromSubscribeUser2ParticularTitle){
            ((main)getActivity()).onFragmentSelected(6,null);
        } else if(v == fromSubscribeUser2UserFeed){
            ((main)getActivity()).onFragmentSelected(7,null);
        }
    }
}
