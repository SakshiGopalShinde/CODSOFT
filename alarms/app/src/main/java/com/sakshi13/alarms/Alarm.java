package com.sakshi13.alarms;

import java.io.Serializable;

public class Alarm implements Serializable {
    private int hour;
    private int minute;
    private int day;
    private int month;
    private int year;
    private String note;

    public Alarm(int hour, int minute, int day, int month, int year, String note) {
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.month = month;
        this.year = year;
        this.note = note;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getNote() {
        return note;
    }
}
