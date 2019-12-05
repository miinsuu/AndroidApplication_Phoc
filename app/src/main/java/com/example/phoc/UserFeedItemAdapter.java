package com.example.phoc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserFeedItemAdapter extends RecyclerView.Adapter<UserFeedItemAdapter.ViewHolder>{
    ArrayList<UserFeedItem> items = new ArrayList<UserFeedItem>();

    public interface OnItemClickListener{
        public  void onItemClick(View view, int position, int type);
    }

    private OnItemClickListener onItemClickListener;

    public UserFeedItemAdapter(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.userfeed_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        UserFeedItemAdapter.ViewHolder holder = (UserFeedItemAdapter.ViewHolder)viewHolder;
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewType1은 TextView인 title
                onItemClickListener.onItemClick(v, position, 1);
            }
        });

        UserFeedItem item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView comment;
        TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.fromUserFeed2ParticularTitle);
            comment = itemView.findViewById(R.id.inUserFeedComment);
            date = itemView.findViewById(R.id.inUserFeedDate);
        }

        public void setItem(UserFeedItem item){
            title.setText(item.getTitle());
            comment.setText(item.getComment());
            date.setText(item.getDate());
        }
    }

    public void addItem(UserFeedItem item){
        items.add(item);
    }

    public void setItems(ArrayList<UserFeedItem> items){
        this.items = items;
    }

    public UserFeedItem getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, UserFeedItem item){
        items.set(position, item);
    }

}
