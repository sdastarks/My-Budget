package com.example.mybudget;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
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


public class SettingsActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "themePreferenceFile";
    public static final String KEY_THEME_RES_ID = "themeResId";
    protected ImageView heroImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int themeResId = settings.getInt(KEY_THEME_RES_ID, -1);
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
                saveAndSwitchTheme(R.style.AppTheme_CookieMonster);
                //heroImage.setImageDrawable(getDrawable(R.drawable.cookie));

            }
        });

        Button themeSwitchRobot = findViewById(R.id.btn_science);
        themeSwitchRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndSwitchTheme(R.style.AppTheme_Science);
               // heroImage.setImageDrawable(getDrawable(R.drawable.science));
            }
        });

        Button themeSwitchGirl = findViewById(R.id.btn_girl);
        themeSwitchGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndSwitchTheme(R.style.AppTheme_GirlMonster);
                //heroImage.setImageDrawable(getDrawable(R.drawable.girl));
            }
        });
        Button themeSwitchCrazy = findViewById(R.id.btn_crazy);
        themeSwitchCrazy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndSwitchTheme(R.style.AppTheme_CrazyMonster);
                //heroImage.setImageDrawable(getDrawable(R.drawable.crazy));
            }
        });


    }

    private void saveAndSwitchTheme(@StyleRes int themeRes) {
        SharedPreferences sharedPrefs = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(KEY_THEME_RES_ID, themeRes);
        editor.apply();
        recreate();
    }
}






//    protected SharedPreferences mPrefs;
//    private String currentTheme;
////    private static final String COOKIEMONSTER = "cookie";
////    private static final String ROBOT = "robot";
////    private static final ArrayMap<String, ThemeSpec> VALUES = new ArrayMap<>();
////    static {
////        BUTTONS.put(R.id.btn_cookieMonster, COOKIEMONSTER);
////        BUTTONS.put(R.id.btn_robot, ROBOT);
////
////        VALUES.put(COOKIEMONSTER, new DayNightSpec(R.string.btn_coo));
////        VALUES.put(ROBOT, new DarkSpec(R.string.theme_dark));
//
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//
//            mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
//            SharedPreferences.Editor prefEditor = mPrefs.edit();
//
//        mPrefs
//                .edit()
//                .putString("current_theme",
//                        "cookie")
//                .apply();
//            recreate();
//
//
//
////        mPrefs= PreferenceManager.getDefaultSharedPreferences(this);
////        currentTheme = mPrefs.getString("current_theme", "default");
////        if (currentTheme == "cookie") {
////            setTheme(R.style.Theme_App_Lilac);
////        } else {
////        setTheme(R.style.Theme_App_Mint);
////        setContentView(...)
////    }
////
////
//
//
//
//
//        private fun setAppTheme(String currentTheme) {
//            if (currentTheme == MINT_THEME) {
//                setTheme(R.style.Theme_App_Mint);
//            }
//            else {
//                setTheme(R.style.Theme_App_Lilac);
//            }
//
//
//            override fun onCreate(savedInstanceState: Bundle?) {
//                super.onCreate(savedInstanceState)
//
//                mPrefs = PreferenceManager
//                        .getDefaultSharedPreferences(this)
//                currentTheme = mPrefs.getString("current_theme",
//                        "lilac");
//                setAppTheme(currentTheme);
//            }
//
//            override fun onResume() {
//                super.onResume()
//                theme = mPrefs.getString("current_theme", "lilac");
//                if(currentTheme != theme) {
//                    recreate();
//                }
//            }
//
//            private fun setAppTheme(currentTheme: String) {
//                if (currentTheme == MINT_THEME) {
//                    setTheme(R.style.Theme_App_Mint);
//                }
//            else {
//                setTheme(R.style.Theme_App_Lilac)
//                    }
//                }
//            }
//        }
//
//
//
//
////            prefEditor.putBoolean("USE_DARK_THEME", !mPrefs.getBoolean("USE_DARK_THEME",false));
////            prefEditor.commit();
////            setTheme(mPrefs.getBoolean("USE_DARK_THEME",false) ? R.style.AppTheme_CookieMonster : R.style.AppTheme);
////            setContentView(R.layout.activity_main);
////
////    }
////    public static ThemeSpec getTheme(String value, boolean isTranslucent) {
////        ThemeSpec themeSpec = VALUES.get(VALUES.containsKey(value) ? value : LIGHT);
////        return isTranslucent ? themeSpec.getTranslucent() : themeSpec;
////    }
//
//
//}