package org.techtown.randomnum;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;

    MediaPlayer mediaPlayer1;

    Boolean clicked1 = false;
    Boolean clicked2 = false;
    Boolean clicked3 = false;
    Boolean clicked4 = false;
    Boolean clicked5 = false;
    Boolean clicked6 = false;

    int nums[] = new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);

        TextView textviewList[] = {textView1, textView2, textView3, textView4, textView5, textView6};

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 룰렛 돌아가는 효과음
                mediaPlayer1 = MediaPlayer.create(getApplicationContext(), R.raw.roulette_spinning);
                mediaPlayer1.setLooping(true);
                mediaPlayer1.start();

                // 숫자 지우기
                for (TextView textView : textviewList) {
                    textView.setText("");
                    textView.setBackgroundResource(R.drawable.roulette_drawable);
                }

                // Clicked 리셋
                clicked1 = false; clicked2 = false; clicked3 = false; clicked4 = false; clicked5 = false; clicked6 = false;

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

//                // 숫자 보여주기 (자동생성)
//                for (int i=0; i<6; i++) {
//                    textviewList[i].setText(nums[i]+"");
//                }
            }

        });

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dingdong();
                textView1.setText(nums[0]+"");
                textView1.setBackgroundResource(R.drawable.roulette2_drawable);
                clicked1 = true;
                endOfRoulette();
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dingdong();
                textView2.setText(nums[1]+"");
                textView2.setBackgroundResource(R.drawable.roulette2_drawable);
                clicked2 = true;
                endOfRoulette();
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dingdong();
                textView3.setText(nums[2]+"");
                textView3.setBackgroundResource(R.drawable.roulette2_drawable);
                clicked3 = true;
                endOfRoulette();
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dingdong();
                textView4.setText(nums[3]+"");
                textView4.setBackgroundResource(R.drawable.roulette2_drawable);
                clicked4 = true;
                endOfRoulette();
            }
        });
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dingdong();
                textView5.setText(nums[4]+"");
                textView5.setBackgroundResource(R.drawable.roulette2_drawable);
                clicked5 = true;
                endOfRoulette();
            }
        });
        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dingdong();
                textView6.setText(nums[5]+"");
                textView6.setBackgroundResource(R.drawable.roulette2_drawable);
                clicked6 = true;
                endOfRoulette();
            }
        });
    }

    // 룰렛 클릭 시 효과음
    public void dingdong() {
        MediaPlayer mediaPlayer2 = MediaPlayer.create(getApplicationContext(), R.raw.dingdong);
        mediaPlayer2.start();
    }

    // 한번의 시도가 끝났을 때
    public void endOfRoulette() {
        // 번호 분석 -> 토스트 메시지
        int odd = 0;
        int even = 0;
        int sum = 0;

        for (int num : nums) {
            if (num % 2 == 0) { even++; }
            else { odd++; }
            sum += num;
        }

        Boolean allClicked = clicked1 && clicked2 && clicked3 && clicked4 && clicked5 && clicked6;

        if (allClicked) {
            mediaPlayer1.stop();
            mediaPlayer1.release();
            Toast.makeText(getApplicationContext(), "홀짝 " + odd + " : " + even + " / 총합 " + sum, Toast.LENGTH_LONG).show();
        }
    }
}