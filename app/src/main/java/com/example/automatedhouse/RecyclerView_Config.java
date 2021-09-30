package com.example.automatedhouse;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerView_Config {

    private Context mcontext;
    private ReminderAdapter reminderAdapter;
    private String R_Date,R_Time,R_Repeat= "Repeat ON";
    private String format;

    public void setConfig(RecyclerView recyclerView, Context context,List<Reminder> reminders,List<String> keys){
        mcontext = context;
        reminderAdapter =  new ReminderAdapter(reminders,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(reminderAdapter);

    }

    class ReminderItemView extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView date;
        private TextView repeat;
        private TextView time;
        private TextView repitition;
        private String key;
        private ImageButton editBtn, deleteBtn;

        public ReminderItemView (ViewGroup parent)
        {
            super(LayoutInflater.from(mcontext).inflate(R.layout.reminder_items,parent,false));
            title = (TextView) itemView.findViewById(R.id.recycle_title);
            date = (TextView) itemView.findViewById(R.id.recycle_date);
            repeat = (TextView) itemView.findViewById(R.id.recycle_repeat);
            time = (TextView) itemView.findViewById(R.id.recycle_time);
            repitition = (TextView) itemView.findViewById(R.id.recycle_repeatition);
            editBtn = (ImageButton) itemView.findViewById(R.id.edit);
            deleteBtn = (ImageButton) itemView.findViewById(R.id.delete);


        }
        public void bind(Reminder reminder, String key){

            title.setText(reminder.getDetail());
            date.setText(reminder.getDate());
            time.setText(reminder.getTime());
            repeat.setText(reminder.getRepeat());
            repitition.setText(reminder.getInterval());
            this.key = key;

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogPlus dialog = DialogPlus.newDialog(mcontext)
                            .setGravity(Gravity.CENTER)
                            .setMargin(50,0,50,0)
                            .setContentHolder(new ViewHolder(R.layout.content))
                            .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                            .create();
                    View  holderView = (RelativeLayout) dialog.getHolderView();

                    Button date  = (Button) holderView.findViewById(R.id.date_btn);
                    Button time  = (Button) holderView.findViewById(R.id.time_btn);
                    Switch repeat = (Switch) holderView.findViewById(R.id.switch_btn);
                    Button update  = (Button) holderView.findViewById(R.id.update);


                    date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mcontext,"Date CLicked",Toast.LENGTH_SHORT).show();
                            Calendar calendar = Calendar.getInstance();
                            int YEAR = calendar.get(Calendar.YEAR);
                            int MONTH = calendar.get(Calendar.MONTH);
                            int DATE = calendar.get(Calendar.DATE);

                            DatePickerDialog datePickerDialog = new DatePickerDialog(mcontext, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                                    Calendar current  = Calendar.getInstance();
                                    Calendar calendar1 = Calendar.getInstance();
                                    calendar1.set(Calendar.YEAR, year);
                                    calendar1.set(Calendar.MONTH, month);
                                    calendar1.set(Calendar.DATE, date);
                                    String dateText = DateFormat.format("dd/MM/yyyy", calendar1).toString();
                                    R_Date = dateText;

                                }
                            }, YEAR, MONTH, DATE);

                            long now = calendar.getTimeInMillis();
                            datePickerDialog.getDatePicker().setMinDate(now);
                            datePickerDialog.show();
                        }
                    });
                    time.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar calendar = Calendar.getInstance();
                            int  CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                            int  CalendarMinute = calendar.get(Calendar.MINUTE);
                            boolean is24HourFormat = DateFormat.is24HourFormat(mcontext);

                            TimePickerDialog timePickerDialog = new TimePickerDialog(mcontext, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                    if (hourOfDay == 0) {

                                        hourOfDay += 12;

                                        format = "AM";
                                    }
                                    else if (hourOfDay == 12) {

                                        format = "PM";

                                    }
                                    else if (hourOfDay > 12) {

                                        hourOfDay -= 12;

                                        format = "PM";

                                    }
                                    else {

                                        format = "AM";
                                    }
                                    String dateText = hourOfDay+":"+minute+" "+format;
                                    R_Time=dateText;
                                    Toast.makeText(mcontext,"Time Set to "+R_Time,Toast.LENGTH_SHORT).show();
                                }
                            }, CalendarHour , CalendarMinute , false );

                            timePickerDialog.show();
                        }
                    });
                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Map<String,Object> map = new HashMap<>();
                            map.put("date",R_Date);
                            map.put("time",R_Time);
                            map.put("repeat",R_Repeat);

                            Toast.makeText(mcontext," "+R_Date+R_Time+R_Repeat,Toast.LENGTH_SHORT).show();

                           if(R_Date != null && !R_Date.isEmpty() && R_Time != null && !R_Time.isEmpty() && R_Repeat != null && !R_Repeat.isEmpty())
                            {
                                FirebaseDatabase.getInstance().getReference()
                                        .child("Reminder")
                                        .child(key)
                                        .updateChildren(map)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                dialog.dismiss();
                                                Toast.makeText(mcontext,"Update Completed Successfully",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                            else
                            {
                                Toast.makeText(mcontext,"Cant Update Data ",Toast.LENGTH_SHORT).show();
                            }






                        }
                    });
                    repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            Toast.makeText(mcontext,"Repetition is "+(isChecked ? "On" : "Off" ),Toast.LENGTH_SHORT).show();

                            if(isChecked == true)
                            {
                                R_Repeat = "Repeat ON";
                            }
                            else
                            {
                                R_Repeat = "Repeat Off";
                            }
                        }
                    });






                    dialog.show();

                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Reminder")
                            .child(key)
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                }
            });
        }
    }

    class ReminderAdapter extends RecyclerView.Adapter<ReminderItemView>{
        private List<Reminder> reminderList;
        private List<String> mKeys;

        public ReminderAdapter(List<Reminder> reminderList, List<String> mKeys) {
            this.reminderList = reminderList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public ReminderItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ReminderItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ReminderItemView holder, int position) {
            holder.bind(reminderList.get(position),mKeys.get(position));

        }

        @Override
        public int getItemCount() {
            return reminderList.size();
        }
    }




}
