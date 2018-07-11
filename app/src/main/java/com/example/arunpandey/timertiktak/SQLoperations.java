package com.example.arunpandey.timertiktak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SQLoperations {

    final private String dataBase = "Timer";
    private String tablename = "TimeInfo";


    private static SQLoperations sqLoperations;
    Context context;
    SQLiteDatabase db;



    private SQLoperations(Context context)
    {
        this.context = context;
        db = context.openOrCreateDatabase(dataBase,MODE_PRIVATE,null);
        Log.d("table", "database created");
    }

   public static SQLoperations getInstance(Context context)
    {
        if(sqLoperations == null)
        {
            sqLoperations = new SQLoperations(context);

        }
        return sqLoperations;
    }



    void createTable()
    {
        try {
           // Log.d("table" , "created");
            db.execSQL("CREATE TABLE IF NOT EXISTS " +tablename+ "(TimerDate VARCHAR,TimerName VARCHAR,TimerDuration VARCHAR);");

            Log.d("table" , "created");

        } catch (Exception e) {
            Log.d("table" , "Not created");
            throw e;
        }
    }

    Boolean isTableCreated()
    {
        Cursor c = null;
        /* get cursor on it */
        try
        {
            c = db.query(tablename, null,
                    null, null, null, null, null);
        }
        catch (Exception e) {
            /* fail */
            Log.d("table","not created...false return");
            return false;
        }
        return true;
    }

    void insert(String TimerDate,String TimerName,String TimerDuration) {

        Log.d("table" ,"insert function call");

       // db.execSQL("INSERT INTO "+tablename+" VALUES ('"+TimerDate/*+','+TimerName+','+TimerDuration*/+"');");

        ContentValues contentValues = new ContentValues();
        contentValues.put("TimerDate",TimerDate);
        contentValues.put("TimerName",TimerName);
        contentValues.put("TimerDuration",TimerDuration);
        long result = db.insert(tablename,null ,contentValues);
        if(result == -1)
            Log.d("Value","no  vaule  entered");
        else
            Log.d("Value"," vaule  entered");

    }

    ArrayList<String> getTimerList() {
        Cursor c = db.rawQuery("SELECT * FROM " +tablename+ "",null);

        ArrayList<String> list = new ArrayList<>();

        if(c.moveToFirst()) {
            do {
                int index1 = c.getColumnIndex("TimerDate");
                int index2 = c.getColumnIndex("TimerName");
                int index3 = c.getColumnIndex("TimerDuration");
                String time = c.getString(index2)+" Date:"+c.getString(index1)+" Duration: "+c.getString(index3);
                //   Log.d("username",note);
                list.add(time);
            } while (c.moveToNext());
        }
        return  list;
    }


}
