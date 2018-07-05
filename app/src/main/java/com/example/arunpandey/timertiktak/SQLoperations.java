package com.example.arunpandey.timertiktak;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
            db.execSQL("CREATE TABLE IF NOT EXISTS " +tablename+ "(TimerDate VARCHAR,TimerName VARCHAR,TimerDuration VARCHAR)");
        } catch (Exception e) {
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
            return false;
        }
        return true;
    }

    void insert(String TimerDate,String TimerName,String TimerDuration) {

        db.execSQL("INSERT INTO " +tablename+ " VALUES('" +TimerDate+","+TimerName+","+TimerDuration+"')");

    }

    ArrayList<String> getTimerList(String tablename) {
        Cursor c = db.rawQuery("SELECT * FROM " +tablename+ "",null);

        ArrayList<String> list = new ArrayList<>();

        if(c.moveToFirst()) {
            do {
                int index1 = c.getColumnIndex("TimerDate");
                int index2 = c.getColumnIndex("TimerName");
                int index3 = c.getColumnIndex("TimerDuration");
                String note = c.getString(index1)+" "+c.getString(index2)+" "+c.getString(index3);
                //   Log.d("username",note);
                list.add(note);
            } while (c.moveToNext());
        }
        return  list;
    }


}
