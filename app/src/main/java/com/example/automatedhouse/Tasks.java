package com.example.automatedhouse;

import java.util.Calendar;

public class Tasks {
    public static String Greetings(){
        String greetings = "";
        Calendar calendar = Calendar.getInstance();
        int time = calendar.get(Calendar.HOUR_OF_DAY);
        if(time >= 0 && time <12)
        {
            greetings = "Good Morning ";
        }
        else if(time >= 12 &&  time < 16){
            greetings = "Good Afternoon ";
        }
        else if(time >=16 && time <24)
        {
            greetings= "Good Evening ";
        }

        return greetings;
    }
}
