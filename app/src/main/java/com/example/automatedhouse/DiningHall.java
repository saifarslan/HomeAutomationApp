package com.example.automatedhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

public class DiningHall extends AppCompatActivity {

    Switch light,fan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_hall);
        light = findViewById(R.id.roof_light);
        fan = findViewById(R.id.roof_fan);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Automate automate = new Automate();

        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);

        light.setChecked(sharedPreferences.getBoolean("value8",false));
        fan.setChecked(sharedPreferences.getBoolean("value9",false));

        light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true)
                {
                    automate.Automated(1,"LED4");
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value8",true);
                    editor.apply();
                    light.setChecked(true);
                }
                else
                {
                    automate.Automated(0,"LED4");
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value8",false);
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
                    automate.Automated(1,"FAN4");
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value9",true);
                    editor.apply();
                    fan.setChecked(true);
                }
                else
                {
                    automate.Automated(0,"FAN4");
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value9",false);
                    editor.apply();
                    fan.setChecked(false);
                }
            }
        });
    }
}