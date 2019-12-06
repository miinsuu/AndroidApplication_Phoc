package com.example.phoc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

interface OnTitleListitemClickListener{
    public void onItemClick(TitleListItemAdapter.ViewHolder holder, View view, int position);
}
public class TitleListItemAdapter extends RecyclerView.Adapter<TitleListItemAdapter.ViewHolder> implements OnTitleListitemClickListener{
    ArrayList<TitleListItem> items = new ArrayList<TitleListItem>();
    OnTitleListitemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.titlelist_item, viewGroup, false);


        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        TitleListItem item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;

        public ViewHolder(@NonNull View itemView, final OnTitleListitemClickListener listener) {
            super(itemView);

            title = itemView.findViewById(R.id.particulartitle);

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

        public void setItem(TitleListItem item){
            title.setText("# "+item.getTitle());
        }
    }
    public void setOnItemClickListenr(OnTitleListitemClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    public void addItem(TitleListItem item){
        items.add(item);
    }

    public void setItems(ArrayList<TitleListItem> items){
        this.items = items;
    }

    public TitleListItem getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, TitleListItem item){
        items.set(position, item);
    }
}
