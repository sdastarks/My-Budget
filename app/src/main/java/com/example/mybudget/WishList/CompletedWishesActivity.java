package com.example.mybudget.WishList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mybudget.Account.AccountActivity;
import com.example.mybudget.AvatarChangeActivity;
import com.example.mybudget.Chores.ChoresActivity;
import com.example.mybudget.Home.MainActivity;
import com.example.mybudget.Models.WishList;
import com.example.mybudget.R;
import com.example.mybudget.myDbHelper;

import java.util.ArrayList;

/**
 * Class to show all completed wishes in Activity
 */
public class CompletedWishesActivity extends AvatarChangeActivity implements RecyclerViewAdapter.OnWishListener{

    private FloatingActionButton exitCompletedWishes;
    private Toolbar toolbar;

    protected ArrayList<Integer> mWishId = new ArrayList<>();
    protected ArrayList <String> mWishNames = new ArrayList<>();
    private ArrayList <Integer> mImageUrls = new ArrayList<>();
    private ArrayList <Integer> mWishPrices = new ArrayList<>();
    private ArrayList <Integer> mSavingProgress = new ArrayList<>();
    private ArrayList<String> mDrawable = new ArrayList<>();

    private Context mContext;
    private RecyclerViewAdapter.OnWishListener mOnWishListener;

    myDbHelper db = new myDbHelper(this, "myDb.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_wishes);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //setTitle("Completed Wishes");
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTextAppearance);

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
                        Intent intent1= new Intent(CompletedWishesActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_wishlist:
                        Intent intent2 = new Intent(CompletedWishesActivity.this, WishlistActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.nav_account:
                        Intent intent3 = new Intent(CompletedWishesActivity.this, AccountActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.nav_chores:
                        Intent intent4 = new Intent(CompletedWishesActivity.this, ChoresActivity.class);
                        startActivity(intent4);
                        break;

                    case R.id.nav_avatar_change:
                        Intent intent5 = new Intent(CompletedWishesActivity.this, AvatarChangeActivity.class);
                        startActivity(intent5);
                        break;
                }
                return false;
            }
        });


        loadDataToRecycle();
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_completedWishes);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mWishId ,mWishNames, mWishPrices, mImageUrls,
                mSavingProgress, this, this, mDrawable);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    /**
     * Loading all wishes in the list from the database
     * @auth DAWNIE Safar
     */
    public void loadDataToRecycle(){

        ArrayList<WishList> loadwishes = db.loadWishes();

        for(WishList wl : loadwishes) {
            if (wl.getCost() == wl.getSaved()) {
                mWishId.add(wl.getWishListId());
                mWishNames.add(wl.getTitle());
                //TODO maybe try catch better be added DAWNIE
                //mDrawable.add(getDrawable(Integer.parseInt(wl.getImage())));

               // mDrawable.add(getDrawable(wl.getImage()));
                mDrawable.add(wl.getImage());
                //mImageUrls.add(wl.getImage());
                mWishPrices.add(wl.getCost());
                mSavingProgress.add(wl.getSaved());
            }
        }
    }

    @Override
    public void onWishClick(int position) {

    }
}
