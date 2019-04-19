package com.example.mybudget.WishList;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mybudget.Models.WishList;
import com.example.mybudget.R;
import com.example.mybudget.WishList.WishlistActivity;


/**
 * Fragment allows user to create new Wish
 * Add a target price, title
 * @author Anastasija Gurejeva
 *
 */
public class NewWishFragment extends Fragment {
    private static final String TAG = "NewWishFragment";
    private EditText mNewWishTitle;
    private EditText mNewWishCost;
    private ImageView wishCathegory;
    private Button saveNewWish;
    private Button cancelNewWish;


    public NewWishFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_wish, container, false);
        mNewWishTitle = view.findViewById(R.id.new_wish_title);
        mNewWishCost = view.findViewById(R.id.new_wish_cost);
        wishCathegory = view.findViewById(R.id.new_wish_cathegory);
        saveNewWish = view.findViewById(R.id.btn_save__new_wish);
        cancelNewWish = view.findViewById(R.id.btn_cancel_new_wish);
        activateOnCancelNewWish();
        activateOnSaveNewWish();
        return view;
    }

    private void activateOnCancelNewWish() {
        cancelNewWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WishlistActivity.class);
                startActivity(intent);
            }
        });
    }

    private void activateOnSaveNewWish() {
        saveNewWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WishList wish = new WishList();
                wish.setTitle(mNewWishTitle.getText().toString());
                wish.setCost(Integer.parseInt(mNewWishCost.getText().toString()));
                wish.setSaved(0);
                //wish.setImage(wishPicture);
                ((WishlistActivity) getActivity()).db.addWish(wish);
                Intent intent = new Intent (getActivity(), WishlistActivity.class);
                startActivity(intent);
            }
        });
    }


        public void bike(View view){
          //wishPicture.setImageResource();
          

    }

}
