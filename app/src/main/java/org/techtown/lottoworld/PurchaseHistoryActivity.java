package org.techtown.lottoworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PurchaseHistoryActivity extends AppCompatActivity {
    DataBaseHelper mDBHelper;
    SQLiteDatabase database;
    public List<NumberQuery> winninglst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);
        
        getWinningData(); // winninglst initialization
        Collections.reverse(winninglst); // 1회차부터 정렬됨



        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recyclerView_ph);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        PurchaseHistoryAdapter adapter = new PurchaseHistoryAdapter();

        ArrayList<PurchaseData> tmp = selectData();
        bindingWithSticker(); // 스티커랑 같이 묶어줌
        //adapter.submitData(selectData());
        recyclerView.setAdapter(adapter);


    }

    public ArrayList<PurchaseData> selectData(){
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

    public void getWinningData(){
        try{
            DataAdapter mDA = new DataAdapter(getApplicationContext());
            mDA.open();
            winninglst = mDA.getWinningData();
            mDA.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void bindingWithSticker(){
        
    }
}