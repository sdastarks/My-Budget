package com.example.mybudget.Account;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mybudget.Chores.ChoresActivity;
import com.example.mybudget.Home.MainActivity;
import com.example.mybudget.Models.Entry;
import com.example.mybudget.R;
import com.example.mybudget.SettingsActivity;
import com.example.mybudget.WishList.WishlistActivity;
import com.example.mybudget.myDbHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends SettingsActivity {
    private static final String TAG = "AccountActivityLog";
    RecyclerView mRecyclerView;
    private List<AccountsRow> data;
    private ArrayList<Entry> entries;
    private AccountsRecyclerViewAdapter adapter;
    private TextView tvBalance;
    private int currentlog = 0;

    myDbHelper db = new myDbHelper(this, "myDb.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        tvBalance = findViewById(R.id.tvBalance);
        currentlog = db.balance();
        tvBalance.setText("Available: " + String.valueOf(currentlog) + " SEK");


        //create recycler view
        mRecyclerView = findViewById(R.id.recyclerview);

        //data = fill_with_data(0);
        data = filterEntriesByDate(fill_with_data(0),0);

        adapter = new AccountsRecyclerViewAdapter(data, getApplication());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Spinner mySpinner = (findViewById(R.id.spinner1));

        String [] labels={"Everything","Expenses","Income","On wish","Chore Money"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, labels);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(spinnerAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(AccountActivity.this, "position: "+position, Toast.LENGTH_SHORT).show();

                if(position == 0){
                    currentlog = db.balance();
                    tvBalance.setText("Available: " + String.valueOf(currentlog) + " SEK");
                    entries = fill_with_data(0);
//                    data = filterEntriesByDate(entries, 0);
                }
                else if(position == 1)
                {
                    currentlog = db.calcExpenses();
                    tvBalance.setText("Spent: " + String.valueOf(currentlog) + " SEK");
                    entries = fill_with_data(1);
                   // data = filterEntriesByDate(fill_with_data(1),0);
                }
                else if (position == 2){
                    currentlog = db.calcIncome();
                    tvBalance.setText("Income: " + String.valueOf(currentlog) + " SEK");
                    entries = fill_with_data(2);
//                    data = filterEntriesByDate(fill_with_data(2),0);
                }
                else if(position == 3){
                    currentlog = db.calcWish();
                    tvBalance.setText("On wish: " + String.valueOf(currentlog) + " SEK");
                    entries = fill_with_data(3);
//                    data = filterEntriesByDate(fill_with_data(3),0);
                }
                else if(position == 4){
                    currentlog = db.calcEarning();
                    tvBalance.setText("Chore Money: " + String.valueOf(currentlog) + " SEK");
                    entries = fill_with_data(4);
//                    data = filterEntriesByDate(fill_with_data(4),0);
                }
                //TODO Dawnie
                /*will be placed in the date filter spinner
                data = filterEntriesByDate(entries, spinnerPosition);
                adapter = new AccountsRecyclerViewAdapter(data, getApplication());
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                */
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /*
         * Method creates a pathway to the other
         * activities via a navigation bar
         */

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem =menu.getItem(2);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        Intent intent1= new Intent(AccountActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_wishlist:
                        Intent intent2 = new Intent(AccountActivity.this, WishlistActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.nav_account:
                        break;
                    case R.id.nav_chores:
                        Intent intent3 = new Intent(AccountActivity.this, ChoresActivity.class);
                        startActivity(intent3);
                        break;

                }
                return false;
            }
        });

        int num = getMonthesOfEntries();
    }

    /**
     * @param typeOfEntry a filter taken from spinner position to specify the type of entry
     * @return an arrayList contains filtered data
     */
    public ArrayList<Entry> fill_with_data(int typeOfEntry) {
        Log.v(TAG, "typeOfEntry: "+typeOfEntry);
//        List<AccountsRow> row = new ArrayList<>();
        ArrayList<Entry> entries = new ArrayList<>();

        if (typeOfEntry == 1) {
            entries = db.expensesEntries();
//            for (Entry e : entries) {
//                row.add(new AccountsRow(e.getDate(), e.getDesc(), e.getAmount(), e.getTypeOfEntry()));
//            }
        } else if (typeOfEntry == 2) {
            entries = db.incomeEntries();
//            for (Entry e : entries) {
//                row.add(new AccountsRow(e.getDate(), e.getDesc(), e.getAmount(), e.getTypeOfEntry()));
//            }
        } else if (typeOfEntry == 3) {
            entries = db.wishEntries();
//            for (Entry e : entries) {
//                row.add(new AccountsRow(e.getDate(), e.getDesc(), e.getAmount(), e.getTypeOfEntry()));
//            }
        } else if (typeOfEntry == 4) {
            entries = db.earningsEntries();
//            for (Entry e : entries) {
//                row.add(new AccountsRow(e.getDate(), e.getDesc(), e.getAmount(), e.getTypeOfEntry()));
//            }
        } else if (typeOfEntry == 0){
            entries = db.allEntries();
//            for (Entry e : entries) {
//                row.add(new AccountsRow(e.getDate(), e.getDesc(), e.getAmount(), e.getTypeOfEntry()));
//            }
        }
        return entries;
    }

    /** Sets the number of months to the spinner
     * @return number of monthes since the user logged an Entry
     * @auth DAWNIE SAFAR
     */
    public int getMonthesOfEntries() {
        int numberOfMonthes = 1;
        LocalDate date = LocalDate.now();
        ArrayList<Entry> allEntries = db.allEntries();

        for (Entry e : allEntries) {
            if (e.getDate().isBefore(date)) {
                date = e.getDate();
                numberOfMonthes++;
            }
        }
        return numberOfMonthes;
    }

    /**
     * @param dataFilteredByTypeOfEntry specifies entries filter by type of entry (income, expenses...)
     * @param spinnerPosition a date filter as per the spinner position
     * @return data filtered by data and type of entry
     * @auth Dawnie Safar
     */
    public List<AccountsRow> filterEntriesByDate(ArrayList<Entry> dataFilteredByTypeOfEntry, int spinnerPosition){
        ArrayList<Entry> allEntries = new ArrayList<>();
        List<AccountsRow> row = new ArrayList<>();

        if(spinnerPosition == 0) {
            allEntries = dataFilteredByTypeOfEntry;
            for(Entry e : allEntries)
                row.add(new AccountsRow(e.getDate(), e.getDesc(), e.getAmount(), e.getTypeOfEntry()));
        }
        else if(spinnerPosition == 1) {
            for (Entry entry : dataFilteredByTypeOfEntry) {
                if (entry.getDate().getMonth().toString() == "JANUARY") {
                    allEntries.add(entry);
                }
            }
        }
        else if (spinnerPosition == 2){
            for (Entry entry : dataFilteredByTypeOfEntry) {
                if (entry.getDate().getMonth().toString() == "FEBRUARY") {
                    allEntries.add(entry);
                }
            }
        }

        else if(spinnerPosition == 3){
            for(Entry entry : dataFilteredByTypeOfEntry){
                if(entry.getDate().getMonth().toString() == "MARCH"){
                    allEntries.add(entry);
                }
            }
        }
        else if(spinnerPosition == 4){
            for(Entry entry : dataFilteredByTypeOfEntry){
                if(entry.getDate().getMonth().toString() == "APRIL"){
                    allEntries.add(entry);
                }
            }
        }

        else if(spinnerPosition == 5){
            for(Entry entry : dataFilteredByTypeOfEntry){
                if(entry.getDate().getMonth().toString() == "MAY"){
                    allEntries.add(entry);
                }
            }
        }

        else if(spinnerPosition == 6){
            for(Entry entry : dataFilteredByTypeOfEntry){
                if(entry.getDate().getMonth().toString() == "JUNE"){
                    allEntries.add(entry);
                }
            }
        }

        else if(spinnerPosition == 7){
            for(Entry entry : dataFilteredByTypeOfEntry){
                if(entry.getDate().getMonth().toString() == "JULY"){
                    allEntries.add(entry);
                }
            }
        }

        else if(spinnerPosition == 8){
            for(Entry entry : dataFilteredByTypeOfEntry){
                if(entry.getDate().getMonth().toString() == "AUGUST"){
                    allEntries.add(entry);
                }
            }
        }

        else if(spinnerPosition == 9){
            for(Entry entry : dataFilteredByTypeOfEntry){
                if(entry.getDate().getMonth().toString() == "SEPTEMBER"){
                    allEntries.add(entry);
                }
            }
        }

        else if(spinnerPosition == 10){
            for(Entry entry : dataFilteredByTypeOfEntry){
                if(entry.getDate().getMonth().toString() == "OCTOBER"){
                    allEntries.add(entry);
                }
            }
        }

        else if(spinnerPosition ==11){
            for(Entry entry : dataFilteredByTypeOfEntry){
                if(entry.getDate().getMonth().toString() == "NOVEMBER"){
                    allEntries.add(entry);
                }
            }
        }

        else if(spinnerPosition == 12){
            for(Entry entry : dataFilteredByTypeOfEntry){
                if(entry.getDate().getMonth().toString() == "DECEMBER"){
                    allEntries.add(entry);
                }
            }
        }

        for(Entry e : allEntries){
            row.add(new AccountsRow(e.getDate(), e.getDesc(), e.getAmount(), e.getTypeOfEntry()));
        }
        return row;
    }
}



