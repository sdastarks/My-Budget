package com.example.mybudget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mybudget.Models.Entry;
import com.example.mybudget.Models.WishList;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.time.LocalDate;

import static com.example.mybudget.R.id.edit_wish_fragment;
import static com.example.mybudget.R.id.fragment_placeholder;
import static com.example.mybudget.R.id.frame_wish_fragment;

/**
 * Fragment allows user to view information about current status of savings for selected item
 * from here you can call another fragment to chnage saved amount
 * @author Anastasija Gurejeva
 *
 */
public class WishFragment extends Fragment {
    private static final String TAG = "WishFragment";

    private CircularProgressBar circularProgressBar;
    private TextView wishTitle;
    private TextView wishPrice;
    private TextView balance;
    private TextView savingProgress;
    private TextView txtAvailableBalance;
    private int index;
    protected Boolean inflow;
    private FloatingActionButton onAddSelected;
    private FloatingActionButton onMinusSelected;
    private FloatingActionButton cancelWishFragment;
    private FloatingActionButton editWishFragment;


    public WishFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int dbid =((WishlistActivity) getActivity()).id;
        Log.v(TAG, "id: "+dbid);
        WishList wishwish = ((WishlistActivity) getActivity()).db.returnWish(dbid);
        Log.v(TAG, "Title: "+wishwish.getTitle());


        index = ((WishlistActivity) getActivity()).index;
        Log.d(TAG, "onCreateView: view infaled. Index passed " + index);

        View view = inflater.inflate(R.layout.fragment_wish, container, false);
        wishTitle = view.findViewById(R.id.wish_title);
        wishPrice = view.findViewById(R.id.wish_price);
        balance = view.findViewById(R.id.balance);
        savingProgress = view.findViewById(R.id.saving_progress);
        txtAvailableBalance = view.findViewById(R.id.txt_available_balance);

        //assign value by index from DB
        //DAWNIE
        int bal = ((WishlistActivity) getActivity()).db.balance();
        balance.setText(String.valueOf(bal));

        int progress = 60; // data received from database
        circularProgressBar = (CircularProgressBar) view.findViewById(R.id.progressBar);
        circularProgressBar.setProgress(progress);
        TextView progresstxt = view.findViewById(R.id.txt_progressBar);
        progresstxt.setText(progress + "%");


        onAddSelected = view.findViewById(R.id.floatingActionButton_addTransaction);
        onMinusSelected = view.findViewById(R.id.floatingActionButton_minusTransaction);
        cancelWishFragment = view. findViewById(R.id.floatingActionButton_cancel_wish_fragment);
        editWishFragment = view. findViewById(R.id.floatingActionButton_edit_wish_fragment);

        activateOnAddSelected();
        activateOnMinusSelected();
        activateEditWishFragment();
        activateCancelWishFragment();

        return view;
    }

    //Floating button calls a new fragment,
    // where you can make a transaction to add saved amount

    public void activateOnAddSelected() {

        onAddSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onAddSelected: activated");
                inflow = true;
                ChangeWishInflowOutflowFragment changeWishInflowOutflowFragment = new ChangeWishInflowOutflowFragment();

                //passing data to new fragment
                Bundle args = new Bundle();
                args.putBoolean("inflow", true);
                args.putInt("index", index);
                //calling new fragment
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_wish_fragment, changeWishInflowOutflowFragment)
                        .commit();
                changeWishInflowOutflowFragment.setArguments(args);
                Log.d(TAG, "onClick: arguments passed to fragment.");
            }
        });
    }

    ///Floating button calls a new fragment,
    // where you can make a transaction to minus saved amount

    public void activateOnMinusSelected() {
        onMinusSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onMinusSelected: activated");
                inflow = false;
                ChangeWishInflowOutflowFragment changeWishInflowOutflowFragment = new ChangeWishInflowOutflowFragment();

                //passing data to new fragment
                Bundle args = new Bundle();
                args.putBoolean("inflow", false);
                args.putInt("index", index);
                //calling new fragment
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_wish_fragment, changeWishInflowOutflowFragment)
                        .commit();
                changeWishInflowOutflowFragment.setArguments(args);
                Log.d(TAG, "onClick: arguments passed to fragment.");
            }
        });
    }

    public void activateCancelWishFragment() {
        cancelWishFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WishlistActivity.class);
                startActivity(intent);
            }
        });
    }

    public void activateEditWishFragment() {
        editWishFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditWishFragment editWishFragmentCall = new EditWishFragment();
                Bundle args = new Bundle();
                args.putInt("indexEdit", index);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_wish_fragment, editWishFragmentCall)
                        .commit();
                editWishFragmentCall.setArguments(args);

            }
        });
    }

}
