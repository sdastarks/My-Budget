package com.example.mybudget.Account;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.mybudget.Chores.ChoresActivity;
import com.example.mybudget.Home.MainActivity;
import com.example.mybudget.Models.Entry;
import com.example.mybudget.R;
import com.example.mybudget.WishList.WishlistActivity;
import com.example.mybudget.myDbHelper;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity {
    private static final String TAG = "AccountActivityLog";
    RecyclerView mRecyclerView;
    private List<AccountsRow> data;
    private AccountsRecyclerViewAdapter adapter;

    myDbHelper db = new myDbHelper(this, "myDb.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //create recycler view
        mRecyclerView = findViewById(R.id.recyclerview);

        data = fill_with_data(0);

        adapter = new AccountsRecyclerViewAdapter(data, getApplication());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Spinner mySpinner = (findViewById(R.id.spinner1));
        String [] labels={"Everything","Expenditures","Income","Spend on wish","Earned from chore"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, labels);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(spinnerAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AccountActivity.this, "position: "+position, Toast.LENGTH_SHORT).show();

                data =fill_with_data(position);
                if (data.isEmpty()){
                    System.out.println("empty");
                }else{
                    System.out.println("not empty");
                }
                for(AccountsRow e : data)
                {
                    System.out.println(e.title);
                }
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
     *   Method for filtering data by expenditures status
     */
    public List<AccountsRow> fill_with_expenditures(){
        List<AccountsRow> row = new ArrayList<>();
        ArrayList<Entry> entries=db.allEntries();

        for(Entry e:entries){
            if (e.getTypeOfEntry()== 0){
                row.add(new AccountsRow( e.getDate(), e.getDesc(), e.getAmount(), ""+e.getTypeOfEntry()));
            }
        }
        return row;
    }

    /*
     *   Method for filtering data by income status
     */
    public List<AccountsRow> fill_with_income_data(){
        List<AccountsRow> row = new ArrayList<>();
        ArrayList<Entry> entries=db.allEntries();

        for(Entry e:entries){
            if (e.getTypeOfEntry()== 1){
                row.add(new AccountsRow( e.getDate(), e.getDesc(), e.getAmount(), ""+e.getTypeOfEntry()));
            }
        }
        return row;
    }

    /*
     *   Method for filtering data by income status
     */
    public List<AccountsRow> fill_with_earnedFromChore(){
        List<AccountsRow> row = new ArrayList<>();
        ArrayList<Entry> entries=db.allEntries();

        for(Entry e:entries){
            if (e.getTypeOfEntry()== 3){
                row.add(new AccountsRow( e.getDate(), e.getDesc(), e.getAmount(), ""+e.getTypeOfEntry()));
            }
        }
        return row;
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
                row.add(new AccountsRow(e.getDate(), e.getDesc(), e.getAmount(), "Expense"));
            }
        } else if (typeOfEntry == 2) {
            entries = db.incomeEntries();
            for (Entry e : entries) {
                row.add(new AccountsRow(e.getDate(), e.getDesc(), e.getAmount(), "Income"));
            }
        } else if (typeOfEntry == 3) {
            entries = db.wishEntries();
            for (Entry e : entries) {
                row.add(new AccountsRow(e.getDate(), e.getDesc(), e.getAmount(), "Spent on wish"));
            }
        } else if (typeOfEntry == 4) {
            entries = db.earningsEntries();
            for (Entry e : entries) {
                row.add(new AccountsRow(e.getDate(), e.getDesc(), e.getAmount(), "Earned from chore"));
            }
        } else if (typeOfEntry == 0){
            entries = db.allEntries();
            String entry = "";
            for (Entry e : entries) {
                if (e.getTypeOfEntry() == 0)
                    entry = "Expense";
                else if (e.getTypeOfEntry() == 1)
                    entry = "Income";
                else if (e.getTypeOfEntry() == 2)
                    entry = "Transferred to Wish";
                else if (e.getTypeOfEntry() == 3)
                    entry = "Earned from Chore";

                row.add(new AccountsRow(e.getDate(), e.getDesc(), e.getAmount(), entry));
            }
        }
        return row;
    }




    //ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AccountActivity.this,
    //        android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.filter));
    //myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown);
    //mySpinner.setAdapter(myAdapter);

}

