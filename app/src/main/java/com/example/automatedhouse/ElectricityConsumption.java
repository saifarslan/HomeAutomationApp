package com.example.automatedhouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ElectricityConsumption extends AppCompatActivity {

    TextView textView;
    DatabaseReference reference;
    String electricity;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_consumption);
        textView = (TextView) findViewById(R.id.current_consumption);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //textView.setText(""+electricity);

        database=FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("Load_Value");

        //reference = FirebaseDatabase.getInstance().getReference();

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String  myvalue = snapshot.getValue(int.class).toString();
                //Double myvalue = dataSnapshot.getValue(Double.class);
                //maxScoreLevel=Integer.parseInt(dataSnapshot.child(SCORE).getValue(String.class))
                //electricity = (String) snapshot.child("LED1:").getValue();
                textView.setText(myvalue +" |AMP|");
                //Log.i("Battery_Voltage","Voltage " +myvalue);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}