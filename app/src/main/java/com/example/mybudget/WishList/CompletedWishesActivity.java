package com.example.mybudget.WishList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.mybudget.AvatarChangeActivity;
import com.example.mybudget.Models.WishList;
import com.example.mybudget.R;
import com.example.mybudget.myDbHelper;

import java.util.ArrayList;

/**
 * Class to show all completed wishes in Activity
 */
public class CompletedWishesActivity extends AvatarChangeActivity implements RecyclerViewAdapter.OnWishListener{

    private FloatingActionButton exitCompletedWishes;

    protected ArrayList<Integer> mWishId = new ArrayList<>();
    protected ArrayList <String> mWishNames = new ArrayList<>();
    private ArrayList <Integer> mImageUrls = new ArrayList<>();
    private ArrayList <Integer> mWishPrices = new ArrayList<>();
    private ArrayList <Integer> mSavingProgress = new ArrayList<>();
    private ArrayList<Drawable> mDrawable = new ArrayList<>();

    private Context mContext;
    private RecyclerViewAdapter.OnWishListener mOnWishListener;

    myDbHelper db = new myDbHelper(this, "myDb.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_wishes);

        loadDataToRecycle();
        initRecyclerView();

        exitCompletedWishesFuction();
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
                mDrawable.add(getDrawable(wl.getImage()));
                //mImageUrls.add(wl.getImage());
                mWishPrices.add(wl.getCost());
                mSavingProgress.add(wl.getSaved());
            }
        }
    }

    public void exitCompletedWishesFuction(){
        exitCompletedWishes = findViewById(R.id.exitCompletedWishes);
        exitCompletedWishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompletedWishesActivity.this, WishlistActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onWishClick(int position) {

    }
}
