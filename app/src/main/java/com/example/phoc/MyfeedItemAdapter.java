package com.example.phoc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyfeedItemAdapter extends RecyclerView.Adapter<MyfeedItemAdapter.ViewHolder>{
    ArrayList<MyfeedItem> items = new ArrayList<MyfeedItem>();

    public interface OnItemClickListener{
        public  void onItemClick(View view, int position, int type);
    }

    private OnItemClickListener onItemClickListener;

    public MyfeedItemAdapter(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.myfeed_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        MyfeedItemAdapter.ViewHolder holder = (MyfeedItemAdapter.ViewHolder)viewHolder;
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewType1은 TextView인 title
                onItemClickListener.onItemClick(v, position, 1);
            }
        });
        MyfeedItem item = items.get(position);
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

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.fromMyFeed2ParticularTitle);
            comment = itemView.findViewById(R.id.inMyFeedComment);
            date = itemView.findViewById(R.id.inMyFeedDate);
        }

        public void setItem(MyfeedItem item){
            title.setText(item.getTitle());
            comment.setText(item.getComment());
            date.setText(item.getDate());
        }
    }

    public void addItem(MyfeedItem item){
        items.add(item);
    }

    public void setItems(ArrayList<MyfeedItem> items){
        this.items = items;
    }

    public MyfeedItem getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, MyfeedItem item){
        items.set(position, item);
    }
}
