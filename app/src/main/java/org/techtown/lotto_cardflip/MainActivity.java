package org.techtown.lotto_cardflip;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textViewList[] = new TextView[45];

    int nums[] = new int[45];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewList[0] = (TextView) findViewById(R.id.textView1);
        textViewList[1] = (TextView) findViewById(R.id.textView2);
        textViewList[2] = (TextView) findViewById(R.id.textView3);
        textViewList[3] = (TextView) findViewById(R.id.textView4);
        textViewList[4] = (TextView) findViewById(R.id.textView5);
        textViewList[5] = (TextView) findViewById(R.id.textView6);
        textViewList[6] = (TextView) findViewById(R.id.textView7);
        textViewList[7] = (TextView) findViewById(R.id.textView8);
        textViewList[8] = (TextView) findViewById(R.id.textView9);
        textViewList[9] = (TextView) findViewById(R.id.textView10);
        textViewList[10] = (TextView) findViewById(R.id.textView11);
        textViewList[11] = (TextView) findViewById(R.id.textView12);
        textViewList[12] = (TextView) findViewById(R.id.textView13);
        textViewList[13] = (TextView) findViewById(R.id.textView14);
        textViewList[14] = (TextView) findViewById(R.id.textView15);
        textViewList[15] = (TextView) findViewById(R.id.textView16);
        textViewList[16] = (TextView) findViewById(R.id.textView17);
        textViewList[17] = (TextView) findViewById(R.id.textView18);
        textViewList[18] = (TextView) findViewById(R.id.textView19);
        textViewList[19] = (TextView) findViewById(R.id.textView20);
        textViewList[20] = (TextView) findViewById(R.id.textView21);
        textViewList[21] = (TextView) findViewById(R.id.textView22);
        textViewList[22] = (TextView) findViewById(R.id.textView23);
        textViewList[23] = (TextView) findViewById(R.id.textView24);
        textViewList[24] = (TextView) findViewById(R.id.textView25);
        textViewList[25] = (TextView) findViewById(R.id.textView26);
        textViewList[26] = (TextView) findViewById(R.id.textView27);
        textViewList[27] = (TextView) findViewById(R.id.textView28);
        textViewList[28] = (TextView) findViewById(R.id.textView29);
        textViewList[29] = (TextView) findViewById(R.id.textView30);
        textViewList[30] = (TextView) findViewById(R.id.textView31);
        textViewList[31] = (TextView) findViewById(R.id.textView32);
        textViewList[32] = (TextView) findViewById(R.id.textView33);
        textViewList[33] = (TextView) findViewById(R.id.textView34);
        textViewList[34] = (TextView) findViewById(R.id.textView35);
        textViewList[35] = (TextView) findViewById(R.id.textView36);
        textViewList[36] = (TextView) findViewById(R.id.textView37);
        textViewList[37] = (TextView) findViewById(R.id.textView38);
        textViewList[38] = (TextView) findViewById(R.id.textView39);
        textViewList[39] = (TextView) findViewById(R.id.textView40);
        textViewList[40] = (TextView) findViewById(R.id.textView41);
        textViewList[41] = (TextView) findViewById(R.id.textView42);
        textViewList[42] = (TextView) findViewById(R.id.textView43);
        textViewList[43] = (TextView) findViewById(R.id.textView44);
        textViewList[44] = (TextView) findViewById(R.id.textView45);

        for (int i  = 0; i < 45; i++) {

            // 포지션 태그로 저장
            textViewList[i].setTag(i);

            // 클릭리스너 등록
            textViewList[i].setOnClickListener(this);
        }

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                // 숫자 정렬
                Arrays.sort(nums);
            }
        });
    }

    public void onClick(View v) {

        // 클릭된 뷰를 text view로 받아옴
        TextView newTextView = (TextView) v;

        // 클릭된 text view 찾기
        for (TextView tempTextView : textViewList) {

            if (tempTextView == newTextView) {

                // 위에서 저장한 버튼의 포지션을 태그로 가져옴
                int position = (Integer)v.getTag();

                newTextView.setText(nums[position]);
                newTextView.setBackgroundResource(R.drawable.card2_drawable);
            }
        }

    }
}