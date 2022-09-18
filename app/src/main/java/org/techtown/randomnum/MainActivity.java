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
    MediaPlayer mediaPlayer2;

    Boolean clicked1 = false;
    Boolean clicked2 = false;
    Boolean clicked3 = false;
    Boolean clicked4 = false;
    Boolean clicked5 = false;
    Boolean clicked6 = false;

    int nums[] = new int[6];

    boolean isStarted = false;

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

                isStarted = true;

                // 룰렛 돌아가는 효과음
                mediaPlayer1 = MediaPlayer.create(getApplicationContext(), R.raw.roulette);
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

            }

        });

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStarted) {
                    dingdong();
                    textView1.setText(nums[0]+"");
                    textView1.setBackgroundResource(R.drawable.roulette2_drawable);
                    clicked1 = true;
                    endOfRoulette();
                }
                else {
                    Toast.makeText(getApplicationContext(), "숫자를 먼저 섞어주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStarted) {
                    dingdong();
                    textView2.setText(nums[1]+"");
                    textView2.setBackgroundResource(R.drawable.roulette2_drawable);
                    clicked2 = true;
                    endOfRoulette();
                }
                else {
                    Toast.makeText(getApplicationContext(), "숫자를 먼저 섞어주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStarted) {
                    dingdong();
                    textView3.setText(nums[2]+"");
                    textView3.setBackgroundResource(R.drawable.roulette2_drawable);
                    clicked3 = true;
                    endOfRoulette();
                }
                else {
                    Toast.makeText(getApplicationContext(), "숫자를 먼저 섞어주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStarted) {
                    dingdong();
                    textView4.setText(nums[3]+"");
                    textView4.setBackgroundResource(R.drawable.roulette2_drawable);
                    clicked4 = true;
                    endOfRoulette();
                }
                else {
                    Toast.makeText(getApplicationContext(), "숫자를 먼저 섞어주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStarted) {
                    dingdong();
                    textView5.setText(nums[4]+"");
                    textView5.setBackgroundResource(R.drawable.roulette2_drawable);
                    clicked5 = true;
                    endOfRoulette();
                }
                else {
                    Toast.makeText(getApplicationContext(), "숫자를 먼저 섞어주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStarted) {
                    dingdong();
                    textView6.setText(nums[5]+"");
                    textView6.setBackgroundResource(R.drawable.roulette2_drawable);
                    clicked6 = true;
                    endOfRoulette();
                }
                else {
                    Toast.makeText(getApplicationContext(), "숫자를 먼저 섞어주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 룰렛 클릭 시 효과음
    public void dingdong() {
        mediaPlayer2 = MediaPlayer.create(getApplicationContext(), R.raw.dingdong);
        mediaPlayer2.start();
    }

    // 한번의 시도가 끝났을 때
    public void endOfRoulette() {
        mediaPlayer2.stop();
        if (clicked1 && clicked2 && clicked3 && clicked4 && clicked5 & clicked6) {
            mediaPlayer1.stop();
        }
    }
}