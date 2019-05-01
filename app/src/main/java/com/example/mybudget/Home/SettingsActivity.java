package com.example.mybudget.Home;
/**
 * Class allows the user to enhance the
 * usability of the app by enabling notifications,
 * Monthly email updates and payment reminders
 *
 * @author Daniel Beadleson
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mybudget.AlertReceiver;
import com.example.mybudget.AvatarChangeActivity;
import com.example.mybudget.Profile.RegisterActivity;
import com.example.mybudget.R;
import com.example.mybudget.WishList.RegisterRequestDialog;
import com.example.mybudget.WishList.WishlistActivity;
import com.example.mybudget.myDbHelper;

import java.text.DateFormat;
import java.util.Calendar;

import static com.example.mybudget.Profile.RegisterActivity.USER_ID;

public class SettingsActivity extends AvatarChangeActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "SettingsActivityLog";
    public static final String SETTINGSPREFS_NAME = "switchPreferences";
    public static final String EMAILPREFS = "email_enabled";
    public static final String MESSAGEPREFS = "messages_enabled";

    private Button btn_exit;
    protected static Switch swt_notifications;
    private Switch swt_email;
    private Switch swt_telephone;
    private ImageView imageViewHero;
    private Drawable avatar;
    protected static Drawable[] dnotification;
    private Drawable[] dEmail;
    private Drawable[] dMessages;
    private int primarycolor;
    protected Boolean timeChosen;
    private SharedPreferences settingsSharedPref;

    myDbHelper db = new myDbHelper(this, "myDb.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsSharedPref = getSharedPreferences(SETTINGSPREFS_NAME, 0);

        btn_exit = findViewById(R.id.btn_exit_settings);
        swt_notifications = findViewById(R.id.switch_notifications);
        swt_email = findViewById(R.id.switch_email);
        swt_telephone = findViewById(R.id.switch_messages);
        imageViewHero = findViewById(R.id.avatarImageSettings);

        dnotification = swt_notifications.getCompoundDrawables();
        dEmail = swt_email.getCompoundDrawables();
        dMessages = swt_telephone.getCompoundDrawables();

        checkRegistration();
        setAvatar();
        setPrimarycolor();
        previousSwitchStates();
        exitSettings();
        notificationsSwitch();
        enableEmailSwitch();
        enableMessagesSwitch();

    }

    /**
     * Method checks if the user is
     * Registered before entering registration
     */
    public void checkRegistration() {

        SharedPreferences sharedPrefs = getSharedPreferences(RegisterActivity.USER_PREFS_NAME, 0);
        if (sharedPrefs.getInt(USER_ID, 0) == 0) {
            Log.d(TAG, "onAddWish: locking user if not registered");
            RegisterRequestDialog dialog = new RegisterRequestDialog();
            dialog.show(getSupportFragmentManager(), "register request dialog");
        }
    }

    /**
     * Method sets the avatar based on the theme
     *
     * @return whether a theme has been set
     */
    public Boolean setAvatar() {
        if (imageResId != -1) {
            avatar = getDrawable(imageResId);
            imageViewHero.setImageDrawable(avatar);
            return true;
        }
        return false;
    }

    /**
     * Method sets the primary colour
     * used to change the colour
     * of th icons
     */
    public void setPrimarycolor() {
        if (R.style.AppTheme_Science == themeResId) {
            primarycolor = getResources().getColor(R.color.scienceMonsterColorAccent);
        } else if (R.style.AppTheme_CookieMonster == themeResId) {
            primarycolor = getResources().getColor(R.color.cookieMonsterColorAccent);
        } else if (R.style.AppTheme_GirlMonster == themeResId) {
            primarycolor = getResources().getColor(R.color.girlMonsterColorAccent);
        } else if (R.style.AppTheme_CrazyMonster == themeResId) {
            primarycolor = getResources().getColor(R.color.CrazyMonsterColorAccent);
        } else {
            primarycolor = getResources().getColor(R.color.colorAccent);
        }
    }

    /**
     * Method sets the state of the switch bars to
     * their original state
     */
    public void previousSwitchStates() {

        Boolean notificationsSet = settingsSharedPref.getBoolean("notification_switch", false);
        Boolean emailsSet = settingsSharedPref.getBoolean("email_switch", false);
        Boolean messagesSet = settingsSharedPref.getBoolean("messages_switch", false);

        swt_notifications.setChecked(notificationsSet);
        swt_email.setChecked(emailsSet);
        swt_telephone.setChecked(messagesSet);

        if (notificationsSet) {
            dnotification[0].setColorFilter(primarycolor, PorterDuff.Mode.SRC_ATOP);
            String alarmText = settingsSharedPref.getString("alarm", "Set Daily Reminder");
            swt_notifications.setText(alarmText);
        }
        if (emailsSet) {
            dEmail[0].setColorFilter(primarycolor, PorterDuff.Mode.SRC_ATOP);
        }
        if (messagesSet) {
            dMessages[0].setColorFilter(primarycolor, PorterDuff.Mode.SRC_ATOP);
        }

    }

    /**
     * Method exits the settings activity
     */
    public Boolean exitSettings() {
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        return true;
    }

    /**
     * Method determines what should happen
     * if the notifications switch is enabled
     *
     * @return if enabled
     */
    public Boolean notificationsSwitch() {
        swt_notifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //initialise alarm

                    DialogFragment timePicker = new TimePickerFragment();
                    timePicker.show(getSupportFragmentManager(), "time picker");
                    dnotification[0].setColorFilter(primarycolor, PorterDuff.Mode.SRC_ATOP);

                } else {
                    dnotification[0].setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                    swt_notifications.setText("Set Daily Reminder");
                    cancelAlarm();

                }
                Log.v(TAG, "time chosen: " + timeChosen);
                storeSwitchState("notification_switch", isChecked);
            }
        });
        return true;
    }

    /**
     * Method determines what should happen
     * if the notifications switch is enabled
     *
     * @return if enabled
     */
    public Boolean enableEmailSwitch() {
        swt_email.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String userParentEmail = db.getUser(getSharedPreferences(
                            RegisterActivity.USER_PREFS_NAME,
                            0).getInt(USER_ID, 0)).getUserMail();
                    Log.v(TAG, userParentEmail);
                    if (!userParentEmail.trim().isEmpty()) {
                        settingsSharedPref.edit().putBoolean(EMAILPREFS, true).commit();
                        dEmail[0].setColorFilter(primarycolor, PorterDuff.Mode.SRC_ATOP);
                    } else {
                        swt_email.setChecked(false);
                        isChecked = false;
                        Snackbar.make(buttonView, "Add an email", Snackbar.LENGTH_LONG).
                                setAction("GO", new EditProfileListener()).show();
                    }

                } else {
                    settingsSharedPref.edit().putBoolean(EMAILPREFS, false).commit();
                    dEmail[0].setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                }
                storeSwitchState("email_switch", isChecked);
            }
        });
        return true;
    }

    /**
     * Class allows sends the user to Edit profile
     * when selected through the snackbar
     */
    public class EditProfileListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent intent2 = new Intent(SettingsActivity.this, RegisterActivity.class);
            intent2.putExtra("editProfile", "update");
            startActivity(intent2);

        }
    }

    /**
     * Method determines what should happen
     * if the notifications switch is enabled
     *
     * @return if enabled
     */
    public Boolean enableMessagesSwitch() {
        swt_telephone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    settingsSharedPref.edit().putBoolean(MESSAGEPREFS, true).commit();
                    dMessages[0].setColorFilter(primarycolor, PorterDuff.Mode.SRC_ATOP);
                } else {
                    settingsSharedPref.edit().putBoolean(MESSAGEPREFS, false).commit();
                    dMessages[0].setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                }
                storeSwitchState("messages_switch", isChecked);
            }
        });
        return true;
    }

    /**
     * Method stores the state of the switch bar
     *
     * @return if states been saved
     */
    public Boolean storeSwitchState(String switchName, Boolean isChecked) {
        SharedPreferences.Editor editor = settingsSharedPref.edit();
        editor.putBoolean(switchName, isChecked);
        editor.commit();
        return true;
    }

    /**
     * Method retreives information from timepicker
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.v(TAG, "onTimeSet initialised");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        String timeText = "Reminder Set For: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        storeAlarmTime(timeText);
        swt_notifications.setText(timeText);

        startAlarm(calendar);
    }

    /**
     * Method initialises the alarm
     */
    private void startAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

    /**
     * Method cancels the alarm when the
     * switch is turned off
     */
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    /**
     * Stores the time in which the
     * alarm is set by the user
     */
    public void storeAlarmTime(String timeText) {
        SharedPreferences.Editor editor = settingsSharedPref.edit();
        editor.putString("alarm", timeText);
        editor.commit();
    }

}
