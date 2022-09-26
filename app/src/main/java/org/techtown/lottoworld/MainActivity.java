package org.techtown.lottoworld;

import static org.techtown.lottoworld.IntroActivity.numberQueryList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //Todo : api 가져오기
    //Todo : 화면표시 인터페이스
    //Todo : 당첨확인
    //Todo : 버튼 온클릭 이벤트 -> 액티비티 넘어가게끔
    // 최신회차 숫자

    // 넘버 텍스트뷰
    TextView latestWinningNumber_1,latestWinningNumber_2,latestWinningNumber_3,
    latestWinningNumber_4,latestWinningNumber_5,latestWinningNumber_6,latestWinningNumber_Bonus,roundMain;

    Button checkWinning,winningHistory,createLotto,analyzingLotto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Todo : 일단 예시로 1029 넣어둠 최신회차로 변경해야됨
        viewMatching();
        setNumbersView();
        buttonIntentMatching();
    }

    // 메인화면 View 매칭
    public void viewMatching(){
        Log.d("Taglog","we get into the ViewMatching function");
        latestWinningNumber_1 = findViewById(R.id.latestWinningNo1);
        latestWinningNumber_2 = findViewById(R.id.latestWinningNo2);
        latestWinningNumber_3 = findViewById(R.id.latestWinningNo3);
        latestWinningNumber_4 = findViewById(R.id.latestWinningNo4);
        latestWinningNumber_5 = findViewById(R.id.latestWinningNo5);
        latestWinningNumber_6 = findViewById(R.id.latestWinningNo6);
        latestWinningNumber_Bonus = findViewById(R.id.latestWinningNoBonus);
        roundMain = findViewById(R.id.roundMain);
        checkWinning = findViewById(R.id.checkWinning);
        winningHistory = findViewById(R.id.winningHistory);
        createLotto = findViewById(R.id.createLotto);
        analyzingLotto = findViewById(R.id.analyzingLotto);
    }
    // api로 가져온 번호를 뷰에 띄우기
    public void setNumbersView(){
        Log.d("Taglog","we get into the setNumbersView function");
        NumberQuery numberQuery = numberQueryList.get(0);
        int nums[] = numberQuery.getNums();
        latestWinningNumber_1.setText(nums[0]);
        latestWinningNumber_2.setText(nums[1]);
        latestWinningNumber_3.setText(nums[2]);
        latestWinningNumber_4.setText(nums[3]);
        latestWinningNumber_5.setText(nums[4]);
        latestWinningNumber_6.setText(nums[5]);
        latestWinningNumber_Bonus.setText(nums[6]);
        roundMain.setText(numberQuery.getRound() + " 회차");
    }

    // button 액티비티 연결
    public void buttonIntentMatching(){
         checkWinning.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getApplicationContext(), CheckWinningActivity.class);
                 startActivity(intent);
             }
         });
         winningHistory.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getApplicationContext(), WinningHistoryActivity.class);
                 startActivity(intent);
             }
         });
    }
}