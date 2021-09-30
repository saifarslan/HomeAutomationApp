package com.example.automatedhouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BedRoom extends AppCompatActivity {

     Switch light,fan;

    private static  final  String Channel_ID = "AAH";
    private static  final  String Channel_Name = "Automated House";
    private static  final  String Channel_Desc = "Automated House Notifications";
    boolean display = false;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_room);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        database=FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("LDR");




        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(Channel_ID,Channel_Name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(Channel_Desc);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int check = snapshot.getValue(int.class);
                if(check == 0 )
                {
                    if(display)
                    {
                        Log.i("Battery_Voltage","LIGHT NOT WORKING");
                        display_notification ();

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        light  = (Switch) findViewById(R.id.roof_light);
        fan = (Switch) findViewById(R.id.roof_fan);

        Automate automate = new Automate();

        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);



        light.setChecked(sharedPreferences.getBoolean("value",false));
        fan.setChecked(sharedPreferences.getBoolean("value1",false));



        light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    automate.Automated(1,"LED1");
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    display = true;
                    editor.apply();
                    light.setChecked(true);



                }
                else
                {
                    automate.Automated(0,"LED1");
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    display = false;
                    light.setChecked(false);

                }
            }
        });

        fan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true)
                {
                    automate.Automated(1,"FAN1");
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value1",true);
                    editor.apply();
                    fan.setChecked(true);
                }
                else
                {
                    automate.Automated(0,"FAN1");
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value1",false);
                    editor.apply();
                    fan.setChecked(false);
                }
            }
        });
    }
    private void  display_notification (){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,Channel_ID)
                .setSmallIcon(R.drawable.ic_baseline_power_settings_new_24)
                .setContentTitle("Error Generation")
                .setContentText("Error is caused by LED 1")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager =  NotificationManagerCompat.from(this);
        notificationManager.notify(1,builder.build());

    }



}