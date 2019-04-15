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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //create recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

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
        Date currentDate = new Date();

        List<AccountsRow> row = new ArrayList<>();

        // DAWNIE database
        row.add(new AccountsRow( currentDate, "Bike", 2300,"saved"));
        row.add(new AccountsRow( currentDate, "hoverboard", 5300,"saved"));
        row.add(new AccountsRow( currentDate, "from mommy", 300,"received"));
        row.add(new AccountsRow( currentDate, "pen", 35,"spent"));
        row.add(new AccountsRow( currentDate, "class", 23000,"saved"));
        row.add(new AccountsRow( currentDate, "candy", 20,"spent"));
        row.add(new AccountsRow( currentDate, "new jeans", 350,"saved"));
        row.add(new AccountsRow( currentDate, "donut", 25,"spent"));
        row.add(new AccountsRow( currentDate, "ice cream", 50,"spent"));
        row.add(new AccountsRow( currentDate, "ball", 270,"saved"));
        row.add(new AccountsRow( currentDate, "doll house", 600,"saved"));
        row.add(new AccountsRow( currentDate, "laptop", 8000,"saved"));
        row.add(new AccountsRow( currentDate, "ice cream", 45,"spent"));
        row.add(new AccountsRow( currentDate, "cola", 50,"spent"));
        row.add(new AccountsRow( currentDate, "chips", 20,"spent"));
        row.add(new AccountsRow( currentDate, "ticket to grandma", 350,"saved"));
        row.add(new AccountsRow( currentDate, "donut", 25,"spent"));
        row.add(new AccountsRow( currentDate, "ice cream", 40,"spent"));
        row.add(new AccountsRow( currentDate, "juice", 30,"spent"));


        return row;
    }

}
