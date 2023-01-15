package org.techtown.lottoworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.techtown.lottoworld.madeNums.MadeNumListAdapter;

import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseHistoryActivity extends AppCompatActivity {
    DataBaseHelper mDBHelper;
    SQLiteDatabase database;
    int[] temp = {1,3,13,16,17,32};
    NumberQuery nq = new NumberQuery(1028,"2020",temp);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recyclerView_ph);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        PurchaseHistoryAdapter adapter = new PurchaseHistoryAdapter();
        ArrayList<PurchaseData> myList = selectData();
        adapter.submitData(myList);
        recyclerView.setAdapter(adapter);

        //리사이클러뷰 클릭 이벤트
        adapter.setOnItemClickListener(new PurchaseHistoryAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(View v, int position) {
                PurchaseData purchaseData = myList.get(position);
                deleteNum(purchaseData.id);
                myList.remove(position);
                if(myList.get(position - 1).type == 102){
                    if(myList.size() == position)
                    {
                        myList.remove(position - 1);
                    }else if(myList.get(position).type == 102){
                        myList.remove(position - 1);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public ArrayList<PurchaseData> selectData(){
        try {
            Log.d("purchasedhistory","into the selectData()");
            ArrayList<PurchaseData> tmpArrayData = new ArrayList<PurchaseData>();
            DataAdapter ph_DbAdapter = new DataAdapter(getApplicationContext());
            ph_DbAdapter.open();
            tmpArrayData = ph_DbAdapter.loadDBforPHlist();
            ph_DbAdapter.close();
            return tmpArrayData;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<PurchaseData> deleteChoosen(int pos){
        try {
            ArrayList<PurchaseData> tmpArrayData = new ArrayList<PurchaseData>();
            DataAdapter ph_DbAdapter = new DataAdapter(getApplicationContext());
            ph_DbAdapter.open();
            tmpArrayData = ph_DbAdapter.loadDBforPHlist();
            ph_DbAdapter.close();
            return tmpArrayData;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteNum(int id){
        try {
            DataAdapter mDbAdapter = new DataAdapter(getApplicationContext());
            mDbAdapter.open();

            mDbAdapter.deletePurchasedNum(id);

            mDbAdapter.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}