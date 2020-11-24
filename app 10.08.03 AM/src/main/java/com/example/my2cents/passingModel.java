package com.example.my2cents;

public class passingModel {

    private String ID;
    private String mainCategories;
    private String subCategories;
    private String amount;


    public passingModel(){

    }

    public passingModel(String ID, String mainCategories, String subCategories, String amount) {
        this.ID = ID;
        this.mainCategories = mainCategories;
        this.subCategories = subCategories;
        this.amount = amount;
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}

