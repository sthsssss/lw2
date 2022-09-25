package org.techtown.lottoworld;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.sql.SQLException;

public class PurchaseHistoryActivity extends AppCompatActivity {
    DataBaseHelper mDBHelper;
    SQLiteDatabase database;
    int[] temp = {1,3,13,16,17,32};
    NumberQuery nq = new NumberQuery(1028,"2020",temp);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);


        Button imsi;
        imsi = findViewById(R.id.buttonTemp);
        imsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zxczxc(nq);
            }
        });
    }
    public void zxczxc(NumberQuery nq){
        try {
            DataAdapter ph_DbAdapter = new DataAdapter(getApplicationContext());
            ph_DbAdapter.open();
            ph_DbAdapter.insertPurchasedNum(nq);
            ph_DbAdapter.close();
            Log.d("PurchaseHistoryActivity","button OnClicked");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}