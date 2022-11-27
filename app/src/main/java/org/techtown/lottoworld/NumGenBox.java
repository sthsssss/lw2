package org.techtown.lottoworld;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

public class NumGenBox extends AppCompatActivity {

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;

    int nums[] = new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.num_gen_box);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);

        TextView textviewList[] = {textView1, textView2, textView3, textView4, textView5, textView6};

//        나머지 부분과 merge 후 주석 해제
//        Button analysisBtn = findViewById(R.id.analysisBtn);
//        analysisBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent numIntent = new Intent(getApplicationContext(), NumAnalysis.class);
//                numIntent.putExtra("numData", nums);
//            }
//        });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 숫자 지우기
                for (TextView textView : textviewList) {
                    textView.setText("");
                    textView.setBackgroundResource(R.drawable.box_close);
                }

                // 1 ~ 45까지의 중복되지 않은 숫자 6개
                Random random = new Random();

                for(int i=0; i<6; i++) {
                    nums[i] = random.nextInt(45) + 1;
                    for (int j = 0; j < i; j++) {
                        if (nums[i] == nums[j]) {
                            i--;
                        }
                    }
                }

                // 숫자 정렬
                Arrays.sort(nums);

                //
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.box_anim);

                for (TextView tv : textviewList) {
                    tv.startAnimation(anim);
                }

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        dingdong();

                        for (int i = 0; i < textviewList.length; i++) {
                            textviewList[i].setText(nums[i] + "\n");
                            textviewList[i].setBackgroundResource(R.drawable.box_open);
                        }

                    }
                }, 2500);


            }
        });

    }

    // 박스 클릭 시 효과음
    public void dingdong() {
        MediaPlayer mediaPlayer2 = MediaPlayer.create(getApplicationContext(), R.raw.box_open);
        mediaPlayer2.start();
    }

}