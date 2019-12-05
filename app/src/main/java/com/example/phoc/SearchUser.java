package com.example.phoc;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phoc.DatabaseConnection.DataListener;
import com.example.phoc.DatabaseConnection.DatabaseQueryClass;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class SearchUser extends Fragment implements View.OnClickListener{

        Button fromSearchUser2UserFeed;
        ImageView searchBtn;
        EditText inputText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.searchuser, container, false);

        fromSearchUser2UserFeed = rootView.findViewById(R.id.fromSearchUser2UserFeed);
        fromSearchUser2UserFeed.setOnClickListener(this);
        searchBtn = rootView.findViewById(R.id.searchBtn);
        inputText = rootView.findViewById(R.id.editText);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputText.getText().toString().length() < 0)
                    return ;
                else {
                    DatabaseQueryClass.User.findSimilarUserByNickname(inputText.getText().toString(), new DataListener() {
                        @Override
                        public void getData(Object data) {
                            JsonElement ele = new JsonParser().parse(data.toString());
                            JsonObject obj = ele.getAsJsonObject();
                            Log.d("Similar", obj.toString());


                            //list 추가
                        }
                    });
                }
            }
        });

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(v==fromSearchUser2UserFeed){


            ((main)getActivity()).onFragmentSelected(7,null);
        }
    }
}
