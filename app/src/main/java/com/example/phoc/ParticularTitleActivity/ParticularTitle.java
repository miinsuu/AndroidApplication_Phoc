package com.example.phoc.ParticularTitleActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phoc.DatabaseConnection.DataListener;
import com.example.phoc.DatabaseConnection.DatabaseQueryClass;
import com.example.phoc.DatabaseConnection.MyOnSuccessListener;
import com.example.phoc.R;
import com.example.phoc.main;

public class ParticularTitle extends Fragment{
    private String theme;
    String TAG = "ParticularTitle";
    RecyclerView recyclerView;
    ParticularTitleAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            theme = getArguments().getString("theme");
        }
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.particulartitle, container, false);
        recyclerView = rootView.findViewById(R.id.particulartitleRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ParticularTitleAdapter(new ParticularTitleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ParticularTitleItem item, int viewType) {
                if(viewType == 1) { //viewType은 TextView인 userName
                    Bundle bundle = new Bundle();
                    bundle.putString("userId", item.userId);
                    bundle.putString("nick", item.userName);

                    ((main) getActivity()).onFragmentSelected(7, bundle);
                }

                else if(viewType == 2){ //viewType2는 imageButton인 phocBtn
                    Log.d("phocPost", "phoc Btn clicked");

                    if(item.isPhoccedFlag){
                        DatabaseQueryClass.Post.unPhocPost(item.postId, new MyOnSuccessListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getActivity(), "unPhoc!", Toast.LENGTH_LONG).show();
                                refreshFragement();

                            }
                        });
                    } else {
                        DatabaseQueryClass.Post.phocPost(item.postId, new MyOnSuccessListener() {
                            @Override
                            public void onSuccess() {
                                Log.d("phocPost", "added");
                                Toast.makeText(getActivity(), "phoc!", Toast.LENGTH_LONG).show();
                                refreshFragement();
                            }
                        });
                    }
                }
            }
        },getContext());

        getPostsFromDB();

        return rootView;
    }
    void getPostsFromDB(){
        adapter.items.clear();
        DatabaseQueryClass.Post.getPostsByTheme(theme, new DataListener() {
            @Override
            public void getData(Object data, String id) {
                adapter.addItem(new ParticularTitleItem(data.toString(), id));
                recyclerView.setAdapter(adapter);
            }
        });
    }
    void refreshFragement(){
        androidx.fragment.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
        //getPostsFromDB();

    }
}
