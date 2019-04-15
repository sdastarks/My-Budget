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
 * Fragment allows the user to transfer
 * money from balance to wish savings
 * @author Anastasija Gurejeva
 *
 */
public class ChangeWishInflowOutflowFragment extends Fragment {

    private static final String TAG = "InflowOutflowFragment";
    EditText mAmount;
    Boolean inflow;
    int index;
    int balance;

    /*
     * Method creates the initial state of the
     * fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_wish_inflow_outflow, container, false);

       balance= ((WishlistActivity) getActivity()).db.balance();
       Log.v(TAG, "balance: "+balance);

        // the boolean expression inflow will show if the input by the user
        // is an transfer to savings or removing from savings to balance
        // boolean is passed from wish fragment

        inflow = getArguments().getBoolean("inflow");
        index = getArguments().getInt("index");
        Log.d(TAG, "onCreateView: index passe " + index);

        ImageView image = view.findViewById(R.id.image_inflow_outflow);
        if (inflow) {
            image.setImageDrawable(getResources().getDrawable(R.drawable.image_inflow));
        } else {
            image.setImageDrawable(getResources().getDrawable(R.drawable.image_outflow));
        }

        mAmount = view.findViewById(R.id.amount);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.v(TAG, "onViewCreated inititialsed");
        Log.v(TAG, "inflow" + inflow);

        FloatingActionButton saveButton = view.findViewById(R.id.floatingActionButton_saveTransfer);
        saveButton.setOnClickListener(new View.OnClickListener() {
            /*
             * Method gets the transaction amount
             */
            @Override
            public void onClick(View v) {

                String sAmount = mAmount.getText().toString();


                if (sAmount.isEmpty()){
                    Toast.makeText(getActivity(),"You have transfered 0 sek",Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(sAmount) > balance) {
                    Toast.makeText(getActivity(), "You don't have enough money on your account", Toast.LENGTH_SHORT).show();
                } else {
                    int amount = Integer.parseInt(sAmount);
                    Log.v(TAG, "TransactionAmount: " + amount);
                }

                //Adding an entry to log
                String wish = "";
                Entry entry = new Entry();
                entry.setDate(LocalDate.now());
                entry.setAmount(Integer.parseInt(sAmount));

                if (inflow) {
                    entry.setTypeOfEntry(2);
                    wish = ((WishlistActivity) getActivity()).mWishNames.get(((WishlistActivity) getActivity()).index)
                            + " wishlist transfer";
                }
                else if (!inflow) {
                    entry.setTypeOfEntry(1);
                    wish = ((WishlistActivity) getActivity()).mWishNames.get(((WishlistActivity) getActivity()).index)
                            + " wishlist return to balance";
                }
                entry.setDesc(wish);

                Log.v(TAG, "wish: "+wish);

                ((WishlistActivity) getActivity()).db.addEntry(entry);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_wish_fragment, new WishFragment())
                        .commit();
            }
        });

        FloatingActionButton cancelTransactionButton = view.findViewById(R.id.floatingActionButton_cancelTransaction);
        cancelTransactionButton.setOnClickListener(new View.OnClickListener() {
            /*
             * Method sends the user back to the previous fragment
             * when the cancel button is initialised
             */
            @Override
            public void onClick(View v) {
                Log.v(TAG, "cancel button initialised");

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_wish_fragment, new WishFragment())
                        .commit();
            }
        });

    }

}
