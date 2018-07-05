package com.example.arunpandey.timertiktak;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;



public class TimerActivity extends AppCompatActivity {

    SQLoperations sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        sql = SQLoperations.getInstance(getApplicationContext());
        if(!sql.isTableCreated())
        {
            sql.createTable();
        }






    }



}
