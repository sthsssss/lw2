package org.techtown.lottoworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CheckWinningActivity extends AppCompatActivity {

    Button qrCheckButton,selfInputButton,purchaseHistoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_winning);
        qrCheckButton = findViewById(R.id.qrCheckButton);
        selfInputButton = findViewById(R.id.selfInputButton);
        purchaseHistoryButton = findViewById(R.id.purchaseHistoryButton);

        qrCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QrCheckingActivity.class);
                startActivity(intent);
            }
        });

        selfInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelfInputActivity.class);
                startActivity(intent);
            }
        });

        purchaseHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PurchaseHistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}