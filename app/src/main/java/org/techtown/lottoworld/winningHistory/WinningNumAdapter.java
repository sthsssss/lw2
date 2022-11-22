package org.techtown.lottoworld.winningHistory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.lottoworld.NumberQuery;
import org.techtown.lottoworld.R;

import java.util.ArrayList;

public class WinningNumAdapter extends RecyclerView.Adapter<WinningNumAdapter.ViewHolder>{
    ArrayList<NumberQuery> items = new ArrayList<NumberQuery>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.winning_num_item, parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

        TextView round;
        TextView winningNums;
        TextView bonusNum;
        TextView statics;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            round = itemView.findViewById(R.id.round);
            winningNums = itemView.findViewById(R.id.winningNums);
            bonusNum = itemView.findViewById(R.id.bonusNum);
            statics = itemView.findViewById(R.id.statics);

        }
        public void setItem(NumberQuery item) {
            String roundT = item.getRound() + "회 당첨번호";
            String staticT = "총합:" + item.getTotal() + " 짝홀:" + item.getEven() + "/" +  (6 - item.getEven());


            round.setText(roundT);
            winningNums.setText(item.numberString());
            bonusNum.setText(Integer.toString(item.getNums()[6]));

            statics.setText(staticT);
        }


    }
}
