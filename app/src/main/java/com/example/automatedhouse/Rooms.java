package com.example.automatedhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Rooms extends AppCompatActivity {
    Switch main_gate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        main_gate = findViewById(R.id.main_gate);
        Automate automate = new Automate();
        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);

        main_gate.setChecked(sharedPreferences.getBoolean("value6",false));
        main_gate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true)
                {
                    automate.Automated(1,"Door");
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value6",true);
                    editor.apply();
                    main_gate.setChecked(true);
                }
                else
                {
                    automate.Automated(0,"Door");
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value6",false);
                    editor.apply();
                    main_gate.setChecked(false);

                }
            }
        });
        
    }

    public void set_bedroom(View view) {
        Intent intent = new Intent(Rooms.this,BedRoom.class);
        startActivity(intent);
    }

    public void set_livingRoom(View view) {
        Intent intent = new Intent(Rooms.this,LivingRoom.class);
        startActivity(intent);
    }

    public void set_study_room(View view) {
        Intent intent = new Intent(Rooms.this,StudyRoom.class);
        startActivity(intent);
    }

    public void set_dining_hall(View view) {
        Intent intent = new Intent(Rooms.this,DiningHall.class);
        startActivity(intent);
    }
}