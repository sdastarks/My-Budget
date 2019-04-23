package com.example.mybudget.WishList;


import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.Resource;
import com.example.mybudget.Home.MainActivity;
import com.example.mybudget.Models.WishList;
import com.example.mybudget.R;
import com.example.mybudget.WishList.WishlistActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;


/**
 * Fragment allows user to create new Wish
 * Add a target price, title
 * @author Anastasija Gurejeva
 * @author Daniel Beadleson
 *
 */
public class NewWishFragment extends Fragment {
    private static final String TAG = "NewWishFragment";
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
    private TextInputEditText mNewWishTitle;
    private TextInputEditText mNewWishCost;
    private ImageView wishPicture;
    private Button saveNewWish;
    private Button cancelNewWish;

    int drawable = R.drawable.button_wish_clothes;
     //Drawable d = getActivity().getDrawable(R.drawable.button_wish_bike);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_wish, container, false);
        mNewWishTitle = view.findViewById(R.id.wish_title);
        mNewWishCost = view.findViewById(R.id.wish_cost);
        wishPicture = view.findViewById(R.id.image_wish_cathegory);
        saveNewWish = view.findViewById(R.id.btn_save_new_wish);
        cancelNewWish = view.findViewById(R.id.btn_cancel_new_wish);
        activateOnCancelNewWish();
        activateOnSaveNewWish();



        theBikePic = view.findViewById(R.id.bike);
        theBikePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_bike);
                drawable = R.drawable.button_wish_bike;
                //d = getActivity().getDrawable(R.drawable.button_wish_bike);

            }
        });

        clothesPic = view.findViewById(R.id.clothes);
        clothesPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_clothes);
                drawable = R.drawable.button_wish_clothes;
            }
        });

        gadgetsPic = view.findViewById(R.id.gadgets);
        gadgetsPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_gadgets);
                drawable = R.drawable.button_wish_gadgets;
            }
        });

        gamesPic = view.findViewById(R.id.games);
        gamesPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_games);
                drawable = R.drawable.button_wish_games;
            }
        });

        giftPic = view.findViewById(R.id.gift);
        giftPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_gift);
                drawable = R.drawable.button_wish_gift;
            }
        });

        holidayPic = view.findViewById(R.id.holiday);
        holidayPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_holiday);
                drawable = R.drawable.button_wish_holiday;
            }
        });

        iceSkatePic = view.findViewById(R.id.iceskate);
        iceSkatePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_iceskate);
                drawable = R.drawable.button_wish_iceskate;
            }
        });


        petsPic = view.findViewById(R.id.pets);
        petsPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_pets);
                drawable = R.drawable.button_wish_pets;
            }
        });

        scooterPic = view.findViewById(R.id.scooter);
        scooterPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_scooter);
                drawable = R.drawable.button_wish_scooter;
            }
        });

        shoesPic = view.findViewById(R.id.shoes);
        shoesPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_shoes);
                drawable = R.drawable.button_wish_shoes;
            }
        });

        otherPic = view.findViewById(R.id.dream);
        otherPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishPicture.setImageResource(R.drawable.button_wish_dream);
                drawable = R.drawable.button_wish_dream;
            }
        });


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

    /*
     * Method attempts to save a wish
     */


    private void activateOnSaveNewWish() {
        saveNewWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
                int cost = Integer.parseInt(mNewWishCost.getText().toString());
                String wishTitle = mNewWishTitle.getText().toString();
//                    if (cost > 10000000){
//                        mNewWishCost.setError("Wish must be less than 10M SEK");
//                    }
//                    else if (wishTitle.isEmpty()){
//                        mNewWishTitle.setError("Field cannot be empty");
//                    }
//
//                    else if (wishTitle.length()> 25){
//                        mNewWishTitle.setError("Choose a smaller wish name");
//                    }
//                    else {
                WishList wish = new WishList();
                wish.setTitle(wishTitle);
                wish.setCost(cost);
                wish.setSaved(0);
                wish.setImage(drawable);
                mNewWishCost.setError(null);
                ((WishlistActivity) getActivity()).db.addWish(wish);
                Intent intent = new Intent(getActivity(), WishlistActivity.class);
                startActivity(intent);
//                    }
//                }
//                catch (Exception e){
//                    mNewWishCost.setError("Try Again");
//                }
            }
        });
    }

    public void categoryIntoImage(String imagePath) {

    }
}
