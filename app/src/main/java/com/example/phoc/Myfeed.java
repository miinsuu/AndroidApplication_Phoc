package com.example.phoc;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phoc.DatabaseConnection.DataListListener;
import com.example.phoc.DatabaseConnection.DataListener;
import com.example.phoc.DatabaseConnection.DatabaseQueryClass;
import com.example.phoc.listView.FeedItem;
import com.example.phoc.listView.FeedItemAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;


public class Myfeed extends Fragment{
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.myfeed, container, false);

        recyclerView = rootView.findViewById(R.id.myFeedRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        final FeedItemAdapter adapter = new FeedItemAdapter(getContext());

        DatabaseQueryClass.Post.getPostsByUserId(MySession.getSession().getUserId(), new DataListener() {
            @Override
            public void getData(Object json) {

                JsonElement ele = new JsonParser().parse(json.toString());
                JsonObject obj = ele.getAsJsonObject();
                Log.d("Post", obj.toString());

                String title = obj.get("theme").getAsString();
                String comment = obj.get("content").getAsString();
                String date= obj.get("createdAt").getAsString();
                String imgUri = obj.get("img").getAsString();

                adapter.addItem(new FeedItem(title, comment, date, imgUri));
                setAdapterToView(adapter);
            }
        });

        //adapter.addItem(new FeedItem("가을","가을 날씨가 좋더라고요","2019-11-29", imgUrl));
        //adapter.addItem(new FeedItem("완성","드디어되네 씌바거","2019-11-29", imgUrl));
        //adapter.addItem(new FeedItem("최지웅","짱","2019-11-29", imgUrl));

        //Log.d("Post", "먼져해버렷당게");

        return rootView;
    }
    void setAdapterToView(final FeedItemAdapter adapter){
        recyclerView.setAdapter(adapter);

    }

}
