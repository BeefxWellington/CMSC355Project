package com.example.my2cents;

public class Log {
    private String month;
    private String day;
    private String title;
    private String category;
    private String type;
    private String amount;
    private String balance;

    public Log(String month, String day, String title, String category, String type, String amount, String balance){
        this.month = month;
        this.day = day;
        this.title = title;
        this.category = category;
        this.type = type;
        this.amount = amount;
        this.balance = balance;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
