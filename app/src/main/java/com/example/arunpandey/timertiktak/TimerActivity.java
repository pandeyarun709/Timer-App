package com.example.arunpandey.timertiktak;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.os.Handler;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TimerActivity extends AppCompatActivity {

    SQLoperations sql;

    private static final long Start_Time = 0;

    private TextView textViewCountDown;
    private Button start;
    private Button reset;
    private Boolean isTimerRunning = false;
    private CountDownTimer countDownTimer;
    private long timeleft = 0 ;
    private  String timeView;

    private String currentDateandTime;
    private String timerName = "Timer";
    private String timerDuration;
    private boolean check = true;

    private  SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        sql = SQLoperations.getInstance(getApplicationContext());
        if(!sql.isTableCreated())
        {
            Log.d("table" , "Table created");
            sql.createTable();
        }
       // sql.createTable();

        textViewCountDown =  findViewById(R.id.textView);
        start = findViewById(R.id.button2);
        reset = findViewById(R.id.button3);
        seekBar = findViewById(R.id.seekBar);

        currentDateandTime =  getTimerSetOnDate(); //get date

      //  Toast.makeText(getApplicationContext(),currentDateandTime,Toast.LENGTH_SHORT).show();

        /*############################### start button ##################################*/

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isTimerRunning)
                {
                    pauseTimer();
                }else{
                    if(timeleft == 0)
                    {
                        Toast.makeText(getApplicationContext(),"First set the timer !", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        startTimer();
                        if(check == true)
                        {
                            insertData(); // inserting data in db
                            check = false;
                        }

                    }

                }
            }
        });

        /*############################### start button ##################################*/

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  resetTimer();
            }
        });
        updateCountDownText();

        /*################### seek bar ###############################################*/

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
               // Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();
                timeleft = 1000 * progress;
                updateCountDownText();

                timerDuration = timeView ; //get timeduration


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
               // Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                //Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();

               currentDateandTime =  getTimerSetOnDate();


                openDialogBox(); //dialoge box




            }
        });

        /*#############-x-x-x-x-x-x-x- seek bar end -x-x-x-x-x-x-x-###############*/

    }


    /*###################-x-x- on create function end-x-x- ###################################*/

    /*-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x--x-xx-x-x-x-x-x-x-*/

    /*################################## CountDown timer function#######################################*/
    private void startTimer()
    {
        countDownTimer = new CountDownTimer(timeleft, 1000) {
            @Override
            public void onTick(long l) {
                timeleft = l;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                timerSound();
                isTimerRunning = false;
                start.setText("START");
                resetseek();

                check =true;

            }
        }.start();

        isTimerRunning = true;
        start.setText("PAUSE");

    }

    private void pauseTimer()
    {
        countDownTimer.cancel();
        isTimerRunning = false;
        start.setText("START");



    }

    private void resetTimer()
    {
        timeleft = Start_Time;
        updateCountDownText();
        start.setText("START");
        countDownTimer.cancel();
        isTimerRunning = false;
        resetseek();
        timerSound();
        check =true;

    }

    private  void updateCountDownText()
    {
        int minutes =  (int) (timeleft/1000)/60;
        int seconds = (int) (timeleft/1000) % 60;

         timeView = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);

        textViewCountDown.setText(timeView);

    }

    /*##################### reset seek bar #######################3*/

    public void resetseek(){
        if(seekBar.getProgress() > 0)
        {
            seekBar.setProgress(0);
        }
    }
/*################################## CountDown timer function#######################################################*/


/*--------------------------- Timer set date  ------------*/

    private String getTimerSetOnDate()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate =  mdformat.format(calendar.getTime());


         return strDate;
    }


    /*-------------------------------- dialog box---------------*/

    private void openDialogBox() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_box, null);
        dialogBuilder.setView(dialogView);

        final EditText add = (EditText) dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle("Tik Tak Timer");
        dialogBuilder.setMessage("Enter Timer Name");
        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

               timerName = add.getText().toString();

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    /*------------------------- insert data -----------------*/

    private  void insertData()
    {
        sql.insert(currentDateandTime,timerName,timerDuration);

    }

    /*-------------------------- sound function ------------------*/

    private  void timerSound()
    {
        final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.tone);
        mp.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mp.release();

            }
        },4000);


    }





}
