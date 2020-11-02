package com.example.my2cents;

public class Timestamp {

    private String day;
    private String month;
    private String year;
    private String dayNum;
    private String hour;
    private String minute;
    private String seconds;

    public Timestamp (String currentDay, String currentMonth, String currentYear, String currentDayNum, String currentHour, String currentMin, String currentSec) {
        this.day = currentDay;
        this.month = currentMonth;
        this.year = currentYear;
        this.dayNum = currentDayNum;
        this.hour = currentHour;
        this.minute = currentMin;
        this.seconds = currentSec;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDayNum() {
        return dayNum;
    }

    public void setDayNum(String dayNum) {
        this.dayNum = dayNum;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getSeconds() {
        return seconds;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }
}

