package com.example.arunpandey.timertiktak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TimerList extends AppCompatActivity {
    ListView listView;
    SQLoperations sqLoperations ;

    ArrayList<String> list;
    ArrayAdapter<String> arrayAdapter;
    SQLoperations sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_list);

        sql = SQLoperations.getInstance(getApplicationContext());





    }
}
