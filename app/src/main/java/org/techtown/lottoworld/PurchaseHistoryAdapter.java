package org.techtown.lottoworld;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.techtown.lottoworld.madeNums.MadeNumListAdapter;

import java.util.ArrayList;

public class PurchaseHistoryAdapter extends RecyclerView.Adapter<PurchaseHistoryAdapter.ViewHolder> {
    private ArrayList<String> mData = null;
    interface OnItemClickListener{
        void onDeleteClick(View v, int positon);//삭제
    }

    //리스너 객체 참조 변수
    private OnItemClickListener mListener = null;
    //리스너 객체 참조를 어댑터에 전달 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public PurchaseHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(getViewSrc(viewType), parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(PurchaseHistoryAdapter.ViewHolder holder, int position) {
        holder.bind(dataSet.get(position));
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    // view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        private int viewType;
        public ViewHolder(View itemView, int viewType){
            super(itemView);
            this.viewType = viewType;

            Button pdeleteButton = itemView.findViewById(R.id.pdeleteButton);
            if(pdeleteButton != null){
                pdeleteButton.setOnClickListener (new View.OnClickListener () {
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

        public void bind(PurchaseData item){
            if (viewType==TYPE_STICKER){
                bindSticker(item);
            } else if(viewType==TYPE_LIST) {
                bindList(item);
            }
        }

        //Sticker Holder Setting
        private void bindSticker(PurchaseData item){
            TextView roundSticker = itemView.findViewById(R.id.roundSticker);
            TextView pn1 = itemView.findViewById(R.id.pn1);
            TextView pn2 = itemView.findViewById(R.id.pn2);
            TextView pn3 = itemView.findViewById(R.id.pn3);
            TextView pn4 = itemView.findViewById(R.id.pn4);
            TextView pn5 = itemView.findViewById(R.id.pn5);
            TextView pn6 = itemView.findViewById(R.id.pn6);
            TextView pn7 = itemView.findViewById(R.id.pn7);


            Log.d("스티커",Integer.toString(item.round));

            roundSticker.setText(Integer.toString(item.round));
            pn1.setText(Integer.toString(item.nums[0]));
            pn2.setText(Integer.toString(item.nums[1]));
            pn3.setText(Integer.toString(item.nums[2]));
            pn4.setText(Integer.toString(item.nums[3]));
            pn5.setText(Integer.toString(item.nums[4]));
            pn6.setText(Integer.toString(item.nums[5]));
            pn7.setText(Integer.toString(item.nums[6]));
        }

        //List Holder Setting
        private void bindList(PurchaseData item){
            Button dbutton = itemView.findViewById(R.id.deleteButton);
            TextView ranking = itemView.findViewById(R.id.ranking);
            TextView lpn1 = itemView.findViewById(R.id.lpn1);
            TextView lpn2 = itemView.findViewById(R.id.lpn2);
            TextView lpn3 = itemView.findViewById(R.id.lpn3);
            TextView lpn4 = itemView.findViewById(R.id.lpn4);
            TextView lpn5 = itemView.findViewById(R.id.lpn5);
            TextView lpn6 = itemView.findViewById(R.id.lpn6);

            ranking.setText(Integer.toString(item.rank) + "위");
            lpn1.setText(Integer.toString(item.nums[0]));
            lpn2.setText(Integer.toString(item.nums[1]));
            lpn3.setText(Integer.toString(item.nums[2]));
            lpn4.setText(Integer.toString(item.nums[3]));
            lpn5.setText(Integer.toString(item.nums[4]));
            lpn6.setText(Integer.toString(item.nums[5]));

        }


    }


    // Data Holding ...
    private ArrayList<PurchaseData> dataSet = new ArrayList();

    // view type
    private int TYPE_STICKER = 102;
    private int TYPE_LIST = 101;

    public void submitData(ArrayList<PurchaseData> newData){
        // newData is the selected query from Local DB!
        dataSet = newData;
        notifyDataSetChanged();
    }

    // ViewType에 따라 xml 파일을 연결
    private int getViewSrc(int viewType){
        if(viewType == TYPE_STICKER){
            return R.layout.purchase_history_item_sticker;
        } else {
            return R.layout.purchase_history_item_list;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataSet.get(position).type == TYPE_STICKER){
            return TYPE_STICKER;
        } else {
            return TYPE_LIST;
        }
    }
}
