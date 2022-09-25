package org.techtown.lottoworld;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class SelfInputActivity extends AppCompatActivity {
    
    // Todo : local DB 에서 latest Round 를 가져온 후
    //  Self Input 에서 쓰게끔
    //  spinner에서 회차를 받아와야됨
    //  저장하기 누르면 db로 넘겨줘야 돼.
    //  reset 누르면 다 리셋.

    // Variable for Spinner UI
    int latestRound_Temporary = 1029;
    Button purchaseHistoryButton,saveNumbersButton;
    Spinner roundSpinner;
    ArrayList arrayList = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;

    // Variable for Button Events
    Button[] inputButtons = new Button[47];
    boolean[] isButtonClicked = new boolean[47];
    static int buttonActiveCnt = 0;
    TextView choosenBall1,choosenBall2,choosenBall3,choosenBall4,choosenBall5,choosenBall6;
    LinkedList<Integer> choosenBallList = new LinkedList<Integer>();

    // 임시변수
    int global_i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_input);
        choosenBallList.clear();
        // View Matching
        saveNumbersButton = findViewById(R.id.SaveTicketButton);
        roundSpinner = findViewById(R.id.roundSpinner);
        choosenBall1 = findViewById(R.id.choosenBall1);
        choosenBall2 = findViewById(R.id.choosenBall2);
        choosenBall3 = findViewById(R.id.choosenBall3);
        choosenBall4 = findViewById(R.id.choosenBall4);
        choosenBall5 = findViewById(R.id.choosenBall5);
        choosenBall6 = findViewById(R.id.choosenBall6);

        adapterSetting();
        buttonSetting();
    }

    // Function for Setting Spinner Adapter
    public void adapterSetting(){
        // Todo : 일단 latest 없이 하드코딩
        for(int i=latestRound_Temporary;i>=1;i--){
            arrayList.add(Integer.toString(i) + "회");
        }
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,
                arrayList);
        roundSpinner.setAdapter(arrayAdapter);
        roundSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    // Function for Setting Button Event
    public void buttonSetting(){
        int tempResId;
        for(int i=1;i<=45;i++){
             isButtonClicked[i] = false;
             tempResId = getResources()
                     .getIdentifier("button"+Integer.toString(i),"id",getPackageName());
            inputButtons[i] = findViewById(tempResId);
        }

        // global_i = button Index
        for(global_i=1;global_i<=45;global_i++) {
            inputButtons[global_i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // View ID를 받아옴
                    int tempId = v.getId();
                    String tempName = getResources().getResourceEntryName(tempId);
                    int tempIndex =  Integer.parseInt(tempName.substring(6));
                    Toast.makeText(getApplicationContext(), Integer.toString(tempIndex), Toast.LENGTH_SHORT).show();

                    // Button Activated
                    if(!isButtonClicked[tempIndex] && buttonActiveCnt<6){
                        isButtonClicked[tempIndex] = true;
                        ++buttonActiveCnt;
                        choosenBallList.add(tempIndex);
                        v.setBackgroundResource(R.drawable.selfinputbuttonclicked);
                        displayPressed();
                    }// Button Disabled
                    else if(isButtonClicked[tempIndex]) {
                        isButtonClicked[tempIndex] = false;
                        --buttonActiveCnt;
                        choosenBallList.remove(choosenBallList.indexOf(tempIndex));
                        v.setBackgroundResource(R.drawable.selfinputbutton);
                        displayPressed();
                    }
                }
            });
        }
    }

    public void displayPressed(){
        int i;
        int tmpId;
        String choosenBall = "choosenBall";
        // View ID를 받아옴
        Collections.sort(choosenBallList);

        Log.d("loging",Integer.toString(buttonActiveCnt));

        for(i=0;i<buttonActiveCnt;i++){
            String choosenBallfull = "";
            choosenBallfull = choosenBall + Integer.toString(i+1);
            tmpId = getResources()
                    .getIdentifier(choosenBallfull,"id",getPackageName());
            TextView tmpView = findViewById(tmpId);
            tmpView.setText(Integer.toString(choosenBallList.get(i)));
        }
        for(;i<6;i++){
            String choosenBallfull = "";
            choosenBallfull = choosenBall + Integer.toString(i+1);
            tmpId = getResources()
                    .getIdentifier(choosenBallfull,"id",getPackageName());
            TextView tmpView = findViewById(tmpId);
            tmpView.setText("");
        }
    }
}