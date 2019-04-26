package com.example.mybudget.Account;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import com.example.mybudget.Models.WishList;
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
    private Spinner mySpinner;
    private Spinner mySpinnerMonths;
    private BottomNavigationView navigation;

    myDbHelper db = new myDbHelper(this, "myDb.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        tvBalance = findViewById(R.id.tvBalance);
        currentlog = db.balance();
        //tvBalance.setText("Available: " + String.valueOf(currentlog) + " SEK");

        //create recycler view
        mRecyclerView = findViewById(R.id.recyclerview);

        mySpinner = (findViewById(R.id.spinner1));

        String[] labels = {"Everything", "Expenses", "Income", "On wish", "Chore Money"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, labels);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mySpinner.setAdapter(spinnerAdapter);
        mySpinnerOnClick();

        mySpinnerMonths = (findViewById(R.id.spinner_months));

        String[] labelsMonths = {"Everything", "January", "February", "March", "April", "May", "April", "June", "July",
                "August", "September", "October", "November", "December"};

        ArrayAdapter<String> spinnerAdapterMonths = new ArrayAdapter<String>(this,
                R.layout.spinner_item, labelsMonths);
        spinnerAdapterMonths.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mySpinnerMonths.setAdapter(spinnerAdapterMonths);
        mySpinnerMonthsOnClick();

        /*
         * Method creates a pathway to the other
         * activities via a navigation bar
         */

        navigation = findViewById(R.id.bottom_navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationOnClick();

        settingAdapter();
    }

    public void mySpinnerOnClick() {

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                settingAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void mySpinnerMonthsOnClick() {
        mySpinnerMonths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                settingAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void bottomNavigationOnClick() {
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        Intent intent1 = new Intent(AccountActivity.this, MainActivity.class);
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

    /**
     * First stage filter function
     *
     * @param typeOfEntry a filter taken from spinner position to specify the type of entry
     * @return an arrayList contains filtered data
     */
    public ArrayList<Entry> fill_with_data(int typeOfEntry) {
        ArrayList<Entry> entries = new ArrayList<>();

        if (typeOfEntry == 1) {
            entries = db.expensesEntries();
        } else if (typeOfEntry == 2) {
            entries = db.incomeEntries();
        } else if (typeOfEntry == 3) {
            entries = db.wishEntries();
        } else if (typeOfEntry == 4) {
            entries = db.earningsEntries();
        } else if (typeOfEntry == 0) {
            entries = db.allEntries();
        }
        return entries;
    }

    /**
     * Sets the number of months to the spinner
     *
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
     * @param spinnerPosition           a date filter as per the spinner position
     * @return data filtered by data and type of entry
     * @auth Dawnie Safar
     */
    public List<AccountsRow> filterEntriesByDate(ArrayList<Entry> dataFilteredByTypeOfEntry, int spinnerPosition) {
        ArrayList<Entry> allEntries = new ArrayList<>();
        List<AccountsRow> row = new ArrayList<>();

        if (spinnerPosition == 0) {
            allEntries = dataFilteredByTypeOfEntry;
        } else if (spinnerPosition == 1) {
            for (Entry entry : dataFilteredByTypeOfEntry) {
                if (entry.getDate().getMonth().toString() == "JANUARY") {
                    allEntries.add(entry);
                }
            }
        } else if (spinnerPosition == 2) {
            for (Entry entry : dataFilteredByTypeOfEntry) {
                if (entry.getDate().getMonth().toString() == "FEBRUARY") {
                    allEntries.add(entry);
                }
            }
        } else if (spinnerPosition == 3) {
            for (Entry entry : dataFilteredByTypeOfEntry) {
                if (entry.getDate().getMonth().toString() == "MARCH") {
                    allEntries.add(entry);
                }
            }
        } else if (spinnerPosition == 4) {
            for (Entry entry : dataFilteredByTypeOfEntry) {
                if (entry.getDate().getMonth().toString() == "APRIL") {
                    allEntries.add(entry);
                }
            }
        } else if (spinnerPosition == 5) {
            for (Entry entry : dataFilteredByTypeOfEntry) {
                if (entry.getDate().getMonth().toString() == "MAY") {
                    allEntries.add(entry);
                }
            }
        } else if (spinnerPosition == 6) {
            for (Entry entry : dataFilteredByTypeOfEntry) {
                if (entry.getDate().getMonth().toString() == "JUNE") {
                    allEntries.add(entry);
                }
            }
        } else if (spinnerPosition == 7) {
            for (Entry entry : dataFilteredByTypeOfEntry) {
                if (entry.getDate().getMonth().toString() == "JULY") {
                    allEntries.add(entry);
                }
            }
        } else if (spinnerPosition == 8) {
            for (Entry entry : dataFilteredByTypeOfEntry) {
                if (entry.getDate().getMonth().toString() == "AUGUST") {
                    allEntries.add(entry);
                }
            }
        } else if (spinnerPosition == 9) {
            for (Entry entry : dataFilteredByTypeOfEntry) {
                if (entry.getDate().getMonth().toString() == "SEPTEMBER") {
                    allEntries.add(entry);
                }
            }
        } else if (spinnerPosition == 10) {
            for (Entry entry : dataFilteredByTypeOfEntry) {
                if (entry.getDate().getMonth().toString() == "OCTOBER") {
                    allEntries.add(entry);
                }
            }
        } else if (spinnerPosition == 11) {
            for (Entry entry : dataFilteredByTypeOfEntry) {
                if (entry.getDate().getMonth().toString() == "NOVEMBER") {
                    allEntries.add(entry);
                }
            }
        } else if (spinnerPosition == 12) {
            for (Entry entry : dataFilteredByTypeOfEntry) {
                if (entry.getDate().getMonth().toString() == "DECEMBER") {
                    allEntries.add(entry);
                }
            }
        }
        for (Entry e : allEntries) {
            row.add(new AccountsRow(e.getDate(), e.getDesc(), e.getAmount(), e.getTypeOfEntry(), e.getEnteryId()));
        }
        return row;
    }

    /**
     * setting the spinners adapter with the selected filters
     *
     * @auth Dawnie Safar
     */
    public void settingAdapter() {
        //Filtering data
        entries = fill_with_data(mySpinner.getSelectedItemPosition());
        data = filterEntriesByDate(entries, mySpinnerMonths.getSelectedItemPosition());
        //setting Balance textView
        if (data.size() > 0) {
            tvBalance.setText("Current Filter: " + String.valueOf(calcBalanceAfterFilter(data)));
        } else if (data.size() <= 0) {
            tvBalance.setText("No data in the specified filter");
        }
        //sending filtered data to recycleView adapter
        adapter = new AccountsRecyclerViewAdapter(data, getApplication());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        setUpItemTouchHelper();
    }

    /**
     * @param filteredLog specifies the double filtered data(date and typeOfEntry)
     *                    to calculate the current balance state
     * @return filtered balance
     * @auth Dawnie
     */
    public int calcBalanceAfterFilter(List<AccountsRow> filteredLog) {
        int balance = 0;
        int expenses = 0;
        int income = 0;
        int onWish = 0;
        int fromChore = 0;
        for (AccountsRow ar : filteredLog) {
            //Log.v(TAG, "AccountsRow: " + ar.amount + " " + ar.status);
            if (ar.status == 0)
                expenses += ar.amount;
            else if (ar.status == 1)
                income += ar.amount;
            else if (ar.status == 2)
                onWish += ar.amount;
            else if (ar.status == 3)
                fromChore += ar.amount;
        }
        if (mySpinner.getSelectedItemPosition() == 0) {
            balance = income + fromChore - onWish - expenses;
            return balance;
        } else if (mySpinner.getSelectedItemPosition() == 1) {
            Log.v(TAG, "EXPENSES: " + expenses);
            return expenses;
        } else if (mySpinner.getSelectedItemPosition() == 2)
            return income;
        else if (mySpinner.getSelectedItemPosition() == 3)
            return onWish;
        else if (mySpinner.getSelectedItemPosition() == 4)
            return fromChore;
        else return 0;

    }

    /**
     * Method allows the user to swipe a
     * transaction to the left
     *
     * @author Daniel Beadleson
     */
    private void setUpItemTouchHelper() {

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                Drawable background = new ColorDrawable(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null)); //?attr/colorPrimaryDark
                Drawable icon = ContextCompat.getDrawable(AccountActivity.this, R.drawable.ic_baseline_delete_24px);
                icon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                int xMarkMargin = 68;

                // Set background when item dragged left
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);


                // set delete icon position
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = icon.getIntrinsicWidth();
                int intrinsicHeight = icon.getIntrinsicWidth();
                int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                int xMarkRight = itemView.getRight() - xMarkMargin;
                int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                int xMarkBottom = xMarkTop + intrinsicHeight;
                icon.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                icon.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int id = (int) viewHolder.itemView.getTag();
                removeItem(id);

            }
        }).attachToRecyclerView(mRecyclerView);

    }

    /**
     * Method removes an entry from the db
     * - if the entry is of type 2, the wish saved
     * is updated, if the wish still exists
     * - if the entry is of type 1 or 3, the
     * entry can only be removed if a negative balance
     * ISNT'T the outcome
     *
     * @param id database id
     * @author Daniel Beadleson
     */
    private void removeItem(int id) {
        // expenditures = 0; income = 1; spendOnWish = 2; earnedFromChore = 3;
        try {
            Entry e = db.returnEntry(id);
            int label = e.getTypeOfEntry();
            int balance = (db.calcIncome() + db.calcEarning()) - (db.calcExpenses() + db.calcWish());
            if (label == 2) {
                String title = e.getDesc();
                title = title.substring(0, title.length() - 9);
                WishList wish2update = db.findWishList(title);
                if (wish2update.getSaved()-e.getAmount()>0){
                    db.updateWish(wish2update.getWishListId(), title, wish2update.getCost(),
                            wish2update.getSaved() - e.getAmount(), wish2update.getImage());
                }
                db.deleteEntry(id);
            } else if (label == 1 | label == 3) {
                if (e.getAmount() > balance) {
                    Toast.makeText(this, "Unable to delete entry", Toast.LENGTH_SHORT).show();
                } else {
                    db.deleteEntry(id);
                }
            } else {
                db.deleteEntry(id);
            }
        } catch (NullPointerException e) {
            db.deleteEntry(id);
        }
        settingAdapter();
    }
}



