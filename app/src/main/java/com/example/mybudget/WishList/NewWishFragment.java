package com.example.mybudget.WishList;


import android.content.Intent;
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

import com.example.mybudget.Models.WishList;
import com.example.mybudget.R;
import com.example.mybudget.WishList.WishlistActivity;


/**
 * Fragment allows user to create new Wish
 * Add a target price, title
 * @author Anastasija Gurejeva
 * @author Daniel Beadleson
 *
 */
public class NewWishFragment extends Fragment {
    private static final String TAG = "NewWishFragment";
    private TextInputEditText mNewWishTitle;
    private TextInputEditText mNewWishCost;
    private ImageView wishCathegory;
    private Button saveNewWish;
    private Button cancelNewWish;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_wish, container, false);
        mNewWishTitle = view.findViewById(R.id.wish_title);
        mNewWishCost = view.findViewById(R.id.wish_cost);
        wishCathegory = view.findViewById(R.id.image_wish_cathegory);
        saveNewWish = view.findViewById(R.id.btn_save_new_wish);
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
    /*
     * Method attempts to save a wish
     */
    private void activateOnSaveNewWish() {
        saveNewWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int cost =Integer.parseInt(mNewWishCost.getText().toString());
                    String wishTitle=mNewWishTitle.getText().toString();
                    if (cost > 10000000){
                        mNewWishCost.setError("Wish must be less than 10M SEK");
                    }
                    else if (wishTitle.isEmpty()){
                        mNewWishTitle.setError("Field cannot be empty");
                    }

                    else if (wishTitle.length()> 25){
                        mNewWishTitle.setError("Choose a smaller wish name");
                    }
                    else {
                        WishList wish = new WishList();
                        wish.setTitle(wishTitle);
                        wish.setCost(cost);
                        wish.setSaved(0);
                        //wish.setImage(wishPicture);
                        mNewWishCost.setError(null);
                        ((WishlistActivity) getActivity()).db.addWish(wish);
                        Intent intent = new Intent (getActivity(), WishlistActivity.class);
                        startActivity(intent);
                    }
                }
                catch (Exception e){
                    mNewWishCost.setError("Try Again");
                }

            }
        });
    }


        public void bike(View view){
          //wishPicture.setImageResource();
          

    }

}
