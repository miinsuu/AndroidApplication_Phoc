package com.example.phoc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phoc.DatabaseConnection.DataListener;
import com.example.phoc.DatabaseConnection.DatabaseQueryClass;
import com.example.phoc.listView.FeedItem;
import com.example.phoc.listView.FeedItemAdapter;


public class Myfeed extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.myfeed, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.myFeedRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        FeedItemAdapter adapter = new FeedItemAdapter(getContext());
        String imgUrl = "https://firebasestorage.googleapis.com/v0/b/phoc-50746.appspot.com/o/images%2Fgit-flow_overall_graph.png?alt=media&token=385b0b45-846c-4f9a-a03f-837f4249e37e";

        DatabaseQueryClass.Post.getPostsByUserId(MySession.getSession().getUserId(), new DataListener() {
            @Override
            public void getData(Object data) {

            }
        });
        adapter.addItem(new FeedItem("가을","가을 날씨가 좋더라고요","2019-11-29", imgUrl));
        adapter.addItem(new FeedItem("완성","드디어되네 씌바거","2019-11-29", imgUrl));
        adapter.addItem(new FeedItem("최지웅","짱","2019-11-29", imgUrl));

        recyclerView.setAdapter(adapter);

        return rootView;
    }

}
