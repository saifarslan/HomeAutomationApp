package com.example.automatedhouse;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Automate {

    public void Automated(int value,String path) {

        DatabaseReference reference;

        reference = FirebaseDatabase.getInstance().getReference(path);
        reference.setValue(value);

    }
}
