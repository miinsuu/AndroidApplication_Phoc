package com.example.phoc.SubscribeUserActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.phoc.DatabaseConnection.DataListener;
import com.example.phoc.DatabaseConnection.DatabaseQueryClass;
import com.example.phoc.MySession.MySession;
import com.example.phoc.R;
import com.example.phoc.SearchUserActivity.SearchUserItemAdapter;
import com.example.phoc.main;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SubscribeUser extends Fragment{

    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.subscribeuser, container, false);
        recyclerView = rootView.findViewById(R.id.subscribeuserRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        final SubscribeUserItemAdapter adapter = new SubscribeUserItemAdapter(new SubscribeUserItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int viewType) {
                if(viewType == 1) { //viewType1은 TextView인 userName
                    ((main) getActivity()).onFragmentSelected(7, null);
                }
                else if(viewType == 2) { //viewType2은 TextView인 title
                    ((main) getActivity()).onFragmentSelected(6, null);
                }
            }
        }, getContext());

        DatabaseQueryClass.Post.getPostBySubscribing(MySession.getSession().getUserId(), new DataListener() {
            @Override
            public void getData(Object data, String id) {

                JsonElement ele = new JsonParser().parse(data.toString());
                JsonObject obj = ele.getAsJsonObject();
                Log.d("subsc", obj.toString());

                String title = obj.get("theme").getAsString();
                String comment = obj.get("content").getAsString();
                String date= obj.get("createdAt").getAsString();
                String imgUri = obj.get("img").getAsString();
                String nick = obj.get("nick").getAsString();

                adapter.addItem(new SubscribeUserItem(title,comment, nick ,date, imgUri));
                setAdapterToView(adapter);
            }
        });

        recyclerView.setAdapter(adapter);

        return rootView;
    }
    void setAdapterToView(final SubscribeUserItemAdapter adapter){
        recyclerView.setAdapter(adapter);
    }
}
