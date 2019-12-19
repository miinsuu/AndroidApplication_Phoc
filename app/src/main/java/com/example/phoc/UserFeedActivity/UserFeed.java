package com.example.phoc.UserFeedActivity;

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


public class UserFeed extends Fragment{
    String userId;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            userId = getArguments().getString("userId");
            Log.d("userfeed",userId );
        }
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.userfeed, container, false);
        recyclerView = rootView.findViewById(R.id.userfeedRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        final UserFeedItemAdapter adapter = new UserFeedItemAdapter(new UserFeedItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UserFeedItem item, int viewType) {
                if(viewType == 1) { //viewType1은 TextView인 title
                    Bundle bundle = new Bundle();
                    bundle.putString("theme",item.title );
                    ((main) getActivity()).onFragmentSelected(6, bundle);
                }
                else if(viewType ==2) { //viewType2는 imageButton인 phocBtn
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
        },getActivity());

        DatabaseQueryClass.Post.getPostsByUserId(userId, new DataListener() {
            @Override
            public void getData(Object data, String id) {
                adapter.addItem(new UserFeedItem(data.toString(), id));
                recyclerView.setAdapter(adapter);
            }
        });

        return rootView;
    }
    void refreshFragement(){
        androidx.fragment.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
        //getPostsFromDB();

    }
}
