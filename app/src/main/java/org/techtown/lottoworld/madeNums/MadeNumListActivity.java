package org.techtown.lottoworld.madeNums;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import org.techtown.lottoworld.DataAdapter;
import org.techtown.lottoworld.NumberQuery;
import org.techtown.lottoworld.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MadeNumListActivity extends AppCompatActivity {
    int pages; // 전체 페이지 수
    int totalItem;
    int page = 0; // 현재 페이지
    List<NumberQuery> madeQueryList;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_made_num_list);

        RecyclerView recyclerView = findViewById(R.id.madeNum);

        getNumberQueryList();

        ArrayList<NumberQuery> listWithSticker = (ArrayList<NumberQuery>) makeSticker();


        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        MadeNumListAdapter adapter = new MadeNumListAdapter();

        adapter.setItems(listWithSticker);

        recyclerView.setAdapter(adapter);

    }
    public int getNumberQueryList(){
        int round = 0;
        try {
            DataAdapter mDbAdapter = new DataAdapter(getApplicationContext());
            mDbAdapter.open();

            // db에 있는 값들을 model을 적용해서 넣는다.
            madeQueryList = mDbAdapter.getMadeNums();
            // db 닫기
            mDbAdapter.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return round;
    }
    public List<NumberQuery> makeSticker(){
        List<NumberQuery> newList = new ArrayList<>();
        String preDate = "1979";

        Collections.reverse(madeQueryList);

        for(NumberQuery numberQuery : madeQueryList){
            if(!numberQuery.getDate().equals(preDate)){
                newList.add(new NumberQuery(-1,numberQuery.getDate(),new int[]{0}));
                preDate = numberQuery.getDate();
            }
            newList.add(numberQuery);
        }
        return newList;
    }

}