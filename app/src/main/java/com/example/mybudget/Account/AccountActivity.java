package com.example.mybudget.Account;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;

import com.example.mybudget.Chores.ChoresActivity;
import com.example.mybudget.Home.MainActivity;
import com.example.mybudget.Models.Entry;
import com.example.mybudget.R;
import com.example.mybudget.SettingsActivity;
import com.example.mybudget.WishList.WishlistActivity;
import com.example.mybudget.myDbHelper;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends SettingsActivity {
    private static final String TAG = "AccountActivityLog";
    RecyclerView mRecyclerView;
    private List<AccountsRow> data;
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

        data = fill_with_data(0);

        adapter = new AccountsRecyclerViewAdapter(data, getApplication());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Spinner mySpinner = (findViewById(R.id.spinner1));

        String [] labels={"Everything","Expenses","Income","On wish","Chore Money"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, labels);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mySpinner.setAdapter(spinnerAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(AccountActivity.this, "position: "+position, Toast.LENGTH_SHORT).show();

                if(position == 0){
                    currentlog = db.balance();
                    tvBalance.setText("Available: " + String.valueOf(currentlog) + " SEK");
                }
                else if(position == 1)
                {
                    currentlog = db.calcExpenses();
                    tvBalance.setText("Spent: " + String.valueOf(currentlog) + " SEK");
                }
                else if (position == 2){
                    currentlog = db.calcIncome();
                    tvBalance.setText("Income: " + String.valueOf(currentlog) + " SEK");
                }
                else if(position == 3){
                    currentlog = db.calcWish();
                    tvBalance.setText("On wish: " + String.valueOf(currentlog) + " SEK");
                }
                else if(position == 4){
                    currentlog = db.calcEarning();
                    tvBalance.setText("Chore Money: " + String.valueOf(currentlog) + " SEK");
                }

                data = fill_with_data(position);
                adapter = new AccountsRecyclerViewAdapter(data, getApplication());

                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
    }

    /*
     * Method retrieves information from the database about
     * inflows and outflows and adds this to a List
     */
    public List<AccountsRow> fill_with_data(int typeOfEntry) {
        Log.v(TAG, "typeOfEntry: "+typeOfEntry);
        List<AccountsRow> row = new ArrayList<>();
        ArrayList<Entry> entries = new ArrayList<>();

        if (typeOfEntry == 1) {
            entries = db.expensesEntries();
            for (Entry e : entries) {
                row.add(new AccountsRow(e.getDate(), e.getDesc(), e.getAmount(), e.getTypeOfEntry()));
            }
        } else if (typeOfEntry == 2) {
            entries = db.incomeEntries();
            for (Entry e : entries) {
                row.add(new AccountsRow(e.getDate(), e.getDesc(), e.getAmount(), e.getTypeOfEntry()));
            }
        } else if (typeOfEntry == 3) {
            entries = db.wishEntries();
            for (Entry e : entries) {
                row.add(new AccountsRow(e.getDate(), e.getDesc(), e.getAmount(), e.getTypeOfEntry()));
            }
        } else if (typeOfEntry == 4) {
            entries = db.earningsEntries();
            for (Entry e : entries) {
                row.add(new AccountsRow(e.getDate(), e.getDesc(), e.getAmount(), e.getTypeOfEntry()));
            }
        } else if (typeOfEntry == 0){
            entries = db.allEntries();
            for (Entry e : entries) {
                row.add(new AccountsRow(e.getDate(), e.getDesc(), e.getAmount(), e.getTypeOfEntry()));
            }
        }
        return row;
    }
}

