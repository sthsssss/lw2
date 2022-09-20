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
                for (TextView textView : textViews) {
                    textView.setText("");
                    textView.setBackgroundResource(R.drawable.roulette_drawable);
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
        }

        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int tempId = view.getId();
                    String tempName = getResources().getResourceEntryName(tempId);
                    int tempIndex =  Integer.parseInt(tempName.substring(8));

                    if (isStarted) {
                        dingdong();
                        ((TextView) view).setText(nums[tempIndex-1]+"");
                        ((TextView) view).setBackgroundResource(R.drawable.roulette2_drawable);
                        isTextViewClicked[tempIndex-1] = true;
                        endOfRoulette();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "숫자를 먼저 섞어주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // 룰렛 클릭 시 효과음
    public void dingdong() {
        mediaPlayer2 = MediaPlayer.create(getApplicationContext(), R.raw.dingdong);
        mediaPlayer2.start();
    }

    // 한번의 시도가 끝났을 때
    public void endOfRoulette() {
        mediaPlayer2.stop();

        isAllTrue = true;

        for (int i = 0; i < isTextViewClicked.length; i++) {
            if (isTextViewClicked[i] == false) {
                isAllTrue = false;
            }
        }

        if (isAllTrue) {
            mediaPlayer1.stop();
            isStarted = false;
        }
    }
}