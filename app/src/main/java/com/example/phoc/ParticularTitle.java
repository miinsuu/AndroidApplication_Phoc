package com.example.phoc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ParticularTitle extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.particulartitle, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.particulartitleRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        final ParticularTitleAdapter adapter = new ParticularTitleAdapter(new ParticularTitleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int viewType) {
                if(viewType == 1) { //viewType은 TextView인 userName
                    ((main) getActivity()).onFragmentSelected(7, null);
                }
            }
        });

        adapter.addItem(new ParticularTitleItem("난 쉬운데?","김용후","2019-11-29"));
        adapter.addItem(new ParticularTitleItem("아 넘무 어렵다","함인규","2019-11-29"));
        adapter.addItem(new ParticularTitleItem("카메라 만들기 어렵다","김민수","2019-11-29"));

        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
