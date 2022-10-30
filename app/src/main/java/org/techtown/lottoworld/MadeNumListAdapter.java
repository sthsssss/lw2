package org.techtown.lottoworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MadeNumListAdapter extends RecyclerView.Adapter<MadeNumListAdapter.ViewHolder> {
    ArrayList<NumberQuery> items = new ArrayList<NumberQuery>();

    @NonNull
    @Override
    public MadeNumListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.made_num_item, parent,false);

        return new MadeNumListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MadeNumListAdapter.ViewHolder holder, int position) {
        NumberQuery item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(NumberQuery item) {
        items.add(item);
    }

    public void setItems(ArrayList<NumberQuery> items) {
        this.items = items;
    }

    public NumberQuery getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, NumberQuery item) {
        items.set(position, item);
    }
    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView madeWNums;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            madeWNums = itemView.findViewById(R.id.madeWNums);
        }
        public void setItem(NumberQuery item) {
            madeWNums.setText(item.numberString());
        }

    }
}
