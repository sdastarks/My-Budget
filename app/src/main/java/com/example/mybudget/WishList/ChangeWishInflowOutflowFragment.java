package com.example.mybudget.WishList;


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

import com.example.mybudget.Home.MainActivity;
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
    private EditText mAmount;
    private TextView mfragmentTitle;
    private TextView mbalance;
    private ImageView mimageViewHero;
    private Boolean addingMoney2Wish;
    private WishList wish2Update;
    private Button btn_cancelTransaction;
    private Button btn_saveTransfer;
    private View view;
    private int dbid;
    private int index;
    private int balance;
    /*
     * Method creates the initial state of the
     * fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_change_wish_inflow_outflow, container, false);

        mfragmentTitle=view.findViewById(R.id.title_change_money_fragment);
        mimageViewHero=view.findViewById(R.id.imageViewHero_wishlist);
        mAmount = view.findViewById(R.id.amount);
        btn_cancelTransaction=view.findViewById(R.id.btn_cancelTransaction);
        btn_saveTransfer=view.findViewById(R.id.btn_saveTransfer);
        mbalance=view.findViewById(R.id.balance);

        dbid = ((WishlistActivity) getActivity()).id;
        wish2Update = ((WishlistActivity) getActivity()).db.returnWish(dbid);
        balance = ((WishlistActivity) getActivity()).db.balance();
        mbalance.setText(String.valueOf(balance));
        addingMoney2Wish = getArguments().getBoolean("inflow");
        index = getArguments().getInt("index");

        if (addingMoney2Wish) {
            mfragmentTitle.setText("Add Money to Wish");
        } else {
            mfragmentTitle.setText("Remove Money from Wish");
        }

        setAvatar();

        return view;
    }
    /*
     * Method sets the avatar image from system
     * preferences
     */
    public void setAvatar(){
        SharedPreferences settings = getActivity().getSharedPreferences("themePreferenceFile", 0);
        int imageResId = settings.getInt("imageResId", -1);
        if(imageResId != -1){
            Drawable d=getActivity().getDrawable(imageResId);
            mimageViewHero.setImageDrawable(d);
        }
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.v(TAG, "onViewCreated inititialsed");
        Log.v(TAG, "inflow" + addingMoney2Wish);

        btn_saveTransfer.setOnClickListener(new View.OnClickListener() {
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

        btn_cancelTransaction.setOnClickListener(new View.OnClickListener() {
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
