package org.techtown.lottoworld.winningHistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.techtown.lottoworld.DataAdapter;
import org.techtown.lottoworld.NumberQuery;
import org.techtown.lottoworld.R;

import java.sql.SQLException;
import java.util.List;

public class WinningHistoryActivity extends AppCompatActivity {
    static public List<NumberQuery> numberQueryList;
    int pages; // 전체 페이지 수
    int totalItem;
    int page = 0; // 현재 페이지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winning_history);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_ph);

        getNumberQueryList();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        WinningNumAdapter adapter = new WinningNumAdapter();

        totalItem = numberQueryList.size();


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
            numberQueryList = mDbAdapter.getWinningData();
            // db 닫기
            mDbAdapter.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
            adapter.addItem(numberQueryList.get(i));
        }
        page++;

    }
}