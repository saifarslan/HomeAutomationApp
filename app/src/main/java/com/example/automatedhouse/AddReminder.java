package com.example.automatedhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class AddReminder extends AppCompatActivity implements Repeatition_Dialog.Dialoglistner {




    @Override
    public void applyText(String repeat) {
        R_Repeatition = repeat;
        Toast.makeText(getApplicationContext()," "+R_Repeatition,Toast.LENGTH_SHORT).show();
    }
    public String R_Date,R_Time,R_Repeat= "Repeat ON",R_Discription,R_Repeatition;
    private Switch repeat;
    String format;
    private EditText Discription;
    private Button submit;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    DatabaseReference reff;
    private Calendar calendarx;
    private TimePicker timePicker;
    private int hour=0, min=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        repeat = (Switch) findViewById(R.id.repeat_switch);
        Discription = (EditText) findViewById(R.id.description);
        submit = (Button) findViewById(R.id.submit_area);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        reff = FirebaseDatabase.getInstance().getReference().child("Reminder");
        createNotificationChannel();

        repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Toast.makeText(getApplicationContext(),"Repetition is "+(isChecked ? "On" : "Off" ),Toast.LENGTH_SHORT).show();

                if(isChecked == true)
                {
                    R_Repeat = "Repeat ON";
                }
                else
                {
                    R_Repeat = "Repeat Off";
                }

            }

        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 R_Discription = Discription.getText().toString().trim();
                 insertreminder(R_Discription,R_Date,R_Time,R_Repeat,R_Repeatition);
                 setAlarm();
            }
        });

    }




    private void insertreminder(String dis,String date,String time,String repeat,String repeatition) {
        if(dis != null && !dis.isEmpty() && time != null && !time.isEmpty() && repeat != null && !repeat.isEmpty() && repeatition != null && !repeatition.isEmpty() )
        {
            reff .child("Reminder");
            Reminder reminder = new Reminder(dis,date,time,repeat,repeatition);
            reff.push().setValue(reminder);
            Toast.makeText(getApplicationContext(),"Data Inserted",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Reminder Not Added",Toast.LENGTH_SHORT).show();
        }

    }


    private void setAlarm() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,alarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendarx.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
       // Toast.makeText(this,"Alarm Set",Toast.LENGTH_SHORT).show();
    }

    public void set_date(View view) {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DATE, date);
                String dateText = DateFormat.format("dd/MM/yyyy", calendar).toString();
                R_Date=dateText;
                Toast.makeText(getApplicationContext(),"Time Set to "+R_Date,Toast.LENGTH_SHORT).show();
            }
        }, YEAR, MONTH, DATE);
        long now = calendar.getTimeInMillis();
        datePickerDialog.getDatePicker().setMinDate(now);
        datePickerDialog.show();
    }

    public void set_time(View view) {
        Calendar calendar = Calendar.getInstance();
        int  CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
        int  CalendarMinute = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                if (hourOfDay == 0) {
                    hourOfDay += 12;
                    format = "AM";
                }
                else if (hourOfDay == 12) {
                    format = "PM";
                }
                else if (hourOfDay > 12) {
                    hourOfDay -= 12;
                    format = "PM";
                }
                else {
                    format = "AM";
                }
                String dateText = hourOfDay+":"+minute+" "+format;
                R_Time=dateText;
                hour=hourOfDay;
                min=minute;
                Toast.makeText(getApplicationContext(),"Time Set to "+R_Time,Toast.LENGTH_SHORT).show();
            }
        }, CalendarHour , CalendarMinute , false );

        calendarx = Calendar.getInstance();
        calendarx.set(Calendar.HOUR_OF_DAY, hour);
        calendarx.set(Calendar.MINUTE, min);
        calendarx.set(Calendar.SECOND,0);
        calendarx.set(Calendar.MILLISECOND,0);

        timePickerDialog.show();
    }

    public void set_repetition(View view) {
        Repeatition_Dialog dialog = new Repeatition_Dialog();
        dialog.show(getSupportFragmentManager(), "dialog");

    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "AlarmReminderChannel";
            String description = "Channel for Alarm Manager";
            int  importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel =  new NotificationChannel("testingapp",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager  = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}