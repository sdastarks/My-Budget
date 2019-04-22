package com.example.mybudget.Home;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybudget.Models.Entry;
import com.example.mybudget.R;
import com.example.mybudget.WishList.WishlistActivity;

import java.time.LocalDate;


/**
 * Fragment allows the user to enter
 * an income, that will affect the balance
 *
 * @author Daniel Beadleson
 */
public class InflowOutflowFragment extends Fragment {

    private static final String TAG = "InflowOutflowFragment";
    private EditText mDescription;
    private EditText mAmount;
    private Boolean inflow;
    private ImageView mImageViewHero;
    private TextView mBalance;
    private int balance;
    private TextView mFragmentTitle;

    //myDbHelper db = new myDbHelper(InflowOutflowFragment.this, "myDb.db", null, 1);
    /*
     * Method creates the initial state of the
     * fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inflow_outflow, container, false);

        // the boolean expression inflow will show if the input by the user
        //  is an income or spending

        inflow = ((MainActivity) getActivity()).inflow;
        mBalance = view.findViewById(R.id.balance_inflow_outflow);
        mImageViewHero = view.findViewById(R.id.imageViewHero_home);
        mDescription = (EditText) view.findViewById(R.id.description_home);
        mAmount = view.findViewById(R.id.amount_home);
        mFragmentTitle = view.findViewById(R.id.title_money_in_out_fragment);

        setBalance();
        setAvatar();

        if (inflow) {
            mFragmentTitle.setText("Money received");
        } else {
            mFragmentTitle.setText("Money Spent");
        }

        return view;

    }

    /*
     * Method sets the balance
     */
    public void setBalance() {
        balance = ((MainActivity) getActivity()).updateBalance();
        if (balance > 999999) {
            mBalance.setText(balance / 1000000 + "M SEK");
        } else if (balance > 999) {
            mBalance.setText(balance / 1000 + "k SEK");
        } else {
            mBalance.setText(balance + " SEK");
        }
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.v(TAG, "onViewCreated inititialsed");
        Log.v(TAG, "inflow" + inflow);
      
        Button saveButton = view.findViewById(R.id.btn_saveIncome);
        saveButton.setOnClickListener(new View.OnClickListener() {
            /*
             * Method gets the description and amount of the income
             */
            @Override
            public void onClick(View v) {


                try {
                    String description = mDescription.getText().toString();
                    Log.v(TAG, "description: " + description);
                    String sAmount = mAmount.getText().toString();

                    if (description.isEmpty()) {
                        mDescription.setError("Field must be filled");
                    } else if (sAmount.isEmpty()) {
                        mAmount.setError("Field must be filled");
                    } else if (Integer.parseInt(sAmount) > ((MainActivity) getActivity()).db.balance() && !inflow) {
                        mAmount.setError("You don't have enough money in your account");
                    } else if (Integer.parseInt(sAmount) > 10000 && inflow) {
                        mAmount.setError("Are you a high roller");
                    } else {
                        int amount = Integer.parseInt(sAmount);
                        Entry entry = new Entry();

                        if (inflow) {
                            entry.setTypeOfEntry(1);
                            addEntry(amount, description, entry);
                            ((MainActivity) getActivity()).db.addEntry(entry);
                        } else if (!inflow) {
                            entry.setTypeOfEntry(0);
                            addEntry(amount, description, entry);
                            ((MainActivity) getActivity()).db.addEntry(entry);
                        }

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    mAmount.setError("Try Again");
                }
            }
        });

        Button cancelButton = view.findViewById(R.id.btn_cancelIncome);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            /*
             * Method sends the user back to the main menu
             * when the cancel button is initialised
             */
            @Override
            public void onClick(View v) {
                Log.v(TAG, "cancel button initialised");
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
     * Method sets the avatar image from system
     * preferences
     */
    public void setAvatar() {
        SharedPreferences settings = getActivity().getSharedPreferences("themePreferenceFile", 0);
        int imageResId = settings.getInt("imageResId", -1);
        if (imageResId != -1) {
            Drawable d = getActivity().getDrawable(imageResId);
            mImageViewHero.setImageDrawable(d);
        }
    }

    public Entry addEntry(int amount, String desc, Entry entry) {
        entry.setAmount(amount);
        entry.setDesc(desc);
        entry.setDate(LocalDate.now());
        return entry;
    }

}
