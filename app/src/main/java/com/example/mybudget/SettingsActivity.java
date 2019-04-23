package com.example.mybudget;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mybudget.Account.AccountActivity;
import com.example.mybudget.Chores.ChoresActivity;
import com.example.mybudget.Home.MainActivity;
import com.example.mybudget.WishList.WishlistActivity;


public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivityLog";
    public static final String PREFS_NAME = "themePreferenceFile";
    public static final String KEY_THEME_RES_ID = "themeResId";
    public static final String KEY_DRAWABLE_RES_ID = "imageResId";
    protected int imageResId;
    protected int themeResId;

    protected ImageView heroImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        themeResId = settings.getInt(KEY_THEME_RES_ID, -1);
        imageResId = settings.getInt(KEY_DRAWABLE_RES_ID, -1);
        if (themeResId != -1) {
            setTheme(themeResId);
        }
        setContentView(R.layout.activity_settings);

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
                        Intent intent0 = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(intent0);
                        break;

                    case R.id.nav_wishlist:
                        Intent intent1 = new Intent(SettingsActivity.this, WishlistActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_account:
                        Intent intent2 = new Intent(SettingsActivity.this, AccountActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_chores:
                        Intent intent3 = new Intent(SettingsActivity.this, ChoresActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_settings:
                        Intent intent4 = new Intent(SettingsActivity.this, SettingsActivity.class);
                        startActivity(intent4);
                        break;

                }
                return false;
            }
        });


        Button themeSwitchCookie = findViewById(R.id.btn_cookie);
        themeSwitchCookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndSwitchTheme(R.style.AppTheme_CookieMonster, R.drawable.cookie);

            }
        });

        Button themeSwitchRobot = findViewById(R.id.btn_science);
        themeSwitchRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndSwitchTheme(R.style.AppTheme_Science, R.drawable.science);
            }
        });

        Button themeSwitchGirl = findViewById(R.id.btn_girl);
        themeSwitchGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndSwitchTheme(R.style.AppTheme_GirlMonster, R.drawable.girl);
            }
        });
        Button themeSwitchCrazy = findViewById(R.id.btn_crazy);
        themeSwitchCrazy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndSwitchTheme(R.style.AppTheme_CrazyMonster, R.drawable.crazy);
            }
        });


    }

    private void saveAndSwitchTheme(@StyleRes int themeRes, int imageRes) {
        SharedPreferences sharedPrefs = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(KEY_THEME_RES_ID, themeRes);
        editor.putInt(KEY_DRAWABLE_RES_ID, imageRes);
        editor.apply();
        recreate();
    }
}

