package com.example.mybudget.Models;

import java.time.LocalDate;

/**
 * Model holding the info regarding entry/records
 * @author Dawnie Safar
 */
public class Entry {

    private int enteryId;
    private LocalDate date;
    private float amount;
    // expenditures = 0; income = 1; spendOnWish = 2; earnedFromChore = 3;
    private int typeOfEntry;
    private String desc;

    public Entry(){}

    public Entry(int enteryId, LocalDate date, float amount, int typeOfEntry, String desc){
        this.enteryId = enteryId;
        this.date = date;
        this.amount = amount;
        this.typeOfEntry = typeOfEntry;
        this.desc = desc;

    }

    public int getEnteryId() {
        return enteryId;
    }

    public LocalDate getDate() {
        return date;
    }

    public float getAmount() {
        return amount;
    }

    public int getTypeOfEntry() {
        return typeOfEntry;
    }

    public String getDesc(){return desc; }

    public void setEnteryId(int enteryId) {
        this.enteryId = enteryId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setTypeOfEntry(int typeOfEntry) {
        this.typeOfEntry = typeOfEntry;
    }

    public void setDesc(String desc){ this.desc = desc; }
}
