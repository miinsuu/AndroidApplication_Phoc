package com.example.phoc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

interface  OnSearchUserItemClickListener{
    public void onItemClick(SearchUserItemAdapter.ViewHolder holder, View view, int position);
}
public class SearchUserItemAdapter extends RecyclerView.Adapter<SearchUserItemAdapter.ViewHolder> implements OnSearchUserItemClickListener{
    ArrayList<SearchUserItem> items = new ArrayList<SearchUserItem>();
    OnSearchUserItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.searchuser_item, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        SearchUserItem item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView userName;

        public ViewHolder(@NonNull View itemView, final OnSearchUserItemClickListener listener) {
            super(itemView);

            userName = itemView.findViewById(R.id.UserName);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(SearchUserItem item){
            userName.setText(item.getUserName());
        }
    }


    public void  setOnItemClickListener(OnSearchUserItemClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    public void addItem(SearchUserItem item){ items.add(item); }

    public void setItems(ArrayList<SearchUserItem> items){
        this.items = items;
    }

    public SearchUserItem getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, SearchUserItem item){
        items.set(position, item);
    }
}
