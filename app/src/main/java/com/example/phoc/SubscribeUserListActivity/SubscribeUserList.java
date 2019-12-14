package com.example.phoc.SubscribeUserListActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phoc.DatabaseConnection.DataListener;
import com.example.phoc.DatabaseConnection.DatabaseQueryClass;
import com.example.phoc.SubscribeUserListActivity.OnSubscribeUserListItemClickListener;
import com.example.phoc.R;
import com.example.phoc.SubscribeUserListActivity.SubscribeUserListItem;
import com.example.phoc.SubscribeUserListActivity.SubscribeUserListItemAdapter;
import com.example.phoc.main;


public class SubscribeUserList extends Fragment {
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.subscribeuserlist, container, false);
        recyclerView = rootView.findViewById(R.id.subscribeuserlistRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        final SubscribeUserListItemAdapter adapter = new SubscribeUserListItemAdapter();

        DatabaseQueryClass.User.getSusbscribings(new DataListener() {
            @Override
            public void getData(Object data, String id) {
                Log.d("subscribe", data.toString() );
                adapter.addItem(new SubscribeUserListItem(data.toString(),id));
                recyclerView.setAdapter(adapter);
            }
        });


        adapter.setOnItemClickListenr(new OnSubscribeUserListItemClickListener() {
            @Override
            public void onItemClick(SubscribeUserListItemAdapter.ViewHolder holder, View view, int position) {
                ((main) getActivity()).onFragmentSelected(7, null);
            }
        });
        return rootView;
    }
}
