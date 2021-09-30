package com.example.automatedhouse;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class ReminderScreen extends AppCompatActivity {

    RecyclerView recyclerView;


    //ImageButton delete_btn,edit_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView = (RecyclerView) findViewById(R.id.list);

        //delete_btn = (ImageButton) findViewById(R.id.delete);


        new FirebaseDatabaseHelper().readReminder(new FirebaseDatabaseHelper.Datastatus() {
            @Override
            public void DataIsloaded(List<Reminder> reminders, List<String> keys) {
                new RecyclerView_Config().setConfig(recyclerView,ReminderScreen.this,reminders,keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });






    }

    public void set_reminder(View view) {
        Intent reminderintent = new Intent(ReminderScreen.this,AddReminder.class);
        startActivity(reminderintent);
    }
   /* public void set_delete (View view){
        Toast.makeText(getApplicationContext(),"delete button",Toast.LENGTH_SHORT).show();
    }
    public void set_edit (View view){
        Toast.makeText(getApplicationContext(),"edit button",Toast.LENGTH_SHORT).show();
    }*/



}