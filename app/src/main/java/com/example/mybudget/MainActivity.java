package com.example.mybudget;
/**
 * Main class
 * <p>
 * Displays a progress bar
 *
 * @author Daniel Beadleson
 */

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView tvBalance;


    private static final String TAG = "MainActivityLog";
    protected Boolean inflow;
    myDbHelper db = new myDbHelper(this, "myDb.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sets the state of the drawer navigation bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /*
         * Method creates a pathway to the other
         * activities via a navigation bar
         */
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        break;

                    case R.id.nav_wishlist:
                        Intent intent1 = new Intent(MainActivity.this, WishlistActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_account:
                        Intent intent2 = new Intent(MainActivity.this, AccountActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_chores:
                        Intent intent3 = new Intent(MainActivity.this, ChoresActivity.class);
                        startActivity(intent3);
                        break;

                }
                return false;
            }
        });

        setProgressBar();
        updateBalance();
    }

    /*
     * Method sets the status of the progress bar
     */
    public void setProgressBar() {
        int progress = 60; // data received from database
        CircularProgressBar circularProgressBar = (CircularProgressBar) findViewById(R.id.progressBar);
        circularProgressBar.setProgress(progress);
        TextView progresstxt = findViewById(R.id.txt_progressBar);
        progresstxt.setText(progress + "%");
    }

    /*
     * Method initialises a fragment allowing the
     * user to enter an inflow
     */
    public void onAddSelected(View view) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, new InflowOutflowFragment());
        inflow = true;
        ft.commit();
    }

    /*
     * Method initialises a fragment allowing the
     * user to enter an outflow
     */
    public void onMinusSelected(View view) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, new InflowOutflowFragment());
        inflow = false;
        ft.commit();
    }

    /*
     * Method intitialses a process when a item is selected
     * from the drawer navigation bar
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.v(TAG, "navagation item selected");
        switch (menuItem.getItemId()) {
            case R.id.side_nav_my_profile:
                Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.side_nav_edit_profile:
                Toast.makeText(this, "Edit Profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.side_nav_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
     * Method closes the drawer navigation bar when the back
     * button is selected
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Updates the balance with every entry
     * @auth DAWNIE
     */
    public int updateBalance(){
        tvBalance = findViewById(R.id.tvBalance);
        int income = db.calcIncome();
        int expense = db.calcExpenses();
        int wishes = db.calcWish();
        int earning = db.calcEarning();
        int balance = (income + earning) - (expense + wishes);
        tvBalance.setText(String.valueOf(balance));
        return balance;
    }
}
