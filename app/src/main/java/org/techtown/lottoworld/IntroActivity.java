package org.techtown.lottoworld;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntroActivity extends AppCompatActivity {

    static public List<NumberQuery> numberQueryList;

    int[] nums = new int[7];
    JsonObject jsonObject;
    RequestQueue requestQueue;
    int latestInDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //인트로를 보여준 후 intent 를 사용해서
                //MainActivity 로 넘어가도록 함

                latestInDB = loadDB();
                addlatestNums();
                getNumberQueryList();


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
            //1.5초 딜레이 후 Runner 객체 실행
        },2000);
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

    public int loadDB(){
        int round = 0;
        try {
            DataAdapter mDbAdapter = new DataAdapter(getApplicationContext());
            mDbAdapter.open();
            round = mDbAdapter.getLatestRound();
            mDbAdapter.close();
            return round;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return round;
    }
    public int getNumberQueryList(){
        int round = 0;
        try {
            DataAdapter mDbAdapter = new DataAdapter(getApplicationContext());
            mDbAdapter.open();

            // db에 있는 값들을 model을 적용해서 넣는다.
            numberQueryList = mDbAdapter.getWinningData();
            // db 닫기
            mDbAdapter.close();
            Log.d("insertData", "성공함");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("insertData", "실패함");
        }
        return round;
    }

    public void addlatestNums(){
        LatestRound round = null;
        {
            try {
                round = new LatestRound();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        int latestRound = round.getRound();
        Log.d("latestRound", Integer.toString(latestRound));
        for(int i = latestInDB + 1; i <= latestRound - 1; i++){
            Log.d("latestInDB loaded","x");
            getLottoApi(i);
        }

    }
    public void insertNewLotto(NumberQuery nq){
        try {
            DataAdapter mDbAdapter = new DataAdapter(getApplicationContext());
            mDbAdapter.open();
            mDbAdapter.insertLastestNumber(nq);
            mDbAdapter.close();
            Log.d("insertData", "성공함");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("insertData", "실패함");
        }
    }
    public void getLottoApi(int round){
        Log.d("Taglog","we get into the getLottoApi function");
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        String[] winningNumber_ParsingIndex = {"drwtNo1","drwtNo2","drwtNo3","drwtNo4","drwtNo5","drwtNo6","bnusNo"};

        String url = "https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=" + Integer.toString(round);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonObject = (JsonObject) JsonParser.parseString(response);
                for (int i = 0; i < 7; i++) {
                    Log.d("introGetLotto",""+jsonObject.get(winningNumber_ParsingIndex[i]));
                    nums[i] = Integer.parseInt(""+jsonObject.get(winningNumber_ParsingIndex[i]));
                }
                String date = "" + jsonObject.get("drwNoDate");
                Log.d(round + "회차 새 번호",nums.toString());
                NumberQuery numberQuery = new NumberQuery(round, date, nums);

                try{insertNewLotto(numberQuery);}
                catch (Exception e){
                    e.printStackTrace();
                    Log.d("새로운 회사 insert 실패", "몰랑랑");
                }
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
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}