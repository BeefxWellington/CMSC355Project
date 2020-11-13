package com.example.my2cents;

import java.sql.Time;
import java.text.*;
import java.util.*;

public class passingModel {
    private String mainCategories;
    private String subCategories;
    private String amount;
    private String time;
    private Timestamp timeStamp;

    public passingModel(){

    }

    public passingModel(String mainCategories, String subCategories, String amount, Timestamp newTime) {
        this.mainCategories = mainCategories;
        this.subCategories = subCategories;
        this.amount = amount;
        this.timeStamp = newTime;
    }

    public passingModel(String timeDate){
        this.time = timeDate;
    }

    public String getMainCategories() {
        return mainCategories;
    }

    public void setMainCategories(String mainCategories) {
        this.mainCategories = mainCategories;
    }

    public String getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(String subCategories) {
        this.subCategories = subCategories;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }
}

