package com.example.phoc.SubscribeUserActivity;

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
import com.example.phoc.MySession.MySession;
import com.example.phoc.R;
import com.example.phoc.SearchUserActivity.SearchUserItemAdapter;
import com.example.phoc.main;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SubscribeUser extends Fragment{

    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.subscribeuser, container, false);
        recyclerView = rootView.findViewById(R.id.subscribeuserRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        final SubscribeUserItemAdapter adapter = new SubscribeUserItemAdapter(new SubscribeUserItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick( SubscribeUserItem item, int viewType) {
                Bundle bundle = new Bundle();

                if(viewType == 1) { //viewType1은 TextView인 userName
                    bundle.putString("nick", item.userName);
                    bundle.putString("userId",item.userId);
                    ((main) getActivity()).onFragmentSelected(7, bundle);
                }
                else if(viewType == 2) { //viewType2은 TextView인 title
                    bundle.putString("theme", item.title);

                    ((main) getActivity()).onFragmentSelected(6, bundle);
                }
                else if(viewType == 3) { //viewType3는 ImageButton인 phocBtn
                    //phoc버튼 눌렀을 때 실행할 내용
                    Toast.makeText(getActivity(), "phoc!", Toast.LENGTH_LONG).show();
                }
            }
        }, getContext());

        DatabaseQueryClass.Post.getPostBySubscribing(MySession.getSession().getUserId(), new DataListener() {
            @Override
            public void getData(Object data, String id) {

                adapter.addItem(new SubscribeUserItem(data.toString(), id));
                setAdapterToView(adapter);
            }
        });

        recyclerView.setAdapter(adapter);

        return rootView;
    }
    void setAdapterToView(final SubscribeUserItemAdapter adapter){
        recyclerView.setAdapter(adapter);
    }
}
