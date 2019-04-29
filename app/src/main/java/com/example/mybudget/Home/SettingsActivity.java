package com.example.mybudget.Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.mybudget.AvatarChangeActivity;
import com.example.mybudget.R;
import com.example.mybudget.myDbHelper;

public class SettingsActivity extends AvatarChangeActivity {

    private static final String TAG = "SettingsActivityLog";

    private Button btn_save;
    private Button btn_exit;
    private Switch swt_notifications;
    private Switch swt_email;
    private Switch swt_telephone;
    private ImageView imageViewHero;
    private Drawable d;

    myDbHelper db = new myDbHelper(this, "myDb.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btn_save = findViewById(R.id.btn_save_settings);
        btn_exit = findViewById(R.id.btn_exit_settings);
        swt_notifications = findViewById(R.id.switch_notifications);
        swt_email = findViewById(R.id.switch_email);
        swt_telephone = findViewById(R.id.switch_messages);
        imageViewHero = findViewById(R.id.avatarImageSettings);


        setAvatar();
        previousSwitchStates();
        exitSettings();
        saveSettings();
        notificationsSwitch();
        enableEmailSwitch();
        enableMessagesSwitch();


    }

    /**
     * Method sets the avatar based on the theme
     *
     * @return whether a theme has been set
     */
    public Boolean setAvatar() {
        if (imageResId != -1) {
            d = getDrawable(imageResId);
            imageViewHero.setImageDrawable(d);
            return true;
        }
        return false;
    }

    /**
     * Method sets the state of the switch bars to
     * their original state
     */
    public void previousSwitchStates(){
        SharedPreferences settings = getSharedPreferences("switch_state_id", 0);
        swt_notifications.setChecked(settings.getBoolean("notification_switch",false));
        swt_email.setChecked(settings.getBoolean("email_switch",false));
        swt_telephone.setChecked(settings.getBoolean("messages_switch",false));
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
     * Method saves the state of the settings
     */
    public Boolean saveSettings() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save settings
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
                    //DialogFragment timePicker = new TimePickerFragment();
                    //timePicker.show(getSupportFragmentManager(), "time picker");
                    swt_notifications.setText("Daily Reminder Set");
                    //swt_notifications.setBackgroundColor(getResources().getColor(R.color.white));

                } else {
                    swt_notifications.setText("Set Daily Reminder");
                    //cancelAlarm();
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
                } else {
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
                } else {
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
        SharedPreferences settings = getSharedPreferences("switch_state_id", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(switchName, isChecked);
        editor.commit();
        return true;
    }


}
