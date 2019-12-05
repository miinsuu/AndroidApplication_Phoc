package com.example.phoc;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phoc.DatabaseConnection.DataListener;
import com.example.phoc.DatabaseConnection.DatabaseQueryClass;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TitleList extends Fragment implements View.OnClickListener{

    RelativeLayout fromTitleList2ParticularTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.titlelist, container, false);

        fromTitleList2ParticularTitle = rootView.findViewById(R.id.fromTitleList2ParticularTitle);
        fromTitleList2ParticularTitle.setOnClickListener(this);
        DatabaseQueryClass.Theme.getThemes(new DataListener() {
            @Override
            public void getData(Object data) {
                JsonElement ele = new JsonParser().parse(data.toString());
                JsonObject obj = ele.getAsJsonObject();
                Log.d("Theme", obj.toString());

                //String title = obj.get("theme").getAsString();
                //String comment = obj.get("content").getAsString();
                //String date= obj.get("createdAt").getAsString();
                //String imgUri = obj.get("img").getAsString();

            }
        });
        return rootView;

    }

    @Override
    public void onClick(View v) {
        if(v == fromTitleList2ParticularTitle){
            ((main)getActivity()).onFragmentSelected(6,null);
        }
    }
}
