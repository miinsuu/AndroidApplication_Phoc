package com.example.phoc.SubscribeUserListActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phoc.R;

import java.util.ArrayList;

interface OnSubscribeUserListItemClickListener {
    public void onItemClick(SubscribeUserListItemAdapter.ViewHolder holder, View view, int position);
}
public class SubscribeUserListItemAdapter extends RecyclerView.Adapter<SubscribeUserListItemAdapter.ViewHolder> implements OnSubscribeUserListItemClickListener{
    ArrayList<SubscribeUserListItem> items = new ArrayList<SubscribeUserListItem>();
    OnSubscribeUserListItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.subscribeuserlist_item, viewGroup, false);


        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        SubscribeUserListItem item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView userName;

        public ViewHolder(@NonNull View itemView, final OnSubscribeUserListItemClickListener listener) {
            super(itemView);

            userName = itemView.findViewById(R.id.particularSubscribeUser);

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

        public void setItem(SubscribeUserListItem item){
            userName.setText("# "+item.getUserName());
        }
    }
    public void setOnItemClickListenr(OnSubscribeUserListItemClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    public void addItem(SubscribeUserListItem item){
        items.add(item);
    }

    public void setItems(ArrayList<SubscribeUserListItem> items){
        this.items = items;
    }

    public SubscribeUserListItem getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, SubscribeUserListItem item){
        items.set(position, item);
    }
}
