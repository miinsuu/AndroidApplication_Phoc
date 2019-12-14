package com.example.phoc.UserFeedActivity;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phoc.R;

import java.util.ArrayList;

public class UserFeedItemAdapter extends RecyclerView.Adapter<UserFeedItemAdapter.ViewHolder>{
    ArrayList<UserFeedItem> items = new ArrayList<UserFeedItem>();
    Context context;
    public interface OnItemClickListener{
        public  void onItemClick(UserFeedItem item, int type);
    }

    private OnItemClickListener onItemClickListener;

    public UserFeedItemAdapter(OnItemClickListener onItemClickListener, Context context){
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.userfeed_item, viewGroup, false);

        return new ViewHolder(itemView, this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        UserFeedItemAdapter.ViewHolder holder = (UserFeedItemAdapter.ViewHolder)viewHolder;
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewType1은 TextView인 title
                onItemClickListener.onItemClick(items.get(position), 1);
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
        Context context;
        ImageView imgView;
        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            title = itemView.findViewById(R.id.fromUserFeed2ParticularTitle);
            comment = itemView.findViewById(R.id.inUserFeedComment);
            date = itemView.findViewById(R.id.inUserFeedDate);
            imgView = itemView.findViewById(R.id.imgView);
        }

        public void setItem(UserFeedItem item){
            title.setText(item.getTitle());
            comment.setText(item.getComment());
            date.setText(item.getDate());
            Uri uri = Uri.parse(item.imgUri);
            Glide.with(context).load(uri).into(imgView);
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
