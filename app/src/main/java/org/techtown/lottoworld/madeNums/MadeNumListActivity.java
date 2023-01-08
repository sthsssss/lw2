package org.techtown.lottoworld.madeNums;

import static android.widget.Toast.makeText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.techtown.lottoworld.DataAdapter;
import org.techtown.lottoworld.MadeNumQuery;
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
    List<MadeNumQuery> madeQueryList;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_made_num_list);

        RecyclerView recyclerView = findViewById(R.id.madeNum);

        getNumberQueryList();

        ArrayList<MadeNumQuery> listWithSticker = (ArrayList<MadeNumQuery>) makeSticker();


        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        MadeNumListAdapter adapter = new MadeNumListAdapter();

        adapter.setItems(listWithSticker);

        recyclerView.setAdapter(adapter);

        //리사이클러뷰 클릭 이벤트
        adapter.setOnItemClickListener (new MadeNumListAdapter.OnItemClickListener() {
            //삭제
            @Override
            public void onDeleteClick(View v, int position) {

                MadeNumQuery madeNumQuery = listWithSticker.get(position);
                //DB에서 삭제
                deleteNum(madeNumQuery.getId());

                listWithSticker.remove (position);
                //이전 이후 아이템을 확인
                    if(checkItem(position, listWithSticker)){
                        listWithSticker.remove (position - 1);
                    }
                adapter.notifyDataSetChanged();
            }
        });
    }
    private boolean checkItem(int position,ArrayList<MadeNumQuery> listWithSticker) {

        if(listWithSticker.get(position - 1).getId() == -1){
            if(listWithSticker.size() == position){
                return true;
            }else if(listWithSticker.get(position).getId() == -1){
                return true;
            }
        }
        return false;
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
    public List<MadeNumQuery> makeSticker(){
        List<MadeNumQuery> newList = new ArrayList<>();
        String preDate = "1979";

        Collections.reverse(madeQueryList);

        for(MadeNumQuery numberQuery : madeQueryList){
            if(!numberQuery.getDate().equals(preDate)){
                newList.add(new MadeNumQuery(-1,numberQuery.getDate(),new int[]{0}));
                preDate = numberQuery.getDate();
            }
            newList.add(numberQuery);
        }
        return newList;
    }
    public void deleteNum(long id){
        try {
            DataAdapter mDbAdapter = new DataAdapter(getApplicationContext());
            mDbAdapter.open();

            mDbAdapter.deleteMadeNum(id);

            mDbAdapter.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertData( NumberQuery wn){
        try {
            DataAdapter mDbAdapter = new DataAdapter(getApplicationContext());
            mDbAdapter.open();
            mDbAdapter.insertMadeNum(wn);
            // db 닫기
            mDbAdapter.close();
            Log.d("insertMadeNum", "성공함");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("insertData", "실패함");
        }
    }

}