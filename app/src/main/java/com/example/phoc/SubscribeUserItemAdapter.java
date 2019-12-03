package com.example.phoc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubscribeUserItemAdapter extends RecyclerView.Adapter<SubscribeUserItemAdapter.ViewHolder>{
    ArrayList<SubscribeUserItem> items = new ArrayList<SubscribeUserItem>();

    public interface OnItemClickListener{
        public  void onItemClick(View view, int position, int type);
    }

    private OnItemClickListener onItemClickListener;

    public SubscribeUserItemAdapter(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.subscribeuser_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        SubscribeUserItemAdapter.ViewHolder holder = (SubscribeUserItemAdapter.ViewHolder)viewHolder;
        holder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewType1은 TextView인 userName
                onItemClickListener.onItemClick(v, position, 1);
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewType2은 TextView인 title
                onItemClickListener.onItemClick(v, position, 2);
            }
        });

        SubscribeUserItem item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView comment;
        TextView userName;
        TextView date;

        public ViewHolder(View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.fromSubscribeUser2ParticularTitle);
            comment = itemView.findViewById(R.id.inSubscribeuserComment);
            userName = itemView.findViewById(R.id.fromSubscribeUser2UserFeed);
            date = itemView.findViewById(R.id.inSubscribeuserDate);
        }

        public void setItem(SubscribeUserItem item){
            title.setText(item.getTitle());
            comment.setText(item.getComment());
            userName.setText(item.getUserName());
            date.setText(item.getDate());
        }
    }
    public void addItem(SubscribeUserItem item){
        items.add(item);
    }

    public void setItems(ArrayList<SubscribeUserItem> items){
        this.items = items;
    }

    public SubscribeUserItem getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, SubscribeUserItem item){
        items.set(position, item);
    }
}
