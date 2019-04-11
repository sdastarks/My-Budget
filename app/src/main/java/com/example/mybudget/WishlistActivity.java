package com.example.mybudget;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity implements RecyclerViewAdapter.OnWishListener {
    private static final String TAG = "WishlistActivity";
    private ArrayList <String> mWishNames = new ArrayList<>();
    private ArrayList <String> mImageUrls = new ArrayList<>();
    private ArrayList <Integer> mWishPrices = new ArrayList<>();
    private ArrayList <Integer> mSavingProgress = new ArrayList<>();
    private FloatingActionButton addWish;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        Log.d(TAG, "onCreate: startec");
        /*
         * Method creates a pathway to the other
         * activities via a navigation bar
         */

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem =menu.getItem(1);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        Intent intent1= new Intent(WishlistActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_wishlist:
                        break;

                    case R.id.nav_account:
                        Intent intent2 = new Intent(WishlistActivity.this, AccountActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_chores:
                        Intent intent3 = new Intent(WishlistActivity.this, ChoresActivity.class);
                        startActivity(intent3);
                        break;

                }
                return false;
            }
        });

        addWish = findViewById(R.id.add_wish);
        addWish.show();
        initImageBitmaps();
    }


    /*
     * Method adds placeholder values to Wish list
     * will be replaced with actual data base values
     */
    private void initImageBitmaps() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps");
        mImageUrls.add("https://www.girlsdressline.com/image/data/products/burgundy-lace-pleated-flower-girl-dresses-GG-3527-BG.jpg");
        mWishNames.add("Dress");
        mWishPrices.add(200);
        mSavingProgress.add(40);

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mWishNames.add("Keds");
        mWishPrices.add(340);
        mSavingProgress.add(60);

        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mWishNames.add("Jimmys present");
        mWishPrices.add(50);
        mSavingProgress.add(40);

        mImageUrls.add("https://static.thenounproject.com/png/971099-200.png");
        mWishNames.add("Add your dream");
        mWishPrices.add(0);
        mSavingProgress.add(0);
        initRecyclerView();
    }

 
    //Method initializes List view with values for Wish List
    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: recycler view init");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mWishNames, mWishPrices, mImageUrls,
                mSavingProgress,this, this );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Method navigates to selected wish fragment
    @Override
    public void onWishClick(int position) {
        Log.d(TAG, "onWishClick: clicked : " + position);
        addWish.hide();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_wish_fragment, new WishFragment());
        index = position ;
        ft.commit();

    }


}
