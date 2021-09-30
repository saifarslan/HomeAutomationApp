package com.example.automatedhouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

public class active_appliances_cp extends AppCompatActivity {

    ListView listViewAppliances;
    DatabaseReference databaseReference;
    ListView powerON;

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> powerList = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> arrayPower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_appliances_cp);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        arrayAdapter = new ArrayAdapter<String>(this,R.layout.row,arrayList);
        arrayPower = new ArrayAdapter<String>(this,R.layout.green,powerList);

        listViewAppliances = findViewById(R.id.listViewAppliances);

        powerON = findViewById(R.id.on_off);


        listViewAppliances.setAdapter(arrayAdapter);

        powerON.setAdapter(arrayPower);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try{

                    String string = dataSnapshot.getValue(int.class).toString();
                    String value  = dataSnapshot.getKey();

                    if (string.equals("1")){

                        arrayList.add(value+": ");
                        powerList.add("ON");



                        arrayAdapter.notifyDataSetChanged();
                        arrayPower.notifyDataSetChanged();
                    }
                }
                catch (Exception ex)
                {
                    Log.i("Error", "Exception"+ex);
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}