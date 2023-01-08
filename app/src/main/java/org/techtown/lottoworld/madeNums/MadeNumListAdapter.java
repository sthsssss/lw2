package org.techtown.lottoworld.madeNums;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.techtown.lottoworld.MadeNumQuery;
import org.techtown.lottoworld.R;

import java.util.ArrayList;

public class MadeNumListAdapter extends RecyclerView.Adapter<MadeNumListAdapter.ViewHolder> {
    ArrayList<MadeNumQuery> items = new ArrayList<MadeNumQuery>();

    // view type
    private int TYPE_DATE = 201;
    private int TYPE_LIST = 202;

    //아이템 클릭 리스너 인터페이스
    interface OnItemClickListener{
        void onDeleteClick(View v, int positon);//삭제
    }

    //리스너 객체 참조 변수
    private OnItemClickListener mListener = null;
    //리스너 객체 참조를 어댑터에 전달 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MadeNumListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(getViewScr(viewType), parent,false);

        return new MadeNumListAdapter.ViewHolder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull MadeNumListAdapter.ViewHolder holder, int position) {
        MadeNumQuery item = items.get(position);
        holder.bind(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(MadeNumQuery item) {
        items.add(item);
    }

    public void setItems(ArrayList<MadeNumQuery> items) {
        this.items = items;
    }

    public MadeNumQuery getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, MadeNumQuery item) {
        items.set(position, item);
    }

    private int getViewScr(int viewType){
        if(viewType == TYPE_DATE){
            return R.layout.made_num_date;
        }else{
            return R.layout.made_num_item;
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (items.get(position).getId() == -1){
            return TYPE_DATE;
        } else {
            return TYPE_LIST;
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private int viewType;

        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;

            Button deleteButton = itemView.findViewById(R.id.deleteButton);
            if(deleteButton != null){
                deleteButton.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition ();
                        if (position!=RecyclerView.NO_POSITION){
                            if (mListener!=null){
                                mListener.onDeleteClick(view,position);
                            }
                        }
                    }
                });
            }
        }

        public void bind(MadeNumQuery item){
            if (viewType== TYPE_DATE){
                bindDate(item);
            } else if(viewType==TYPE_LIST) {
                bindItem(item);
            }
        }
        private void bindDate(MadeNumQuery item){
            TextView madeDate = itemView.findViewById(R.id.madeDate);
            madeDate.setText(item.getDate());
        }
        private void bindItem(MadeNumQuery item){
            String staticT = "총합:" + item.getTotal() + " 짝홀:" + item.getEven() + "/" +  (6 - item.getEven());

            TextView nums = itemView.findViewById(R.id.nums);
            nums.setText(item.numberString());
            TextView statics = itemView.findViewById(R.id.staticsMade);
            statics.setText(staticT);

        }
    }

}
