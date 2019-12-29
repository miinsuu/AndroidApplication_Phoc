package com.example.phoc.MyFeedActivity;

import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phoc.DatabaseConnection.DataListener;
import com.example.phoc.DatabaseConnection.DatabaseQueryClass;
import com.example.phoc.DatabaseConnection.MyOnSuccessListener;
import com.example.phoc.MainActivity;
import com.example.phoc.MySession.MySession;
import com.example.phoc.MyFeedActivity.MyFeedItem;
import com.example.phoc.MyFeedActivity.MyFeedItemAdapter;
import com.example.phoc.R;
import com.example.phoc.main;



public class Myfeed extends Fragment{
    RecyclerView recyclerView;
    final String TAG = "myFeed";
    MyFeedItemAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.myfeed, container, false);
        recyclerView = rootView.findViewById(R.id.myFeedRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


         adapter = new MyFeedItemAdapter(new MyFeedItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MyFeedItem item, int viewType) {
                if(viewType == 1) { //viewType1은 TextView인 title
                    Log.d(TAG,item.title);

                    Bundle args = new Bundle();
                    args.putString("theme", item.title);

                    ((main) getActivity()).onFragmentSelected(6, args);
                }
                else if( viewType == 2){
                    DatabaseQueryClass.Post.deletePost(item.postId, new MyOnSuccessListener() {
                        @Override
                        public void onSuccess() {
                            Toast toast = Toast.makeText(getContext(), "게시글 삭제!", Toast.LENGTH_SHORT);
                            toast.show();
                            refreshFragement();
                        }
                    });
                }
            }
        }, getContext());

        getPostsFromDB();

        return rootView;
    }
    void setAdapterToView(){
        recyclerView.setAdapter(adapter);

    }
    void getPostsFromDB(){
        DatabaseQueryClass.Post.getPostsByUserId(MySession.getSession().getUserId(), new DataListener() {
            @Override
            public void getData(Object json, String postId) {
                adapter.addItem(new MyFeedItem(postId, json.toString()));
                setAdapterToView();
            }
        });
    }
    void refreshFragement(){
        androidx.fragment.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }
}
