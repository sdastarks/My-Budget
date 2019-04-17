package com.example.mybudget;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mybudget.Models.Entry;

import java.time.LocalDate;

/**
 * Fragment allows the user to enter
 * an income, that will affect the balance
 *
 * @author Daniel Beadleson
 */

public class addChoresMoney extends Fragment {

    private static final String TAG = "addChoresMoney";
    EditText choresDescription;
    EditText choresAmount;
    Boolean inflow;
    private Bundle bundle;

    /*
     * Method creates the initial state of the
     * fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_chores_money, container, false);

        // the boolean expression inflow will show if the input by the user
        //  is an income or spending
        inflow = ((ChoresActivity) getActivity()).inflow;

        ImageView image = view.findViewById(R.id.choresImage_inflow_outflow);
        image.setImageDrawable(getResources().getDrawable(R.drawable.image_inflow));
        choresDescription = (EditText) view.findViewById(R.id.choresDescription);
        choresAmount = view.findViewById(R.id.choresAmount);
        choresDescription.setText(getArguments().getString("title"));
        choresAmount.setText((getArguments().getInt("amount"))+"");

        return view;

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.v(TAG, "onViewCreated inititialsed");
        Log.v(TAG, "inflow" + inflow);

        FloatingActionButton saveButton = view.findViewById(R.id.choresFloatingActionButton_saveIncome);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * Method gets the description and amount of the income
                 */
                String description = choresDescription.getText().toString();
                Log.v(TAG, "description: " + description);
                String sAmount = choresAmount.getText().toString();

                if (description.isEmpty() | sAmount.isEmpty()) {
                    Toast.makeText(getActivity(), "Both fields must be filled", Toast.LENGTH_SHORT).show();
                } else {
                    int amount = Integer.parseInt(sAmount);
                    Log.v(TAG, "amount: " + amount);
                    //DATABASE
                    Entry entry = new Entry();
                    entry.setTypeOfEntry(3);
                    entry.setAmount(amount);
                    entry.setDate(LocalDate.now());
                    entry.setDesc(description);
                    ((ChoresActivity) getActivity()).db.addEntry(entry);

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        FloatingActionButton cancelButton = view.findViewById(R.id.choresFloatingActionButton_cancelIncome);
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
}
