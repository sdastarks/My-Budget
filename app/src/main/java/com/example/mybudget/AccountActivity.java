package com.example.mybudget;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mybudget.Models.Entry;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountActivity extends AppCompatActivity {
    private static final String TAG = "AccountActivityLog";

    RecyclerView mRecyclerView;
    TextView tvBalance;
    myDbHelper db = new myDbHelper(this, "myDb.db", null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //create recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        //Viewing Balance
        tvBalance = findViewById(R.id.tvBalance);
        int balance = db.balance();
        tvBalance.setText("Balance: " + String.valueOf(balance));

        List<AccountsRow> data = fill_with_data();

        AccountsRecyclerViewAdapter adapter = new AccountsRecyclerViewAdapter(data, getApplication());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
         * Method creates a pathway to the other
         * activities via a navigation bar
         */

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
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
    public List<AccountsRow> fill_with_data() {

        List<AccountsRow> row = new ArrayList<>();
        ArrayList<Entry> entries = db.allEntries();
        String typeOfEntry = "";
        for(Entry e : entries){
            if(e.getTypeOfEntry() == 0)
                typeOfEntry = "Expense";
            else if(e.getTypeOfEntry() == 1)
                typeOfEntry = "Income";
            else if(e.getTypeOfEntry() == 2)
                typeOfEntry = "Transferred to Wish";
            else if(e.getTypeOfEntry() == 3)
                typeOfEntry = "Earned from Chore";

            row.add(new AccountsRow( e.getDate(), e.getDesc(), e.getAmount(), typeOfEntry));
        }
        return row;
    }

}
