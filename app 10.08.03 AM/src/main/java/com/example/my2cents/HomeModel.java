package com.example.my2cents;

public class HomeModel {


    private String title;
    private String firstTitle;
    private String secondTitle;
    private String thirdTitle;
    private String firstAmount;
    private String secondAmount;
    private String thirdAmount;
    private String firstDate;
    private String secondDate;
    private String thirdDate;

    public HomeModel(String title, String firstTitle, String secondTitle, String thirdTitle, String firstAmount, String secondAmount, String thirdAmount, String firstDate, String secondDate, String thirdDate) {
        this.title = title;
        this.firstTitle = firstTitle;
        this.secondTitle = secondTitle;
        this.thirdTitle = thirdTitle;
        this.firstAmount = firstAmount;
        this.secondAmount = secondAmount;
        this.thirdAmount = thirdAmount;
        this.firstDate = firstDate;
        this.secondDate = secondDate;
        this.thirdDate = thirdDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstTitle() {
        return firstTitle;
    }

    public void setFirstTitle(String firstTitle) {
        this.firstTitle = firstTitle;
    }

    public String getSecondTitle() {
        return secondTitle;
    }

    public void setSecondTitle(String secondTitle) {
        this.secondTitle = secondTitle;
    }

    public String getThirdTitle() {
        return thirdTitle;
    }

    public void setThirdTitle(String thirdTitle) {
        this.thirdTitle = thirdTitle;
    }

    public String getFirstAmount() {
        return firstAmount;
    }

    public void setFirstAmount(String firstAmount) {
        this.firstAmount = firstAmount;
    }

    public String getSecondAmount() {
        return secondAmount;
    }

    public void setSecondAmount(String secondAmount) {
        this.secondAmount = secondAmount;
    }

    public String getThirdAmount() {
        return thirdAmount;
    }

    public void setThirdAmount(String thirdAmount) {
        this.thirdAmount = thirdAmount;
    }

    public String getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(String firstDate) {
        this.firstDate = firstDate;
    }

    public String getSecondDate() {
        return secondDate;
    }

    public void setSecondDate(String secondDate) {
        this.secondDate = secondDate;
    }

    public String getThirdDate() {
        return thirdDate;
    }

    public void setThirdDate(String thirdDate) {
        this.thirdDate = thirdDate;
    }
}
