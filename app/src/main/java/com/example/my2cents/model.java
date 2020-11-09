package com.example.my2cents;

public class model {

    private String mainCategories;
    private String subCategories;
    private String amount;
    private String time;
    private Timestamp timeStamp;

    public model() {
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
