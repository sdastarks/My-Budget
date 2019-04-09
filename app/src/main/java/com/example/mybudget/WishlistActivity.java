package com.example.mybudget;

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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity {
    private static final String TAG = "WishlistActivity";
    private ArrayList <String> mWishNames = new ArrayList<>();
    private ArrayList <String> mImageUrls = new ArrayList<>();
    private ArrayList <Double> mWishPrices = new ArrayList<>();
    private ArrayList <ProgressBar> progressBarHorizontal = new ArrayList<>();


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

                }
                return false;
            }
        });
        initImageBitmaps();
    }

    private void initImageBitmaps() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps");
        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mWishNames.add("Havasu Falls");

        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mWishNames.add("Trondheim");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mWishNames.add("Portugal");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mWishNames.add("Rocky Mountain National Park");


        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mWishNames.add("Mahahual");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mWishNames.add("Frozen Lake");
        initRecyclerView();


    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: recycler view init");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mWishNames, mWishPrices, mImageUrls, progressBarHorizontal,this );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }
}
