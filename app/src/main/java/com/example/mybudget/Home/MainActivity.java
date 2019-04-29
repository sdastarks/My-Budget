package com.example.mybudget.Home;
/**
 * Main class
 * <p>
 * Displays a progress bar
 *
 * @author Daniel Beadleson
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybudget.Account.AccountActivity;
import com.example.mybudget.AvatarChangeActivity;
import com.example.mybudget.Chores.ChoresActivity;
import com.example.mybudget.Models.WishList;
import com.example.mybudget.Profile.ProfileActivity;
import com.example.mybudget.R;
import com.example.mybudget.Profile.RegisterActivity;
import com.example.mybudget.WishList.WishlistActivity;
import com.example.mybudget.myDbHelper;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;


public class MainActivity extends AvatarChangeActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView tvBalance;
    private int progress;
    private ImageView imageViewHero;
    private FloatingActionButton addIncome;
    private FloatingActionButton outOutcome;
    private Toolbar toolbar;
    private Button register_button;
    private Drawable d;

    private static final String TAG = "MainActivityLog";
    protected Boolean inflow;
    myDbHelper db = new myDbHelper(this, "myDb.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_CookieMonster);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("SettingsActivityLog","imageResId2: "+imageResId);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        addIncome = findViewById(R.id.floatingActionButton_add);
        outOutcome = findViewById(R.id.floatingActionButton_minus);
        imageViewHero=findViewById(R.id.imageViewHero);
        if(imageResId != -1){
            d = getDrawable(imageResId);
            imageViewHero.setImageDrawable(d);
        }

        //Sets the state of the drawer navigation bar

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.drawer_layout);

        View headView = navigationView.getHeaderView(0);
        ImageView heroImageNav = headView.findViewById(R.id.hero_image_side_nav);
        if(imageResId != -1){
            d = getDrawable(imageResId);
            heroImageNav.setImageDrawable(d);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ImageView logo =findViewById(R.id.logo);
        logo.setVisibility(View.VISIBLE);

        register_button = findViewById(R.id.user_register_button);
        register_button.setVisibility(View.VISIBLE);
        register_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.v(TAG, "clickable");
                Intent intent_register = new Intent (MainActivity.this, RegisterActivity.class);
                intent_register.putExtra("editProfile", "add");
                startActivity(intent_register);

            }
        });

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
                    case R.id.nav_avatar_change:
                        Intent intent4 = new Intent(MainActivity.this, AvatarChangeActivity.class);
                        startActivity(intent4);
                        break;

                }
                return false;
            }
        });

        int favWish_dbID= getFavouriteWishID();
        calcProgress(favWish_dbID);
        setProgressBar(favWish_dbID);
        setTitle(favWish_dbID);
        updateBalance();


    }
    /*
     * Method gets the database id of the favourite wish
     */
    public int getFavouriteWishID(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        int favWish_dbID= sharedPref.getInt("favouriteWish",0);
        Log.v(TAG, "favWish_dbID: "+favWish_dbID);
        return favWish_dbID;
    }
    /*
     * Method calculates the progress of the favourite wish
     */
    public void calcProgress(int favWish_dbID){
        if (favWish_dbID !=0){
            WishList favWish = db.returnWish(favWish_dbID);
            int wishPrice=favWish.getCost();
            int wishSaved=favWish.getSaved();
            progress =wishSaved*100/wishPrice;
        }
    }

    public void setTitle(int favWish_dbID){
        if (favWish_dbID !=0){
            TextView favWishTitle = findViewById(R.id.favWishTitle);
            WishList favWish = db.returnWish(favWish_dbID);
            favWishTitle.setText("Your savings for " + favWish.getTitle());
        }
    }

    /*
     * Method sets the state of the progress bar
     */
    public void setProgressBar(int favWish_dbID) {
        if (favWish_dbID !=0){
            CircularProgressBar circularProgressBar = (CircularProgressBar) findViewById(R.id.progressBar);
            circularProgressBar.setProgress(progress);
            TextView progresstxt = findViewById(R.id.txt_progressBar);
            progresstxt.setText(progress + "%");
        }
    }
    /*
     * Method initialises a fragment allowing the
     * user to enter an inflow
     */
    public void onAddSelected(View view) {
        addIncome.hide();
        outOutcome.hide();
        toolbar.setVisibility(View.INVISIBLE);
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
        addIncome.hide();
        outOutcome.hide();
        toolbar.setVisibility(View.INVISIBLE);
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
                Intent intent1 = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent1);
                break;
            case R.id.side_nav_edit_profile:
                Intent intent2 = new Intent(MainActivity.this, RegisterActivity.class);
                intent2.putExtra("editProfile", "update");
                startActivity(intent2);
                break;
            case R.id.side_nav_settings:
                Intent intent3 = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent3);
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
        tvBalance.setText(String.valueOf(balance) + " SEK");
        return balance;
    }
}
