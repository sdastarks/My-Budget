package com.example.mybudget.WishList;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybudget.Models.Entry;
import com.example.mybudget.Models.WishList;
import com.example.mybudget.R;

import java.time.LocalDate;


/**
 * Fragment allows the user to transfer
 * money from balance to wish savings
 *
 * @author Anastasija Gurejeva
 * @author Daniel Beadleson
 */
public class ChangeWishInflowOutflowFragment extends Fragment {

    private static final String TAG = "InflowOutflowFragment";
    EditText mAmount;
    TextView wishTitle;
    Boolean addingMoney2Wish;
    WishList wish2Update;
    int dbid;
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
        dbid = ((WishlistActivity) getActivity()).id;
        wish2Update = ((WishlistActivity) getActivity()).db.returnWish(dbid);

        wishTitle= view.findViewById(R.id.wish_title2);
        wishTitle.setText(wish2Update.getTitle());

        balance = ((WishlistActivity) getActivity()).db.balance();
        addingMoney2Wish = getArguments().getBoolean("inflow");
        index = getArguments().getInt("index");
        ImageView image = view.findViewById(R.id.image_inflow_outflow);
        if (addingMoney2Wish) {
            image.setImageDrawable(getResources().getDrawable(R.drawable.image_inflow));
        } else {
            image.setImageDrawable(getResources().getDrawable(R.drawable.image_outflow));
        }
        mAmount = view.findViewById(R.id.amount);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.v(TAG, "onViewCreated inititialsed");
        Log.v(TAG, "inflow" + addingMoney2Wish);

        FloatingActionButton saveButton = view.findViewById(R.id.floatingActionButton_saveTransfer);
        saveButton.setOnClickListener(new View.OnClickListener() {
            /*
             * Method either adds or takes away money from the wish
             * if the user has enough money from their balance
             * and has not entered 0 SEK
             */
            @Override
            public void onClick(View v) {

                String sAmount = mAmount.getText().toString();
                if (sAmount.isEmpty()) {
                    Toast.makeText(getActivity(), "You have transferred 0 sek", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(sAmount) > balance) {
                    Toast.makeText(getActivity(), "You don't have enough money in your account", Toast.LENGTH_SHORT).show();
                } else {
                    //Adding an entry to log
                    int amount = Integer.parseInt(sAmount);
                    String wishName;
                    Entry entry = new Entry();
                    entry.setDate(LocalDate.now());
                    entry.setAmount(Integer.parseInt(sAmount));

                    if (addingMoney2Wish) {
                        addMoney2Wish(entry, amount, wish2Update, dbid);

                    } else if (!addingMoney2Wish) {
                        takeMoneyFromWish(entry, amount, wish2Update, dbid);
                    }
                }

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
    /*
     * Method adds money to the wish
     * if the amount doesnt exceed the total price
     * of the wish
     */
    public void addMoney2Wish(Entry entry, Integer amount, WishList wish2Update, int dbid) {
        entry.setTypeOfEntry(2);
        String entryDescription = ((WishlistActivity) getActivity()).mWishNames.get(((WishlistActivity) getActivity()).index)
                + " wishlist transfer";
        if (amount > (wish2Update.getCost() - wish2Update.getSaved())) {
            Toast.makeText(getActivity(), "Your goal doesn' t need that much money, try " +
                    (wish2Update.getCost() - wish2Update.getSaved()) + " SEK", Toast.LENGTH_LONG).show();
        } else if ((wish2Update.getCost() - wish2Update.getSaved()) == 0) {
            //TODO show some avatar when reach the goal
            Toast.makeText(getActivity(), "You have reached your goal", Toast.LENGTH_SHORT).show();
        } else {
            ((WishlistActivity) getActivity()).db.updateWish(dbid, wish2Update.getTitle()
                    , wish2Update.getCost(), amount + wish2Update.getSaved(),
                    wish2Update.getImage());
            entry.setDesc(entryDescription);
            ((WishlistActivity) getActivity()).db.addEntry(entry);
        }
    }
    /*
     * Method takes away money from the list
     * if the amount doesnt exceed the total
     * amount of money allocated to the wish
     */
    public void takeMoneyFromWish(Entry entry, Integer amount, WishList wish2Update, int dbid) {
        entry.setTypeOfEntry(1);
        String entryDescription = ((WishlistActivity) getActivity()).mWishNames.get(((WishlistActivity) getActivity()).index)
                + " wishlist return to balance";
        if (amount < wish2Update.getSaved()) {
            ((WishlistActivity) getActivity()).db.updateWish(dbid, wish2Update.getTitle()
                    , wish2Update.getCost(), wish2Update.getSaved() - amount,
                    wish2Update.getImage());
            entry.setDesc(entryDescription);
            ((WishlistActivity) getActivity()).db.addEntry(entry);
        } else {
            Toast.makeText(getActivity(), "Gotta be less than " +
                            (wish2Update.getSaved()) +
                            " SEK"
                    , Toast.LENGTH_SHORT).show();
        }
    }
}
