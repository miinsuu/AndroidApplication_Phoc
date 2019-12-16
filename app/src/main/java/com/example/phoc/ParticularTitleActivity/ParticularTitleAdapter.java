package com.example.phoc.ParticularTitleActivity;

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
import com.example.phoc.MainActivity;
import com.example.phoc.R;

import java.util.ArrayList;
import android.content.Context;

public class ParticularTitleAdapter extends RecyclerView.Adapter<ParticularTitleAdapter.ViewHolder> {
    ArrayList<ParticularTitleItem> items = new ArrayList<ParticularTitleItem>();
    Context context;
    public interface OnItemClickListener{
        public  void onItemClick(ParticularTitleItem item, int type);
    }

    private OnItemClickListener onItemClickListener;

    public ParticularTitleAdapter(OnItemClickListener onItemClickListener, Context context){
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.particulartitle_item, viewGroup, false);

        return new ViewHolder(itemView, this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        ParticularTitleAdapter.ViewHolder holder = (ParticularTitleAdapter.ViewHolder)viewHolder;
        holder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewType1은 TextView인 userName
                onItemClickListener.onItemClick(items.get(position), 1);
            }
        });

        ParticularTitleItem item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView comment;
        TextView userName;
        TextView date;
        ImageView imgView;
        Context context;
        ImageButton exifBtn;

        public ViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            this.context = context;
            comment = itemView.findViewById(R.id.inParticulartitleComment);
            userName = itemView.findViewById(R.id.fromParticularTitle2UserFeed);
            date = itemView.findViewById(R.id.intParticulartitleDate);
            imgView = itemView.findViewById(R.id.imgView);
            exifBtn = itemView.findViewById(R.id.particulartitleExifSmallBtn);

        }

        public void setItem(final ParticularTitleItem item){
            comment.setText(item.getComment());
            userName.setText(item.getUserName());
            date.setText(item.getDate());
            Uri uri = Uri.parse(item.imgUrl);
            Glide.with(context).load(uri).into(imgView);

            exifBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("titleName", item.getTitle());
                    intent.putExtra("exifJsonString", item.getExifJsonString());
                    context.startActivity(intent);
                }
            });
        }


    }

    public void addItem(ParticularTitleItem item){
        items.add(item);
    }

    public void setItems(ArrayList<ParticularTitleItem> items){
        this.items = items;
    }

    public ParticularTitleItem getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, ParticularTitleItem item){
        items.set(position, item);
    }
}
