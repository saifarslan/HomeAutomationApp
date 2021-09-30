package com.example.automatedhouse;

public class Reminder {
    private String detail;
    private String date;
    private String time;
    private String repeat;
    private String interval;

    public Reminder() {
    }

    public Reminder(String detail, String date, String time, String repeat, String interval) {
        this.detail = detail;
        this.date = date;
        this.time = time;
        this.repeat = repeat;
        this.interval = interval;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }
}
