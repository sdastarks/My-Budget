package com.example.mybudget.Account;

import java.time.LocalDate;

public class AccountsRow {
    public LocalDate date;
    public String title;
    public int amount;
    public int status;

    AccountsRow(LocalDate date, String title, int amount, int status) {
        this.date = date;
        this.title = title;
        this.amount = amount;
        this.status = status;
    }
}
