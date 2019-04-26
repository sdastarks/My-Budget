package com.example.mybudget.WishList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mybudget.Account.AccountActivity;
import com.example.mybudget.AvatarChangeActivity;
import com.example.mybudget.Chores.ChoresActivity;
import com.example.mybudget.Home.MainActivity;
import com.example.mybudget.Models.WishList;
import com.example.mybudget.R;
import com.example.mybudget.myDbHelper;

import java.util.ArrayList;

public class WishlistActivity extends AvatarChangeActivity implements RecyclerViewAdapter.OnWishListener {
    private static final String TAG = "WishlistActivity";
    protected ArrayList<Integer> mWishId = new ArrayList<>();
    protected ArrayList <String> mWishNames = new ArrayList<>();
    private ArrayList <Integer> mImageUrls = new ArrayList<>();
    private ArrayList <Integer> mWishPrices = new ArrayList<>();
    private ArrayList <Integer> mSavingProgress = new ArrayList<>();
    private ArrayList<Drawable> mDrawable = new ArrayList<>();
    private FloatingActionButton addWish;
    protected int id;
    protected int wishPrice;
    protected Integer progress;
    private Button register_button;
    int index;
    private Toolbar toolbar;
    myDbHelper db = new myDbHelper(this, "myDb.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        Log.d(TAG, "onCreate: startec");
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        /*
         * Method creates a pathway to the other
         * activities via a navigation bar
         */

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Wishlist");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

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

                    case R.id.nav_avatar_change:
                        Intent intent4 = new Intent(WishlistActivity.this, AvatarChangeActivity.class);
                        startActivity(intent4);
                        break;

                }
                return false;
            }
        });


        addWish = findViewById(R.id.add_wish);
        addWish.show();
        loadDataToRecycle();
        initRecyclerView();
        //initImageBitmaps();
    }


    //Method initializes List view with values for Wish List
    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: recycler view init");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mWishId ,mWishNames, mWishPrices, mImageUrls,
                mSavingProgress,this, this ,mDrawable);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Method navigates to selected wish fragment
    @Override
    public void onWishClick(int position) {
        Log.d(TAG, "onWishClick: clicked : " + position);
            addWish.hide();
            toolbar.setVisibility(View.INVISIBLE);
            id = mWishId.get(position);
            wishPrice = mWishPrices.get(position);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_wish_fragment, new WishFragment());
            index = position;
            ft.commit();
    }


    public void onAddWish(View view) {
        Log.d(TAG, "onAddWish: clicked : " );
            addWish.hide();
            toolbar.setVisibility(View.INVISIBLE);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_wish_fragment, new NewWishFragment());
            ft.commit();

    }

    /**
     * Loading all wishes in the list from the database
     * @auth DAWNIE Safar
     */
    public void loadDataToRecycle(){

        ArrayList<WishList> loadwishes = db.loadWishes();

        for(WishList wl : loadwishes){
            mWishId.add(wl.getWishListId());
            mWishNames.add(wl.getTitle());
            mDrawable.add(getDrawable(wl.getImage()));
            //mImageUrls.add(wl.getImage());
            mWishPrices.add(wl.getCost());
            mSavingProgress.add(wl.getSaved());
        }
    }
}
