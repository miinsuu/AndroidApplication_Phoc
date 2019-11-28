package com.example.phoc;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;


public class Home extends Fragment implements View.OnClickListener {

    Button fromHome2MakeFeedBtn;
    TextView todayTheme;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home, container, false);

        fromHome2MakeFeedBtn = rootView.findViewById(R.id.fromHome2MakeFeedBtn);
        todayTheme = rootView.findViewById((R.id.todayTheme));

        fromHome2MakeFeedBtn.setOnClickListener(this);

        DatabaseQueryClass.Theme.getTodayTheme(new DataListener() {
            @Override
            public void getData(Object data) {
                String json = new Gson().toJson(data);
                JsonElement element = new JsonParser().parse(json);
                JsonObject jobj = element.getAsJsonObject();

                String name = element.getAsJsonObject().get("name").getAsString();
                todayTheme.setText(jobj.get("name").toString());
            }
        });


        return rootView;
    }

    @Override
    public void onClick(View v) {
        ((main)getActivity()).onFragmentSelected(5,null);
    }
}
