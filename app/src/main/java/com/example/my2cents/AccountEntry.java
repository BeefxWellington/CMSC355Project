package com.example.my2cents;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountEntry {

    FirebaseDatabase rootNode;
    DatabaseReference refNode;

    private String typeSpinner;
    private String cateSpinner;
    private String amount;

    public AccountEntry() {

        rootNode = FirebaseDatabase.getInstance();
        refNode = rootNode.getReference();
    }

    public AccountEntry(String type, String category, String montenaryAmount) {
        this.typeSpinner = type;
        this.cateSpinner = category;
        this.amount = montenaryAmount;
    }

    public DatabaseReference getRefNode() {
        return refNode;
    }

    public FirebaseDatabase getRootNode() {
        return rootNode;
    }

    public String getAmount() {
        return amount;
    }

    public String getCateSpinner() {
        return cateSpinner;
    }

    public String getTypeSpinner() {
        return typeSpinner;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCateSpinner(String cateSpinner) {
        this.cateSpinner = cateSpinner;
    }

    public void setRefNode(DatabaseReference refNode) {
        this.refNode = refNode;
    }

    public void setRootNode(FirebaseDatabase rootNode) {
        this.rootNode = rootNode;
    }

    public void setTypeSpinner(String typeSpinner) {
        this.typeSpinner = typeSpinner;
    }
}
