package com.example.automatedhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

public class StudyRoom extends AppCompatActivity {

    Switch light,fan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_room);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        light = (Switch)findViewById(R.id.roof_light);
        fan = (Switch) findViewById(R.id.roof_fan);

        Automate automate = new Automate();

        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);

        light.setChecked(sharedPreferences.getBoolean("value4",false));
        fan.setChecked(sharedPreferences.getBoolean("value5",false));


        light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true)
                {
                    automate.Automated(1,"LED3");
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value4",true);
                    editor.apply();
                    light.setChecked(true);
                }
                else
                {
                    automate.Automated(0,"LED3");
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value4",false);
                    editor.apply();
                    light.setChecked(false);
                }
            }
        });

        fan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true)
                {
                    automate.Automated(1,"FAN3");
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value5",true);
                    editor.apply();
                    fan.setChecked(true);
                }
                else
                {
                    automate.Automated(0,"FAN3");
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value5",false);
                    editor.apply();
                    fan.setChecked(false);
                }
            }
        });
    }
}