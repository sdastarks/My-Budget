package com.example.mybudget.WishList;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mybudget.Models.WishList;
import com.example.mybudget.R;


/**
 * Fragment allows user to create new Wish
 * Add a target price, title
 *
 * @author Anastasija Gurejeva
 * @author Daniel Beadleson
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
    private int drawable;
    private FloatingActionButton completed_wishes;

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
        completed_wishes = getActivity().findViewById(R.id.completed_wishes);
        completed_wishes.hide();

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

    /**
     * Method saves a wish if all constraints
     * are avoided
     */

    private void activateOnSaveNewWish() {
        saveNewWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Drawable: " + drawable);
                try {
                    int cost = Integer.parseInt(mNewWishCost.getText().toString().trim());
                    String wishTitle = mNewWishTitle.getText().toString().trim();
                    if (cost > 10000000) {
                        mNewWishCost.setError("Wish must be less than 10M SEK");
                    } else if (cost == 0) {
                        mNewWishCost.setError("Wish price can't be 0 SEK");
                    } else if (wishTitle.isEmpty()) {
                        mNewWishTitle.setError("Field cannot be empty");
                    } else if (wishTitle.length() > 18) {
                        mNewWishTitle.setError("Wish must be less than 18 characters");
                    } else if (((WishlistActivity) getActivity()).db.findWishList(wishTitle) != null) {
                        mNewWishTitle.setError("Wish already exists");
                    } else if (drawable == 0) {
                        drawable = R.drawable.button_wish_dream;
                        addWishEntry(wishTitle, cost);
                    } else {

                        addWishEntry(wishTitle, cost);
                    }
                } catch (Exception e) {
                    mNewWishCost.setError("Try Again");
                }
            }

        });
    }

    /**
     * Method adds a wish entry in
     * the db
     *
     * @param wishTitle
     * @param cost
     */
    public void addWishEntry(String wishTitle, int cost) {
        WishList wish = new WishList();
        wish.setTitle(wishTitle);
        wish.setCost(cost);
        wish.setSaved(0);
        wish.setImage(getURLForResource(drawable));
        mNewWishCost.setError(null);
        ((WishlistActivity) getActivity()).db.addWish(wish);
        Intent intent = new Intent(getActivity(), WishlistActivity.class);
        startActivity(intent);
    }

    /**
     * Method returns an image url
     * from the app
     *
     * @param resourceId
     * @return URL
     */
    public String getURLForResource(int resourceId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId).toString();

    }
}
