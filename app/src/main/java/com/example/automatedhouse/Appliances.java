package com.example.automatedhouse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Appliances extends AppCompatActivity {

    TextView text;
    ListView listView;
    DatabaseReference reference;
    String container;
    String[] names;
    String[] values;
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> arrayPower;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> power = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliances);
        listView = (ListView) findViewById(R.id.To_show);
        listView.setAdapter(arrayPower);
        
        reference = FirebaseDatabase.getInstance().getReference();



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);




        /*for(int i=0; i<arrayList.size(); i++)
        {
            names[i]=arrayList.get(i);
        }
        for (int j=0; j<power.size();j++)
        {
            values[j] = power.get(j);
        }*/



        Myadapter myadapter = new Myadapter(this,names,values);
        listView.setAdapter(myadapter);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getKey();
                String valid = snapshot.getValue(int.class).toString();
                if(valid.equals("1"))
                {
                    arrayList.add(name);
                    power.add("ON");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
    class Myadapter extends ArrayAdapter<String>{

        Context context;
        String [] names;
        String [] values;

        Myadapter (Context c, String [] names,String [] values){
            super(c,R.layout.appliance_items,R.id.name, names);
            this.context = c;
            this.names = names;
            this.values = values;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.appliance_items,parent,false);
            TextView name = row.findViewById(R.id.name);
            TextView value = row.findViewById(R.id.value);

            name.setText(names[position]);
            value.setText(values[position]);

            return row;
        }
    }

}