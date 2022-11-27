package org.techtown.lottoworld;

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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.techtown.lottoworld.madeNums.MadeNumListActivity;
import org.techtown.lottoworld.numAnalysis.NumAnalysisActivity;
import org.techtown.lottoworld.winningHistory.WinningHistoryActivity;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //Todo : api 가져오기
    //Todo : 화면표시 인터페이스
    //Todo : 당첨확인
    //Todo : 버튼 온클릭 이벤트 -> 액티비티 넘어가게끔
    // 최신회차 숫자

    public int lottoRound_Latest = 1029;
    // 최신회차 당첨번호 6개 배열 + 보너스넘버 1개
    String[] winningNumbers_Main = new String[7];
    JsonObject jsonObject;
    RequestQueue requestQueue;

    // 넘버 텍스트뷰
    TextView latestWinningNumber_1,latestWinningNumber_2,latestWinningNumber_3,
            latestWinningNumber_4,latestWinningNumber_5,latestWinningNumber_6,latestWinningNumber_Bonus,roundMain;

    Button checkWinning,winningHistory,createLotto, numsMade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LatestRound round = null;
        {
            try {
                round = new LatestRound();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        int latestRound = round.getRound();
        lottoRound_Latest = latestRound;
        // Todo : 일단 예시로 1029 넣어둠 최신회차로 변경해야됨
        viewMatching();

        getLottoApi(Integer.toString(latestRound));
        buttonIntentMatching();
    }


    // Main 화면 Lotto Api 가져오는 함수
    public void getLottoApi(String roundNumber){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        String[] winningNumber_ParsingIndex = {"drwtNo1","drwtNo2","drwtNo3","drwtNo4","drwtNo5","drwtNo6","bnusNo"};

        String url = "https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=" + roundNumber;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonObject = (JsonObject) JsonParser.parseString(response);
                for (int i = 0; i < 7; i++) {
                    winningNumbers_Main[i] = "" + jsonObject.get(winningNumber_ParsingIndex[i]);
                }
                setNumbersView();
            }
        }, new Response.ErrorListener() {
            // 에러처리
            @Override
            public void onErrorResponse(VolleyError error){
                Log.d("Taglog","getLottoApi Error Occured");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    // 메인화면 View 매칭
    public void viewMatching(){
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
        numsMade = findViewById(R.id.analyzingLotto);
    }
    // api로 가져온 번호를 뷰에 띄우기
    public void setNumbersView(){
        Log.d("Taglog","we get into the setNumbersView function");
        latestWinningNumber_1.setText(winningNumbers_Main[0]);
        latestWinningNumber_2.setText(winningNumbers_Main[1]);
        latestWinningNumber_3.setText(winningNumbers_Main[2]);
        latestWinningNumber_4.setText(winningNumbers_Main[3]);
        latestWinningNumber_5.setText(winningNumbers_Main[4]);
        latestWinningNumber_6.setText(winningNumbers_Main[5]);
        latestWinningNumber_Bonus.setText(winningNumbers_Main[6]);
        roundMain.setText( lottoRound_Latest + " 회차");
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

        numsMade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NumAnalysisActivity.class);
                startActivity(intent);
            }
        });

        createLotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NumGenMain.class);
                startActivity(intent);
            }
        });
    }
}