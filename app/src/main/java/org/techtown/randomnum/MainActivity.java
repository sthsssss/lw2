package org.techtown.randomnum;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    boolean isStarted = false;

    int nums[] = new int[6];

    TextView[] textViews = new TextView[6];
    boolean[] isTextViewClicked = new boolean[6];
    boolean isAllTrue = true;

    MediaPlayer mediaPlayer1;
    MediaPlayer mediaPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button analysisBtn = findViewById(R.id.analysisBtn);
        analysisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent numIntent = new Intent(getApplicationContext(), NumAnalysis.class);
                numIntent.putExtra("numData", nums);
            }
        });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isStarted = true;

                // 숫자 지우기
                for (TextView textView : textViews) {
                    textView.setText("");
                    textView.setBackgroundResource(R.drawable.roulette);
                }

                // Clicked 리셋
                for (int i = 0; i < isTextViewClicked.length; i++) {
                    isTextViewClicked[i] = false;
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

            }

        });

        for (int i = 0; i < textViews.length; i++) {
            int textViewId = getResources()
                    .getIdentifier("textView"+ (i + 1),"id",getPackageName());
            textViews[i] = findViewById(textViewId);
            textViews[i].setSoundEffectsEnabled(false);
        }

        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int tempId = view.getId();
                    String tempName = getResources().getResourceEntryName(tempId);
                    int tempIndex =  Integer.parseInt(tempName.substring(8));

                    Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.spin);

                    if (isStarted) {
                        MediaPlayer mediaPlayer1 = MediaPlayer.create(getApplicationContext(), R.raw.roulette_spin);
                        mediaPlayer1.start();

                        ((TextView) view).startAnimation(anim);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                ((TextView) view).setText(nums[tempIndex-1]+"");
                                isTextViewClicked[tempIndex-1] = true;
                                endOfRoulette();
                            }
                        }, 1500);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "숫자를 먼저 섞어주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // 한번의 시도가 끝났을 때
    public void endOfRoulette() {
        isAllTrue = true;

        for (int i = 0; i < isTextViewClicked.length; i++) {
            if (isTextViewClicked[i] == false) {
                isAllTrue = false;
            }
        }

        if (isAllTrue) {
            isStarted = false;
        }
    }
}