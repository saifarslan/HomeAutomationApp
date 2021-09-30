
package com.example.automatedhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void set_rooms(View view) {
        Intent intent  = new Intent(Menu.this,Rooms.class);
        startActivity(intent);

    }

    public void Reminder(View view) {
        Intent intent  = new Intent(Menu.this,ReminderScreen.class);
        startActivity(intent);

    }


    public void Active_Appliances(View view) {
        Intent intent  = new Intent(Menu.this,active_appliances_cp.class);
        startActivity(intent);
    }

    public void consumption_electricity(View view) {
        Intent intent  = new Intent(Menu.this,ElectricityConsumption.class);
        startActivity(intent);

    }

    public void Voice(View view) {
        Intent intent = new Intent(Menu.this,VoiceRecognition.class);
        startActivity(intent);
    }
}