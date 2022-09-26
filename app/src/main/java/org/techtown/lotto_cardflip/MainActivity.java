package org.techtown.lotto_cardflip;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    boolean isStarted = false;
    int nums[] = new int[45];
    TextView[] textViews = new TextView[45];
    boolean[] isTextViewClicked = new boolean[45];
    static int TextViewActiveCnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button analysisBtn = findViewById(R.id.analysisBtn);
        analysisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent numIntent = new Intent(getApplicationContext(), NumAnalysis.class);
                numIntent.putExtra("numData", Arrays.copyOfRange(nums, 0, 6));
            }
        });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isStarted = true;

                for (int i  = 0; i < 45; i++) {
                    textViews[i].setBackgroundResource(R.drawable.card);
                    textViews[i].setText("");
                    isTextViewClicked[i] = false;
                }

                TextViewActiveCnt = 0;

                // 1 ~ 45까지의 중복되지 않은 숫자 6개
                Random random = new Random();

                for (int i = 0; i < 45; i++) {
                    nums[i] = random.nextInt(45) + 1;
                    for (int j = 0; j < i; j++) {
                        if (nums[i] == nums[j]) {
                            i--;
                        }
                    }
                }
            }
        });

        for (int i = 0; i < textViews.length; i++) {
            int textViewId = getResources()
                    .getIdentifier("textView"+ (i + 1),"id",getPackageName());
            textViews[i] = findViewById(textViewId);
        }

        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int tempId = view.getId();
                    String tempName = getResources().getResourceEntryName(tempId);
                    int tempIndex =  Integer.parseInt(tempName.substring(8));

                    AnimatorSet anim = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flip);

                    if(!isTextViewClicked[tempIndex-1] && TextViewActiveCnt < 6 && isStarted){
                        isTextViewClicked[tempIndex-1] = true;
                        ++TextViewActiveCnt;
                        anim.setTarget((TextView)view);
                        anim.start();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                ((TextView) view).setBackgroundResource(R.drawable.card2);
                                ((TextView) view).setText(nums[tempIndex-1] + "");
                            }
                        }, 1000);
                    }
                    else if(isTextViewClicked[tempIndex-1]) {
                        Toast.makeText(getApplicationContext(), "한 번 선택한 카드는 다시 선택할 수 없습니다", Toast.LENGTH_SHORT).show();
                    }
                    else if (!isStarted) {
                        Toast.makeText(getApplicationContext(), "카드를 먼저 섞어주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if (TextViewActiveCnt >= 6) {
                        Toast.makeText(getApplicationContext(), "6개 이상 선택하실 수 없습니다", Toast.LENGTH_SHORT).show();
                        isStarted = false;
                    }
                }
            });
        }
    }

}