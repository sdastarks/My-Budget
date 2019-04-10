package com.example.mybudget.Models;

import android.icu.util.LocaleData;

public class Entry {

    private int enteryId;
    private LocaleData date;
    private float amount;
    private Type typeOfEntry;

    public Entry(){}
    public Entry(int enteryId, LocaleData date, float amount, Type typeOfEntry){
        this.enteryId = enteryId;
        this.date = date;
        this.amount = amount;
        this.typeOfEntry = typeOfEntry;
    }

    public int getEnteryId() {
        return enteryId;
    }

    public LocaleData getDate() {
        return date;
    }

    public float getAmount() {
        return amount;
    }

    public Type getTypeOfEntry() {
        return typeOfEntry;
    }

    public void setEnteryId(int enteryId) {
        this.enteryId = enteryId;
    }

    public void setDate(LocaleData date) {
        this.date = date;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setTypeOfEntry(Type typeOfEntry) {
        this.typeOfEntry = typeOfEntry;
    }
}
