package com.example.mybudget.Models;

import java.util.Date;

public class Entry {

    private int enteryId;
    private Date date;
    private float amount;
    // expenditures = 0; income = 1; spendOnWish = 2; earnedFromChore = 3;
    private int typeOfEntry;

    public Entry(){}

    public Entry(int enteryId, Date date, float amount, int typeOfEntry){
        this.enteryId = enteryId;
        this.date = date;
        this.amount = amount;
        this.typeOfEntry = typeOfEntry;
    }

    public int getEnteryId() {
        return enteryId;
    }

    public Date getDate() {
        return date;
    }

    public float getAmount() {
        return amount;
    }

    public int getTypeOfEntry() {
        return typeOfEntry;
    }

    public void setEnteryId(int enteryId) {
        this.enteryId = enteryId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setTypeOfEntry(int typeOfEntry) {
        this.typeOfEntry = typeOfEntry;
    }
}
