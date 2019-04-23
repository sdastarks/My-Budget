package com.example.mybudget.WishList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybudget.Models.WishList;
import com.example.mybudget.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

/**
 * Fragment allows user to view information about current status of savings for selected item
 * from here you can call another fragment to chnage saved amount
 * @author Anastasija Gurejeva
 *
 */
public class WishFragment extends Fragment {
    private static final String TAG = "WishFragment";

    private View view;
    private CircularProgressBar circularProgressBar;
    private TextView mwishTitle;
    private TextView mwishPrice;
    private TextView msavingProgress;
    private ImageView wish_picture_fragment;
    private int index;
    private int progress;
    private int dbid;
    protected Boolean inflow;
    private FloatingActionButton onAddSelected;
    private FloatingActionButton onMinusSelected;
    private FloatingActionButton favouriteWish_btn;

    private Button cancelWishFragment;
    private Button editWishFragment;


    WishList wishSelected;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        index = ((WishlistActivity) getActivity()).index;
        Log.d(TAG, "onCreateView: view infaled. Index passed " + index);

        view = inflater.inflate(R.layout.fragment_wish, container, false);
        mwishTitle = view.findViewById(R.id.wish_title_wish_frag);
        mwishPrice = view.findViewById(R.id.wish_price);
        msavingProgress = view.findViewById(R.id.saving_progress);
        wish_picture_fragment = view.findViewById(R.id.wish_picture_fragment);

        //selected wish database id and Wish
        dbid = ((WishlistActivity) getActivity()).id;
        wishSelected = ((WishlistActivity) getActivity()).db.returnWish(dbid);
        wish_picture_fragment.setImageResource(wishSelected.getImage());

        onAddSelected = view.findViewById(R.id.floatingActionButton_addTransaction);
        onMinusSelected = view.findViewById(R.id.floatingActionButton_minusTransaction);

        cancelWishFragment = view. findViewById(R.id.btn_cancel_wish_fragment);
        editWishFragment = view. findViewById(R.id.btn_edit_wish);

        favouriteWish_btn=view.findViewById(R.id.floatingActionButton_favourite_wish_btn);


        setTitle();
        calcProgress();
        setProgressBar();
        activateOnAddSelected();
        activateOnMinusSelected();
        activateEditWishFragment();
        activateCancelWishFragment();
        activateFavouriteWish();

        return view;
    }
    /*
     * Method sets the title of the
     * selected wish
     */
    public void setTitle(){
        mwishTitle.setText(wishSelected.getTitle());
    }
    /*
     * Method calculates the progress of the
     * selected wish
     */
    public void calcProgress(){
        int wishPrice=((WishlistActivity) getActivity()).wishPrice;
        mwishPrice.setText("price: "+wishPrice+" SEK");
        int wishSaved= wishSelected.getSaved();
        progress=wishSaved*100/wishPrice;
        msavingProgress.setText("savings: "+wishSaved+" SEK ("+progress+"%)");
    }
    /*
     * Method sets the state of the progress
     * bar
     */
    public void setProgressBar(){
        circularProgressBar = (CircularProgressBar) view.findViewById(R.id.progressBar);
        circularProgressBar.setProgress(progress);
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
    public void activateFavouriteWish(){
        favouriteWish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "dbid: "+ dbid);
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                sharedPref.edit().putInt("favouriteWish", dbid).apply();
                Toast.makeText(getActivity(), "Your new favourite wish!", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
