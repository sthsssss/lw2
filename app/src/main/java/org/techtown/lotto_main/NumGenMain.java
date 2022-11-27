package org.techtown.lotto_main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NumGenMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.num_gen_main);

        Button basicBtn = findViewById(R.id.basicBtn);
        basicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent basicIntent = new Intent(getApplicationContext(), NumGenBasic.class);
                startActivity(basicIntent);
            }
        });

        Button boxBtn = findViewById(R.id.boxBtn);
        boxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent boxIntent = new Intent(getApplicationContext(), NumGenBox.class);
                startActivity(boxIntent);
            }
        });

        Button rouletteBtn = findViewById(R.id.rouletteBtn);
        rouletteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rouletteIntent = new Intent(getApplicationContext(), NumGenRoulette.class);
                startActivity(rouletteIntent);
            }
        });

        Button cardBtn = findViewById(R.id.cardBtn);
        cardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cardIntent = new Intent(getApplicationContext(), NumGenCard.class);
                startActivity(cardIntent);
            }
        });
    }


}