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

public class PurchaseHistoryActivity extends AppCompatActivity {
    DataBaseHelper mDBHelper;
    SQLiteDatabase database;
    int[] temp = {1,3,13,16,17,32};
    NumberQuery nq = new NumberQuery(1028,"2020",temp);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);
        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<100; i++) {
            list.add(String.format("TEXT %d", i));
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recyclerView_ph);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        PurchaseHistoryAdapter adapter = new PurchaseHistoryAdapter();
        adapter.submitData(selectData());
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

    private void load_values() {

    }
}