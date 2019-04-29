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

import com.example.mybudget.Models.Entry;
import com.example.mybudget.R;
import com.example.mybudget.SendMailTask;

import java.time.LocalDate;
import java.util.function.ToDoubleBiFunction;

/**
 * Fragment allows the user to enter
 * an income, that will affect the balance
 *
 * @author Daniel Beadleson
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
                /*
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

                        userParentEmail = "nastasyja@gmail.com";
                         //userParent email should be added and stored in data base
                        // TODO: 2019-04-29   userParentEmail = ((ChoresActivity) getActivity()).db.getUser().getUserParentsMail()
                        String emailBody = "Your child completed chore: " + mChoresDescription.getText() + " \n Payment for chore: " + mChoresAmount.getText() +
                                "\n please approve: ";
                        new SendMailTask().execute(userParentEmail, emailBody);
                        Toast toast = Toast.makeText(getActivity(),"Completed chore status is sent to your parents email ",Toast.LENGTH_LONG);
                        toast.show();

                        Log.v(TAG, "amount: " + amount);
                        //DATABASE
                        Entry entry = new Entry();
                        entry.setTypeOfEntry(3);
                        entry.setAmount(amount);
                        entry.setDate(LocalDate.now());
                        entry.setDesc(description);
                        ((ChoresActivity) getActivity()).db.addEntry(entry);

                        FragmentTransaction t = getFragmentManager().beginTransaction();
                        Fragment mFrag = new SendSms();
                        t.replace(R.id.check, mFrag);
                        t.commit();
                       // startActivity(intent);
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
                Intent intent = new Intent(getActivity(), ChoresActivity.class);
                startActivity(intent);
            }
        });

    }

    public void setAvatar() {
        SharedPreferences settings = getActivity().getSharedPreferences("themePreferenceFile", 0);
        int imageResId = settings.getInt("imageResId", -1);
        if (imageResId != -1) {
            Drawable d = getActivity().getDrawable(imageResId);
            mImageViewHero.setImageDrawable(d);
        }
    }
}
