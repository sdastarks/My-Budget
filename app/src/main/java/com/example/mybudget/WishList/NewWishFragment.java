package com.example.mybudget.WishList;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private EditText title;
    private EditText cost;
    private ImageView wishPicture;
    private FloatingActionButton floatingActionButton_save_wish;
    private ImageView theBikePic;
    private ImageView clothesPic;
    private ImageView gadgetsPic;
    private ImageView gamesPic;
    private ImageView giftPic;
    private ImageView holidayPic;
    private ImageView iceSkatePic;
    private ImageView petsPic;
    private ImageView scooterPic;
    private ImageView shoesPic;
    private ImageView otherPic;




    public NewWishFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_wish, container, false);
        title = view.findViewById(R.id.editText_title);
        cost = view.findViewById(R.id.editText_cost);
        wishPicture = view.findViewById(R.id.wish_picture);
        floatingActionButton_save_wish = view.findViewById(R.id.floatingActionButton_save_wish);

        floatingActionButton_save_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WishList wish = new WishList();
                wish.setTitle(title.getText().toString());
                wish.setCost(Integer.parseInt(cost.getText().toString()));
                wish.setSaved(0);
                //wish.setImage(wishPicture);
                ((WishlistActivity) getActivity()).db.addWish(wish);
                Intent intent = new Intent(getActivity(), WishlistActivity.class);
                startActivity(intent);

            }
        });


        //Method to exit fragment

        FloatingActionButton exitNewWish = view.findViewById(R.id.floatingActionButton_exit_new_wish);
        exitNewWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WishlistActivity.class);
                startActivity(intent);
            }
        });


        theBikePic = view.findViewById(R.id.bike);
        theBikePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_bike);
            }
        });

        clothesPic = view.findViewById(R.id.clothes);
        clothesPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_clothes);
            }
        });

        gadgetsPic = view.findViewById(R.id.gadgets);
        gadgetsPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_gadgets);
            }
        });

        gamesPic = view.findViewById(R.id.games);
        gamesPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_games);
            }
        });

        giftPic = view.findViewById(R.id.gift);
        giftPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_gift);
            }
        });

        holidayPic = view.findViewById(R.id.holiday);
        holidayPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_holiday);
            }
        });

        iceSkatePic = view.findViewById(R.id.iceskate);
        iceSkatePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_iceskate);
            }
        });

        petsPic = view.findViewById(R.id.pets);
        petsPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_pets);
            }
        });

        scooterPic = view.findViewById(R.id.scooter);
        scooterPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_scooter);
            }
        });

        shoesPic = view.findViewById(R.id.shoes);
        shoesPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_shoes);
            }
        });

        otherPic = view.findViewById(R.id.dream);
        otherPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_dream);
            }
        });





        return view;
    }

}
