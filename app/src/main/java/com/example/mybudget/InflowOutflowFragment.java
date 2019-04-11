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
 *
 */
public class InflowOutflowFragment extends Fragment {

    private static  final String TAG= "InflowOutflowFragment";
    EditText mDescription;
    EditText mAmount;
    Boolean inflow;
    //myDbHelper db = new myDbHelper(InflowOutflowFragment.this, "myDb.db", null, 1);
    /*
     * Method creates the initial state of the
     * fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_inflow_outflow, container, false);

        // the boolean expression inflow will show if the input by the user
        //  is an income or spending

            inflow =((MainActivity) getActivity()).inflow;





        ImageView image =view.findViewById(R.id.image_inflow_outflow);
        if(inflow){
            image.setImageDrawable(getResources().getDrawable(R.drawable.image_inflow));
        }
        else {
            image.setImageDrawable(getResources().getDrawable(R.drawable.image_outflow));
        }
        mDescription= (EditText) view.findViewById(R.id.description);
        mAmount = view.findViewById(R.id.amount);

        return view;

    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        Log.v(TAG, "onViewCreated inititialsed");
        Log.v(TAG, "inflow"+inflow);

        FloatingActionButton saveButton= view.findViewById(R.id.floatingActionButton_saveIncome);
        saveButton.setOnClickListener(new View.OnClickListener() {
            /*
             * Method gets the description and amount of the income
             */
            @Override
            public void onClick(View v) {

                String description = mDescription.getText().toString();
                Log.v(TAG,"description: " +description);
                String sAmount= mAmount.getText().toString();

                if (description.isEmpty() | sAmount.isEmpty()){
                    Toast.makeText(getActivity(),"Both fields must be filled",Toast.LENGTH_SHORT).show();
                }
                else{
                    int amount= Integer.parseInt(sAmount);
                    Log.v(TAG, "amount: "+ amount);

                    Log.v(TAG, "inflow: "+inflow);

                    //DAWNIE...
                    Entry entry = new Entry();
                    //inflow ? entry.setTypeOfEntry(1) : entry.setTypeOfEntry(0);

                    if(inflow)
                        entry.setTypeOfEntry(1);
                    else if(!inflow)
                        entry.setTypeOfEntry(0);

                    entry.setAmount(Float.parseFloat(sAmount));
                    entry.setDate(LocalDate.now());
                    entry.setDesc(description);
                    ((MainActivity) getActivity()).db.addEntry(entry);

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        FloatingActionButton cancelButton = view.findViewById(R.id.floatingActionButton_cancelIncome);
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

}
