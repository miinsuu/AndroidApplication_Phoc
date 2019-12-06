package com.example.phoc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SearchUser extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.searchuser, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.searchuserRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        final SearchUserItemAdapter adapter = new SearchUserItemAdapter();

        adapter.addItem(new SearchUserItem("김용후"));
        adapter.addItem(new SearchUserItem("재갈용후"));
        adapter.addItem(new SearchUserItem("안드용후"));
        adapter.addItem(new SearchUserItem("박용후"));
        adapter.addItem(new SearchUserItem("최용후"));
        adapter.addItem(new SearchUserItem("함용후"));
        adapter.addItem(new SearchUserItem("남궁용후"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnSearchUserItemClickListener() {
            @Override
            public void onItemClick(SearchUserItemAdapter.ViewHolder holder, View view, int position) {
                ((main) getActivity()).onFragmentSelected(7, null);
            }
        });
        return rootView;
    }

}
