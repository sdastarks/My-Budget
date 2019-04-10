package com.example.mybudget;
/**
 *
 * Main class
 *
 * Displays a progress bar
 *
 * @author Daniel Beadleson
 */
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class MainActivity extends AppCompatActivity {
    private static  final String TAG= "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Method creates a pathway to the other
         * activities via a navigation bar
         */

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem =menu.getItem(0);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        break;

                    case R.id.nav_wishlist:
                         Intent intent1 =new Intent(MainActivity.this, WishlistActivity.class);
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
       ;
        //Fragment fragment_add_income=findViewById(R.id.fragment_add_income);


    }
    /*
     * Method sets the status of the progress bar
     */
    public void setProgressBar(){
        int progress=60; // data received from database
        CircularProgressBar circularProgressBar = (CircularProgressBar)findViewById(R.id.progressBar);
        circularProgressBar.setProgress(progress);
        TextView progresstxt = findViewById(R.id.txt_progressBar);
        progresstxt.setText(progress+"%");
    }
    /*
     * Method initialises a fragment allowing the
     * user to enter an income
     */
    public void onAddSelected(View view) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, new AddIncomeFragment());
        ft.commit();
    }
}
