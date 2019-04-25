package com.example.mybudget.Chores;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.mybudget.Account.AccountActivity;
import com.example.mybudget.AvatarChangeActivity;
import com.example.mybudget.Home.MainActivity;
import com.example.mybudget.R;
import com.example.mybudget.WishList.WishlistActivity;
import com.example.mybudget.myDbHelper;

public class ChoresActivity extends AvatarChangeActivity {
    protected Boolean inflow;
    private static  final String TAG= "ChoresActivity";
    myDbHelper db = new myDbHelper(this, "myDb.db", null, 1);

    //Initilaizing ImagesButtons
    ImageButton laundry, dishwashing, readingBook, vacuum, beingNice, petting, grass, mopping,
            goodGrade, cooking, baby, other;
    String title = "";
    int amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores);

        /*
         * Method creates a pathway to the other
         * activities via a navigation bar
         */

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        Intent intent1 = new Intent(ChoresActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_wishlist:
                        Intent intent2 = new Intent(ChoresActivity.this, WishlistActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.nav_account:
                        Intent intent3 = new Intent(ChoresActivity.this, AccountActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_chores:
                        break;

                    case R.id.nav_avatar_change:
                        Intent intent4 = new Intent(ChoresActivity.this, AvatarChangeActivity.class);
                        startActivity(intent4);
                        break;

                }
                return false;
            }
        });

        laundry = findViewById(R.id.laudry);
        laundry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                 title = "Laundry";
                 amount = 50;
                 addMoney(title,amount);
            }
        });

        dishwashing = findViewById(R.id.dishwashing);
        dishwashing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 title = "Dish washing";
                 amount = 50;
                addMoney(title,amount);
            }
        });

        readingBook = findViewById(R.id.readingBook);
        readingBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 title = "Reading a book";
                 amount = 50;
                addMoney(title,amount);
            }
        });
        vacuum = findViewById(R.id.vacuum);
        vacuum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 title = "Doing the vacuuming";
                 amount = 50;
                addMoney(title,amount);
            }
        });
        beingNice = findViewById(R.id.beingNice);
        beingNice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 title = "Being nice";
                 amount = 50;
                addMoney(title,amount);
            }
        });
        petting = findViewById(R.id.petting);
        petting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 title = "Walking the dog";
                 amount = 50;
                addMoney(title,amount);
            }
        });
        grass = findViewById(R.id.grass);
        grass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 title = "Mowing the lawn";
                 amount = 50;
                addMoney(title,amount);
            }
        });
        mopping = findViewById(R.id.mopping);
        mopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 title = "Mopping the floor";
                 amount = 50;
                addMoney(title,amount);
            }
        });
        goodGrade = findViewById(R.id.goodGrade);
        goodGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 title = "A high grade in class";
                 amount = 50;
                addMoney(title,amount);
            }
        });
        cooking = findViewById(R.id.cooking);
        cooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 title = "Cooking a small dish";
                 amount = 50;
                addMoney(title,amount);
            }
        });
        baby = findViewById(R.id.baby);
        baby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 title = "Babysitting";
                 amount = 50;
                addMoney(title,amount);
            }
        });
        other = findViewById(R.id.other);
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 title = "Specify here!";
                 amount = 50;
                addMoney(title,amount);
            }
        });
    }

    public void addMoney(String title, int amount) {

        addChoresMoneyFragment add = new addChoresMoneyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("amount", amount);
        add.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.check, add);
        inflow = true;
        ft.commit();
    }
}