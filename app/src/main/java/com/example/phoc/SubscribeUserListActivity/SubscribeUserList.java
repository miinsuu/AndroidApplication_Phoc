package com.example.phoc.SubscribeUserListActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phoc.SubscribeUserListActivity.OnSubscribeUserListItemClickListener;
import com.example.phoc.R;
import com.example.phoc.SubscribeUserListActivity.SubscribeUserListItem;
import com.example.phoc.SubscribeUserListActivity.SubscribeUserListItemAdapter;
import com.example.phoc.main;


public class SubscribeUserList extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.subscribeuserlist, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.subscribeuserlistRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        final SubscribeUserListItemAdapter adapter = new SubscribeUserListItemAdapter();

        adapter.addItem(new SubscribeUserListItem("김용후"));
        adapter.addItem(new SubscribeUserListItem("갓용후"));
        adapter.addItem(new SubscribeUserListItem("킹용후"));
        adapter.addItem(new SubscribeUserListItem("쌉벌레용후"));
        adapter.addItem(new SubscribeUserListItem("드래곤후"));
        adapter.addItem(new SubscribeUserListItem("재갈용후"));
        adapter.addItem(new SubscribeUserListItem("곽용후"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListenr(new OnSubscribeUserListItemClickListener() {
            @Override
            public void onItemClick(SubscribeUserListItemAdapter.ViewHolder holder, View view, int position) {
                ((main) getActivity()).onFragmentSelected(7, null);
            }
        });
        return rootView;
    }
}
