package com.example.arunpandey.timertiktak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button startTimer;
    private Button checkPreviousTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startTimer =  findViewById(R.id.button);
        checkPreviousTimer =  findViewById(R.id.button1);

        startTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TimerActivity.class);
                startActivity(intent);
            }
        });

        checkPreviousTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TimerList.class);
                startActivity(intent);
            }
        });
    }
}
