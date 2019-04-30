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
import com.example.mybudget.R;
import com.example.mybudget.WishList.RegisterRequestDialog;
import com.example.mybudget.myDbHelper;

import java.text.DateFormat;
import java.util.Calendar;

public class SettingsActivity extends AvatarChangeActivity implements TimePickerDialog.OnTimeSetListener{

    private static final String TAG = "SettingsActivityLog";
    public static final String PREFS_NAME = "switchPreferences";
    public static final String USER_PREFS_NAME = "userPreferenceFile";
    public static final String USER_ID = "userId";

    private Button btn_exit;
    private Switch swt_notifications;
    private Switch swt_email;
    private Switch swt_telephone;
    private ImageView imageViewHero;
    private Drawable avatar;
    private Drawable[] dnotification;
    private Drawable[] dEmail;
    private Drawable[] dMessages;
    private int primarycolor;


    myDbHelper db = new myDbHelper(this, "myDb.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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

        SharedPreferences sharedPrefs = getSharedPreferences(USER_PREFS_NAME, 0);
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
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        Boolean notificationsSet = settings.getBoolean("notification_switch", false);
        Boolean emailsSet = settings.getBoolean("email_switch", false);
        Boolean messagesSet = settings.getBoolean("messages_switch", false);

        swt_notifications.setChecked(notificationsSet);
        swt_email.setChecked(emailsSet);
        swt_telephone.setChecked(messagesSet);

        if (notificationsSet) {
            dnotification[0].setColorFilter(primarycolor, PorterDuff.Mode.SRC_ATOP);

            swt_notifications.setText("Daily Reminder Set");

        }
        if (emailsSet) {
            dEmail[0].setColorFilter(primarycolor, PorterDuff.Mode.SRC_ATOP);
        }
        if (messagesSet) {
            dMessages[0].setColorFilter(primarycolor, PorterDuff.Mode.SRC_ATOP);
        }
/*
        reminderSet = settings.getBoolean("notification_switchkey", false);
        if (reminderSet){
            SharedPreferences remindSettings=getSharedPreferences("reminder_id",0);
            String alarmText=remindSettings.getString("alarm", "Set Daily Reminder");
            notificationsSwitch.setText(alarmText);
        }
*/
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
                    swt_notifications.setText("Daily Reminder Set");

                } else {
                    dnotification[0].setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                    swt_notifications.setText("Set Daily Reminder");
                    cancelAlarm();

                }
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
                    dEmail[0].setColorFilter(primarycolor, PorterDuff.Mode.SRC_ATOP);

                } else {
                    dEmail[0].setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                }
                storeSwitchState("email_switch", isChecked);
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
    public Boolean enableMessagesSwitch() {
        swt_telephone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dMessages[0].setColorFilter(primarycolor, PorterDuff.Mode.SRC_ATOP);
                } else {
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
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(switchName, isChecked);
        editor.commit();
        return true;
    }



    /*
     * Method initialises the alarm
     */
    private void startAlarm(Calendar calendar){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

    /*
     * Method cancels the alarm when the
     * switch is turned off
     */
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Reminder Cancelled", Toast.LENGTH_SHORT).show();
    }

    /*
     * Stores the time in which the
     * alarm is set by the user
     */
    public void storeAlarmTime(String timeText){
        SharedPreferences settings = getSharedPreferences("reminder_id", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("alarm", timeText);
        editor.commit();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.v(TAG, "onTimeSet initialised");
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        Toast.makeText(this, timeText, Toast.LENGTH_SHORT).show();
        storeAlarmTime(timeText);
        swt_notifications.setText(timeText);

        startAlarm(calendar);
    }
}
