package com.example.phoc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TitleList extends Fragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.titlelist, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.titlelistRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        TitleListItemAdapter adapter = new TitleListItemAdapter();

        adapter.addItem(new TitleListItem("가을"));
        adapter.addItem(new TitleListItem("기말고사"));
        adapter.addItem(new TitleListItem("안드로이드"));
        adapter.addItem(new TitleListItem("겨울"));
        adapter.addItem(new TitleListItem("사진"));
        adapter.addItem(new TitleListItem("고양이"));
        adapter.addItem(new TitleListItem("강아지"));

        recyclerView.setAdapter(adapter);
        return rootView;
    }

}
