package com.example.automatedhouse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase myDatabase;
    private DatabaseReference reference;
    private DatabaseReference myreference;
    private List <Reminder> reminders = new ArrayList<>();
    public interface Datastatus{
        void DataIsloaded (List<Reminder> reminders,List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();

    }

    public FirebaseDatabaseHelper() {
        myDatabase  = FirebaseDatabase.getInstance();
        reference =  myDatabase.getReference("Reminder");


    }
    public void readReminder(final Datastatus datastatus){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reminders.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : snapshot.getChildren())
                {
                    keys.add(keyNode.getKey());
                    Reminder reminder = keyNode.getValue(Reminder.class);
                    reminders.add(reminder);
                }
                datastatus.DataIsloaded(reminders,keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}
