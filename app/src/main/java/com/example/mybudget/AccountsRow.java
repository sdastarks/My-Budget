package com.example.mybudget;

import java.util.Date;

public class AccountsRow {
    public Date date;
    public String title;
    public int amount;
    public String status;

    AccountsRow(Date date, String title, int amount, String status) {
        this.date = date;
        this.title = title;
        this.amount = amount;
        this.status = status;
    }
}
