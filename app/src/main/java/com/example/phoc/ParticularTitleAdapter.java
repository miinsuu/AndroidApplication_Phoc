package com.example.phoc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ParticularTitleAdapter extends RecyclerView.Adapter<ParticularTitleAdapter.ViewHolder> {
    ArrayList<ParticularTitleItem> items = new ArrayList<ParticularTitleItem>();

    public interface OnItemClickListener{
        public  void onItemClick(View view, int position, int type);
    }

    private OnItemClickListener onItemClickListener;

    public ParticularTitleAdapter(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.particulartitle_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        ParticularTitleAdapter.ViewHolder holder = (ParticularTitleAdapter.ViewHolder)viewHolder;
        holder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewType1은 TextView인 userName
                onItemClickListener.onItemClick(v, position, 1);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            comment = itemView.findViewById(R.id.inParticulartitleComment);
            userName = itemView.findViewById(R.id.fromParticularTitle2UserFeed);
            date = itemView.findViewById(R.id.intParticulartitleDate);
        }

        public void setItem(ParticularTitleItem item){
            comment.setText(item.getComment());
            userName.setText(item.getUserName());
            date.setText(item.getDate());
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
