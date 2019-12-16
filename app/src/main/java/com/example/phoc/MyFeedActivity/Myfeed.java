package com.example.phoc.MyFeedActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phoc.DatabaseConnection.DataListener;
import com.example.phoc.DatabaseConnection.DatabaseQueryClass;
import com.example.phoc.MySession.MySession;
import com.example.phoc.MyFeedActivity.MyFeedItem;
import com.example.phoc.MyFeedActivity.MyFeedItemAdapter;
import com.example.phoc.R;
import com.example.phoc.main;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class Myfeed extends Fragment{
    RecyclerView recyclerView;
    final String TAG = "myFeed";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.myfeed, container, false);
        recyclerView = rootView.findViewById(R.id.myFeedRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


        final MyFeedItemAdapter adapter = new MyFeedItemAdapter(new MyFeedItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MyFeedItem item, int viewType) {
                if(viewType == 1) { //viewType1은 TextView인 title
                    Log.d(TAG,item.title);

                    Bundle args = new Bundle();
                    args.putString("theme", item.title);

                    ((main) getActivity()).onFragmentSelected(6, args);
                }
            }
        }, getContext());


        DatabaseQueryClass.Post.getPostsByUserId(MySession.getSession().getUserId(), new DataListener() {
            @Override
            public void getData(Object json, String postId) {
                adapter.addItem(new MyFeedItem(postId, json.toString()));
                setAdapterToView(adapter);
            }
        });

        return rootView;
    }
    void setAdapterToView(final MyFeedItemAdapter adapter){
        recyclerView.setAdapter(adapter);

    }

}
