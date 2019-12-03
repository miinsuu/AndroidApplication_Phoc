package com.example.phoc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SubscribeUser extends Fragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.subscribeuser, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.subscribeuserRecyclerView);

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
        });

        adapter.addItem(new SubscribeUserItem("가을","날씨 좋아서", "함인규","2019-11-29"));
        adapter.addItem(new SubscribeUserItem("안드로이드","아 넘무 어렵다","김용후","2019-11-29"));
        adapter.addItem(new SubscribeUserItem("카메라","카메라 만들기 어렵다","김민수","2019-11-29"));

        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
