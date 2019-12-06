package com.example.phoc.MyFeedListVIew;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phoc.R;

import java.util.ArrayList;
import android.content.Context;

public class MyFeedItemAdapter extends RecyclerView.Adapter<MyFeedItemAdapter.ViewHolder> {
    ArrayList<MyFeedItem> items = new ArrayList<MyFeedItem>();
    private Context context;
    public MyFeedItemAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.feed_item, viewGroup, false);

        return new ViewHolder(itemView, this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        MyFeedItem item = items.get(position);
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
        ImageView imgView;
        Context context;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;

            title = itemView.findViewById(R.id.fromMyFeed2ParticularTitle);
            comment = itemView.findViewById(R.id.inMyFeedComment);
            date = itemView.findViewById(R.id.inMyFeedDate);
            imgView = itemView.findViewById(R.id.imgView);
        }

        public void setItem(MyFeedItem item){
            title.setText(item.getTitle());
            comment.setText(item.getComment());
            date.setText(item.getDate());
            Log.d("Post", item.getImgUri());
            Uri uri = Uri.parse(item.getImgUri());
            Glide.with(context).load(uri).into(imgView);

        }
    }

    public void addItem(MyFeedItem item){
        items.add(item);
    }

    public void setItems(ArrayList<MyFeedItem> items){
        this.items = items;
    }

    public MyFeedItem getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, MyFeedItem item){
        items.set(position, item);
    }
}
