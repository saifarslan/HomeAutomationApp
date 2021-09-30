package com.example.automatedhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

public class LivingRoom extends AppCompatActivity {

    Switch light,fan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        light = (Switch) findViewById(R.id.roof_light);
        fan = (Switch) findViewById(R.id.roof_fan);

        Automate automate = new Automate();

        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);

        light.setChecked(sharedPreferences.getBoolean("value2",false));
        fan.setChecked(sharedPreferences.getBoolean("value3",false));

        light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true)
                {
                    automate.Automated(1,"LED2");
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value2",true);
                    editor.apply();
                    light.setChecked(true);
                }
                else
                {
                    automate.Automated(0,"LED2");
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value2",false);
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
                    automate.Automated(1,"FAN2");
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value3",true);
                    editor.apply();
                    fan.setChecked(true);
                }
                else
                {
                    automate.Automated(0,"FAN2");
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value3",false);
                    editor.apply();
                    fan.setChecked(false);
                }
            }
        });
    }
}