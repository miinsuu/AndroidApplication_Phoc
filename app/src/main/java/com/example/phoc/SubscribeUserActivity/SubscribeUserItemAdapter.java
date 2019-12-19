package com.example.phoc.SubscribeUserActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phoc.DatabaseConnection.DataListener;
import com.example.phoc.DatabaseConnection.DatabaseQueryClass;
import com.example.phoc.MainActivity;
import com.example.phoc.R;

import java.util.ArrayList;

public class SubscribeUserItemAdapter extends RecyclerView.Adapter<SubscribeUserItemAdapter.ViewHolder>{
    ArrayList<SubscribeUserItem> items = new ArrayList<SubscribeUserItem>();
    Context context;

    public interface OnItemClickListener{
        public  void onItemClick(SubscribeUserItem item, int type);
    }

    private OnItemClickListener onItemClickListener;

    public SubscribeUserItemAdapter(OnItemClickListener onItemClickListener, Context context){
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.subscribeuser_item, viewGroup, false);

        return new ViewHolder(itemView, this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        SubscribeUserItemAdapter.ViewHolder holder = (SubscribeUserItemAdapter.ViewHolder)viewHolder;
        holder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewType1은 TextView인 userName
                onItemClickListener.onItemClick(items.get(position), 1);
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewType2은 TextView인 title
                onItemClickListener.onItemClick(items.get(position), 2);
            }
        });
        holder.phocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewType3은 imageButton인 phocBtn
                onItemClickListener.onItemClick(items.get(position), 3);
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
        ImageView imgView;
        ImageButton exifBtn;
        ImageButton phocBtn;
        Context context;
        TextView phocNum;

        public ViewHolder(View itemView, Context context){
            super(itemView);

            title = itemView.findViewById(R.id.fromSubscribeUser2ParticularTitle);
            comment = itemView.findViewById(R.id.inSubscribeuserComment);
            userName = itemView.findViewById(R.id.fromSubscribeUser2UserFeed);
            date = itemView.findViewById(R.id.inSubscribeuserDate);
            imgView = itemView.findViewById(R.id.imgView);
            exifBtn = itemView.findViewById(R.id.subscribeuserExifSmallBtn);
            phocBtn = itemView.findViewById(R.id.phocInSubscribeuser);
            phocNum = itemView.findViewById(R.id.phocNumInSubscribeuser);
            this.context = context;
        }

        public void setItem(final SubscribeUserItem item){
            title.setText(item.getTitle());
            comment.setText(item.getComment());
            userName.setText(item.getUserName());
            date.setText(item.getDate());
            Uri uri = Uri.parse(item.getImgUri());
            Glide.with(context).load(uri).into(imgView);
            phocNum.setText(Integer.toString(item.phocNum));

            exifBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("titleName", item.getTitle());
                    intent.putExtra("exifJsonString", item.getExifJsonString());
                    context.startActivity(intent);
                }
            });

            DatabaseQueryClass.Post.isPhocced(item.postId, new DataListener() {
                @Override
                public void getData(Object data, String id) {
                    if((boolean)data){
                        item.isPhoccedFlag = true;
                        phocBtn.setImageResource(R.drawable.phocced);

                    } else {
                        item.isPhoccedFlag = false;
                        phocBtn.setImageResource(R.drawable.phoc);
                    }
                    phocBtn.setScaleType(ImageButton.ScaleType.FIT_CENTER);
                }
            });
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
