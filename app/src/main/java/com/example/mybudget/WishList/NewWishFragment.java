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
                Intent intent = new Intent (getActivity(), WishlistActivity.class);
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

        return view;
    }

}
