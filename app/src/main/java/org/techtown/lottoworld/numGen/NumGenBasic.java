package org.techtown.lottoworld.numGen;

import static android.widget.Toast.makeText;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.techtown.lottoworld.R;
import org.techtown.lottoworld.numAnalysis.NumAnalysisActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class NumGenBasic extends AppCompatActivity {

    Random rd = new Random();

    // Variable for Button Events
    Button[] inputButtons = new Button[46];
    boolean[] isButtonClicked = new boolean[46];
    static int buttonActiveCnt = 0;
    TextView textView1,textView2,textView3,textView4,textView5,textView6;
    ArrayList<Integer> numList = new ArrayList<>();

    int[] include;
    int[] exclude;
    int[] nums = new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.num_gen_basic);

        numList.clear();

        // View Matching
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);

        TextView textviewList[] = {textView1, textView2, textView3, textView4, textView5, textView6};

//        나머지 부분과 merge 후 주석 해제
        Button analysisBtn = findViewById(R.id.analysisBtn);
        analysisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeText(getApplicationContext(), "번호가 저장되었습니다.",Toast.LENGTH_SHORT);
                Intent numIntent = new Intent(getApplicationContext(), NumAnalysisActivity.class);
                numIntent.putExtra("numData", nums);
                startActivity(numIntent);
            }
        });

        buttonSetting();

        // Include
        Button includeBtn = findViewById(R.id.includeBtn);
        includeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numList.size() > 5) {
                    Toast.makeText(getApplicationContext(), "최대 5개의 수만 포함할 수 있습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (numList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "최소 한개의 수는 포함해야 합니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    include = new int[numList.size()];
                    for (int i = 0; i < numList.size(); i++) {
                        include[i] = numList.get(i);
                    }

                    numList.clear();

                    for (int i = 1; i < 46; i++) {
                        if (isButtonClicked[i] == true) {
                            inputButtons[i].setBackgroundResource(R.drawable.selfinputbuttonclickedinclude);
                            isButtonClicked[i] = false;
                        }
                    }

                    Toast.makeText(getApplicationContext(), "포함수에 저장되었습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Exclude
        Button excludeBtn = findViewById(R.id.excludeBtn);
        excludeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numList.size() > 38) {
                    Toast.makeText(getApplicationContext(), "최대 38개의 수만 포함할 수 있습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (numList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "최소 한개의 수는 포함해야 합니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    exclude = new int[numList.size()];
                    for (int i = 0; i < numList.size(); i++) {
                        exclude[i] = numList.get(i);
                    }

                    numList.clear();

                    for (int i = 1; i < 46; i++) {
                        if (isButtonClicked[i] == true) {
                            inputButtons[i].setBackgroundResource(R.drawable.selfinputbuttonclickedexclude);
                            isButtonClicked[i] = false;
                        }
                    }

                    Toast.makeText(getApplicationContext(), "제외수에 저장되었습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // pick
        Button pickBtn = findViewById(R.id.pickBtn);
        pickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 6; i++) {
                    if (nonNull(include) && nonNull(exclude)) {
                        if (i < include.length) {

                            // include
                            nums[i] = include[i];

                        }
                        else {
                            nums[i] = rd.nextInt(45) + 1;

                            // exclude
                            for (int j = 0; j < exclude.length; j++) {
                                if (nums[i] == exclude[j]) {
                                    i--;
                                    break;
                                }
                            }
                        }
                    }
                    else if (nonNull(include) && isNull(exclude)) {
                        if (i < include.length) {

                            // include
                            nums[i] = include[i];

                        }
                        else {
                            nums[i] = rd.nextInt(45) + 1;
                        }
                    }
                    else if (isNull(include) && nonNull(exclude)) {
                        nums[i] = rd.nextInt(45) + 1;

                        // exclude
                        for (int j = 0; j < exclude.length; j++) {
                            if (nums[i] == exclude[j]) {
                                i--;
                                break;
                            }
                        }
                    }
                    else {
                        nums[i] = rd.nextInt(45) + 1;
                    }

                    // 중복된 수 없애기
                    for (int j = 0; j < i; j++) {
                        if (nums[i] == nums[j]) {
                            i--;
                            break;
                        }
                    }
                }


                Arrays.sort(nums);

                for (int i = 0; i < 6; i++) {
                    textviewList[i].setText(nums[i] + "");

                    if (nums[i] >= 1 && nums[i] <= 10) {
                        textviewList[i].setBackgroundResource(R.drawable.yellow);
                    }
                    else if (nums[i] >= 11 && nums[i] <= 20) {
                        textviewList[i].setBackgroundResource(R.drawable.blue);
                    }
                    else if (nums[i] >= 21 && nums[i] <= 30) {
                        textviewList[i].setBackgroundResource(R.drawable.red);
                    }
                    else if (nums[i] >= 31 && nums[i] <= 40) {
                        textviewList[i].setBackgroundResource(R.drawable.gray);
                    }
                    else {
                        textviewList[i].setBackgroundResource(R.drawable.green);
                    }
                }

                for (int i = 1; i < 46; i++) {
                    inputButtons[i].setBackgroundResource(R.drawable.selfinputbutton);
                }

                include = null;
                exclude = null;
            }
        });

    }

    // Function for Setting Button Event
    public void buttonSetting() {
        int tempResId;
        for (int i = 1; i <= 45; i++) {
            isButtonClicked[i] = false;
            tempResId = getResources()
                    .getIdentifier("button" + i, "id", getPackageName());
            inputButtons[i] = findViewById(tempResId);
        }

        //
        for (int i = 1; i <= 45; i++) {
            inputButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // View ID를 받아옴
                    int tempId = v.getId();
                    String tempName = getResources().getResourceEntryName(tempId);
                    int tempIndex = Integer.parseInt(tempName.substring(6));

                    // Button Activated
                    if (!isButtonClicked[tempIndex]) {
                        isButtonClicked[tempIndex] = true;
                        ++buttonActiveCnt;
                        numList.add(tempIndex);
                        v.setBackgroundResource(R.drawable.selfinputbuttonclicked);
                    }// Button Disabled
                    else if (isButtonClicked[tempIndex]) {
                        isButtonClicked[tempIndex] = false;
                        --buttonActiveCnt;
                        numList.remove(numList.indexOf(tempIndex));
                        v.setBackgroundResource(R.drawable.selfinputbutton);
                    }
                }
            });
        }
    }
}