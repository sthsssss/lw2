package org.techtown.lotto_cardflip;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isStarted = true;

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

                    if(!isTextViewClicked[tempIndex-1] && TextViewActiveCnt<6 && isStarted){
                        isTextViewClicked[tempIndex-1] = true;
                        ++TextViewActiveCnt;
                        ((TextView) view).setBackgroundResource(R.drawable.card2_drawable);
                        ((TextView) view).setText(nums[tempIndex-1] + "");
                    }
                    else if(isTextViewClicked[tempIndex-1]) {
                        isTextViewClicked[tempIndex-1] = false;
                        --TextViewActiveCnt;
                    }
                    else if (!isStarted) {
                        Toast.makeText(getApplicationContext(), "시작 버튼을 눌러주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}