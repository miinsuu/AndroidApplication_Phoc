package com.example.phoc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TitleListItemAdapter extends RecyclerView.Adapter<TitleListItemAdapter.ViewHolder>{
    ArrayList<TitleListItem> items = new ArrayList<TitleListItem>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.titlelist_item, viewGroup, false);


        return new ViewHolder(itemView);
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
        TextView amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.particulartitle);
            amount = itemView.findViewById(R.id.inTitleItemAmount);
        }

        public void setItem(TitleListItem item){
            title.setText("# "+item.getTitle());
            amount.setText(item.getAmount()+"개의 작품");
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
