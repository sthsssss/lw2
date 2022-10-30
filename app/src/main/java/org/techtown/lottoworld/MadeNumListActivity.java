package org.techtown.lottoworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.sql.SQLException;
import java.util.List;

public class MadeNumListActivity extends AppCompatActivity {
    int pages; // 전체 페이지 수
    int totalItem;
    int page = 0; // 현재 페이지
    List<NumberQuery> madeQueryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_made_num_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_ph);

        getNumberQueryList();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        WinningNumAdapter adapter = new WinningNumAdapter();


        totalItem = madeQueryList.size();

        if (totalItem % 10 == 0) { // 전체 페이지 계산
            pages = totalItem / 10;
        } else {
            pages = totalItem / 10 + 1;
        }

        addNumItem(adapter);

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int totalCount = recyclerView.getAdapter().getItemCount();

                if (lastPosition == totalCount - 1) {
                    //아이템 추가 ! 입맛에 맞게 설정하시면됩니다.
                    addNumItem(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public int getNumberQueryList() {
        int round = 0;
        try {
            DataAdapter mDbAdapter = new DataAdapter(getApplicationContext());
            mDbAdapter.open();

            // db에 있는 값들을 model을 적용해서 넣는다.
            madeQueryList = mDbAdapter.getMadeNums();
            // db 닫기
            mDbAdapter.close();
            Log.d("insertData", "성공함");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("insertData", "실패함");
        }
        return round;
    }

    public void addNumItem(WinningNumAdapter adapter) {

        int start = page * 10;
        int end;

        if (totalItem < (page + 1) * 10) {
            end = totalItem;
        } else {
            end = (page + 1) * 10;
        }

        for (int i = start; i < end; i++) {
            adapter.addItem(madeQueryList.get(i));
        }
        page++;

    }
}