package com.example.arunpandey.timertiktak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
        listView = findViewById(R.id.listview);
       // list = sql.getTimerList();


        displayTimerList();





    }

    void displayTimerList()
    {
        list = sql.getTimerList();

        if(!list.isEmpty())
        {
            //arrayAdapter = new ArrayAdapter<>(TimerList.this,android.R.layout.simple_list_item_1,list);
            arrayAdapter = new ArrayAdapter<>(TimerList.this,android.R.layout.simple_list_item_1,list);
            listView.setAdapter(arrayAdapter);

        }else{
            Toast.makeText(getApplicationContext(),"List is empty",Toast.LENGTH_LONG).show();
        }
    }
}
