package org.techtown.lottoworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PurchaseHistoryAdapter extends RecyclerView.Adapter<PurchaseHistoryAdapter.ViewHolder> {
    private ArrayList<String> mData = null;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolderforSticker extends RecyclerView.ViewHolder {
        TextView textView1;

        ViewHolderforSticker(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            textView1 = itemView.findViewById(R.id.roundSticker);
        }
    }

    public class ViewHolderforList extends RecyclerView.ViewHolder {
        TextView textView1;

        ViewHolderforList(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            textView1 = itemView.findViewById(R.id.roundSticker);
        }
    }
    // 생성자에서 데이터 리스트 객체를 전달받음.
    PurchaseHistoryAdapter(ArrayList<String> list) {
        mData = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public PurchaseHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.purchase_history_item_sticker, parent, false);
        PurchaseHistoryAdapter.ViewHolder vh = new PurchaseHistoryAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(PurchaseHistoryAdapter.ViewHolder holder, int position) {
        String text = mData.get(position);
        holder.textView1.setText(text);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }


}
