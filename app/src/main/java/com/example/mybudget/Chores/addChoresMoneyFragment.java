package com.example.mybudget.Chores;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybudget.Home.SettingsActivity;
import com.example.mybudget.Models.Entry;
import com.example.mybudget.R;
import com.example.mybudget.SendMailTask;

import java.time.LocalDate;
import java.util.function.ToDoubleBiFunction;

/**
 * Fragment allows the user to enter
 * an income, that will affect the balance
 *
 * @author Alaa Al Sakka
 */

public class addChoresMoneyFragment extends Fragment {

    private static final String TAG = "addChoresMoneyFragment";
    private EditText mChoresDescription;
    private EditText mChoresAmount;
    private Boolean inflow;
    private ImageView mImageViewHero;
    private TextView mBalance;
    private int balance;
    private TextView mFragmentTitle;
    private String userParentEmail;


    private Bundle bundle;

    /*
     * Method creates the initial state of the
     * fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_chores_money, container, false);

        balance = ((ChoresActivity) getActivity()).db.balance();
        Log.d(TAG, "onCreateView: balance is" + balance);
        mBalance = view.findViewById(R.id.balance_choreFragment);
        mBalance.setText(balance + " SEK");
        mImageViewHero = view.findViewById(R.id.imageViewHero_chores);
        mChoresDescription = (EditText) view.findViewById(R.id.choresDescription);
        mChoresAmount = view.findViewById(R.id.choresAmount);
        mChoresAmount.setText((getArguments().getInt("amount")) + "");

        if (getArguments().getString("title").equals("Specify here!")) {
            mChoresDescription.setHint(getArguments().getString("title"));
        } else {
            mChoresDescription.setText(getArguments().getString("title"));
        }

        mFragmentTitle = view.findViewById(R.id.title_money_from_chore_fragment);
        mFragmentTitle.setText("Chores Money");
        setAvatar();


        return view;

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.v(TAG, "onViewCreated inititialsed");

        Button saveButton = view.findViewById(R.id.btn_saveChoreTransfer);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Method gets the description and amount of the income
                 */

                // TODO: 2019-04-29 Add dialog to submit parents email if it is null and save entry in data base

                try {
                    String description = mChoresDescription.getText().toString().trim();
                    Log.v(TAG, "description: " + description);
                    String sAmount = mChoresAmount.getText().toString().trim();
                    int amount = Integer.parseInt(sAmount);
                    if (description.isEmpty()) {
                        mChoresDescription.setError("Field must be filled");
                    } else if (sAmount.isEmpty()) {
                        mChoresAmount.setError("Field must be filled");
                    } else if (Integer.parseInt(sAmount) <= 0) {
                        mChoresAmount.setError("Must be larger than 0");
                    } else if (description.contains("Specify here!")) {
                        mChoresDescription.setError("Enter a name");
                    } else if (description.length() > 24) {
                        mChoresDescription.setError("Must be less than 24 characters");
                    } else if (amount > 10000) {
                        mChoresAmount.setError("You kidding?");
                    } else {

                        addEntry(amount, description);
                        checkSMSEnabled();
                        //sendEmail();  //TODO: Perhaps delete this sendEMail() method as your using sms???
                    }
                } catch (Exception e) {
                    mChoresAmount.setError("Try Again");
                }

            }
        });

        Button cancelButton = view.findViewById(R.id.btn_cancelChoreFragment);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            /*
             * Method sends the user back to the main menu
             * when the cancel button is initialised
             */
            @Override
            public void onClick(View v) {
                Log.v(TAG, "cancel button initialised");
                exit();
            }
        });

    }
    /**
     * Method adds the entry to the database
     */
    public void addEntry(int amount, String description){
        Entry entry = new Entry();
        entry.setTypeOfEntry(3);
        entry.setAmount(amount);
        entry.setDate(LocalDate.now());
        entry.setDesc(description);
        ((ChoresActivity) getActivity()).db.addEntry(entry);
    }

    /**
     * Method checks the setting preferences
     * to see if the user would like to send
     * a payment reminder via sms
     *
     * @ Daniel Beadleson
     */
    public void checkSMSEnabled() {
        Boolean smsEnabled = getActivity().getSharedPreferences(SettingsActivity.SETTINGSPREFS_NAME,
                0).getBoolean(SettingsActivity.MESSAGEPREFS, false);
        if (smsEnabled) {
            sendSMS();
        }
        else {
            exit();
        }
    }

    /**
     * Method allows the user to send a
     * payment reminder via sms
     */
    public void sendSMS() {
        SendSms mFrag = new SendSms();
        Bundle bundle = new Bundle();
        bundle.putString("desc", mChoresDescription.getText().toString());
        bundle.putString("amount", mChoresAmount.getText().toString());
        mFrag.setArguments(bundle);
        FragmentTransaction t = getFragmentManager().beginTransaction();
        t.replace(R.id.check, mFrag);
        t.commit();
        

    }

    public void setAvatar() {
        SharedPreferences settings = getActivity().getSharedPreferences("themePreferenceFile", 0);
        int imageResId = settings.getInt("imageResId", -1);
        if (imageResId != -1) {
            Drawable d = getActivity().getDrawable(imageResId);
            mImageViewHero.setImageDrawable(d);
        }
    }
    public void exit(){
        Intent intent = new Intent(getActivity(), ChoresActivity.class);
        startActivity(intent);
    }
}
