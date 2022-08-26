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


    int nums[] = new int[45];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        TextView[] textViews = new TextView[45];
        for (int i = 0; i < textViews.length; i++) {
            String textViewId = "textView" + (i + 1);
            textViews[i] = findViewById(getResources().getIdentifier(textViewId, "id", getPackageName()));
        }

        for (TextView textViewId : textViews) {
            textViewId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView result = findViewById(view.getId());
                    Toast.makeText(MainActivity.this, "클릭 : " + result.getText().toString(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

}