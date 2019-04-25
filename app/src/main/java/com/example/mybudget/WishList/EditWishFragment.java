package com.example.mybudget.WishList;


import android.content.Intent;
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

import com.example.mybudget.Models.WishList;
import com.example.mybudget.R;
import com.example.mybudget.WishList.WishlistActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditWishFragment extends Fragment {

    private static final String TAG = "editWishFragment";
    private EditText meditTitle;
    private EditText meditCost;
    private ImageView editWishPicture;
    private Button btn_exitEditWish;
    private Button btn_saveEditWish;
    private FloatingActionButton btn_deletwish;
    private int index;
    private int dbid;
    private WishList wish2Edit;
    View view;
    private int drawable;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_edit_wish, container, false);

        index = getArguments().getInt("indexEdit");

        meditTitle = view.findViewById(R.id.edit_wish_title);
        meditCost = view.findViewById(R.id.edit_wish_cost);
        btn_exitEditWish = view.findViewById(R.id.btn_cancel_edit_wish);
        btn_saveEditWish = view.findViewById(R.id.btn_save_edit_wish);
        btn_deletwish = view.findViewById(R.id.floatingActionButton_delete_wish);
        editWishPicture = view.findViewById(R.id.edit_wish_picture);

        dbid = ((WishlistActivity) getActivity()).id;
        wish2Edit = ((WishlistActivity) getActivity()).db.returnWish(dbid);
        editWishPicture.setImageResource(wish2Edit.getImage());
        meditTitle.setText(wish2Edit.getTitle());
        meditCost.setText(String.valueOf(wish2Edit.getCost()));
        drawable = wish2Edit.getImage();

        ImageView theBikePic = view.findViewById(R.id.bike_edit);
        theBikePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_bike);
                drawable = R.drawable.button_wish_bike;
            }
        });

        ImageView clothesPic = view.findViewById(R.id.clothes_edit);
        clothesPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_clothes);
                drawable = R.drawable.button_wish_clothes;
            }
        });

        ImageView gadgetsPic = view.findViewById(R.id.gadgets_edit);
        gadgetsPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_gadgets);
                drawable = R.drawable.button_wish_gadgets;
            }
        });

        ImageView gamesPic = view.findViewById(R.id.games_edit);
        gamesPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_games);
                drawable = R.drawable.button_wish_games;
            }
        });

        ImageView giftPic = view.findViewById(R.id.gift_edit);
        giftPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_gift);
                drawable = R.drawable.button_wish_gift;
            }
        });

        ImageView holidayPic = view.findViewById(R.id.holiday_edit);
        holidayPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_holiday);
                drawable = R.drawable.button_wish_holiday;
            }
        });

        ImageView iceSkatePic = view.findViewById(R.id.iceskate_edit);
        iceSkatePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_iceskate);
                drawable = R.drawable.button_wish_iceskate;
            }
        });


        ImageView petsPic = view.findViewById(R.id.pets_edit);
        petsPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_pets);
                drawable = R.drawable.button_wish_pets;
            }
        });

        ImageView scooterPic = view.findViewById(R.id.scooter_edit);
        scooterPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_scooter);
                drawable = R.drawable.button_wish_scooter;
            }
        });

        ImageView shoesPic = view.findViewById(R.id.shoes_edit);
        shoesPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_shoes);
                drawable = R.drawable.button_wish_shoes;
            }
        });

        ImageView otherPic = view.findViewById(R.id.dream_edit);
        otherPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWishPicture.setImageResource(R.drawable.button_wish_dream);
                drawable = R.drawable.button_wish_dream;
            }
        });


        activateOnExitEditWish();
        activateOnSaveEditWish();
        activateDeleteWish();
        return view;
    }

    /*
     * Method creates a dialog fragment allowing the user
     * to delete a wish or abort the procedure
     */
    public void activateDeleteWish() {
        btn_deletwish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteWishDialog deleteDialog = new DeleteWishDialog();
                Bundle args = new Bundle();
                args.putInt("indexEdit", index);
                deleteDialog.setArguments(args);
                deleteDialog.show(getActivity().getSupportFragmentManager(), "delete dialog");
            }
        });
    }

    private void activateOnExitEditWish() {

        btn_exitEditWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WishlistActivity.class);
                startActivity(intent);
            }
        });

    }

    private void activateOnSaveEditWish() {
        btn_saveEditWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int dbid = ((WishlistActivity) getActivity()).id;
                    String title = meditTitle.getText().toString().trim();
                    int cost = Integer.parseInt(meditCost.getText().toString());


                    if (cost > 10000000) {
                        meditCost.setError("Wish must be less than 10M SEK");
                    } else if (cost <= 0) {
                        meditCost.setError("Must be greater than 0 SEK");
                    } else if (wish2Edit.getSaved() > cost) {
                        meditCost.setError("You've already saved " + wish2Edit.getSaved() + " SEK");
                    } else if (title.isEmpty()) {
                        Log.v(TAG, "title: " + title + "cost: " + cost);
                        updateWish();
                    } else if (title.length() > 18) {
                        meditTitle.setError("Wish must be less than 18 characters");
                    } else {
                        updateWish();
                    }

                } catch (Exception e) {
                    if (meditCost.getText().toString().trim().isEmpty()) {
                        Log.v(TAG, "if statment initialsed");
                        Log.v(TAG, "title: " + meditTitle.getText().toString() + " cost: " + wish2Edit.getCost());
                        updateWish();
                    } else {
                        meditCost.setError("Try Again");
                    }

                }

            }
        });
    }

    public void updateWish() {
        String newTitle = meditTitle.getText().toString();
        int newCost = Integer.parseInt(meditCost.getText().toString());
        int newDrawable = drawable;

        if(meditTitle.getText().toString()== wish2Edit.getTitle())
            newTitle = wish2Edit.getTitle();
        else newTitle = meditTitle.getText().toString();

        if(meditCost.getText().toString() == String.valueOf(wish2Edit.getCost()))
            newCost = wish2Edit.getCost();
        else newCost =Integer.parseInt(meditCost.getText().toString());

        if(drawable == wish2Edit.getImage())
            newDrawable = wish2Edit.getImage();
        else newDrawable = drawable;

        ((WishlistActivity) getActivity()).db.updateWish(dbid, newTitle, newCost, wish2Edit.getSaved(), newDrawable);
        Intent intent = new Intent(getActivity(), WishlistActivity.class);
        startActivity(intent);
    }
}
