package com.example.mybudget;


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

import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditWishFragment extends Fragment {

    private static final String TAG = "editWishFragment";
    private EditText editTitle;
    private EditText editCost;
    private ImageView editWishPicture;
    private FloatingActionButton floatingActionButton_save_wish;
    private FloatingActionButton exitEditWish;
    private int index;


    public EditWishFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_wish, container, false);

        index = getArguments().getInt("indexEdit");
        editTitle = view.findViewById(R.id.edit_title);
//      Todo  Dawnie: Set Hint as current wish title
                // editTitle.setHint(data from DB by index);
        editCost = view.findViewById(R.id.edit_cost);

        //Todo  Dawnie: Set Hint as current wish price
            //editCost.setHint(data from DB by index);
        editWishPicture = view.findViewById(R.id.edit_wish_picture);
        exitEditWish = view.findViewById(R.id.floatingActionButton_exit_edit_wish);
        floatingActionButton_save_wish = view.findViewById(R.id.floatingActionButton_save_edit_wish);

//


        //Method to exit fragment

        activateOnExitEditWish();

        return view;
    }

    private void activateOnExitEditWish() {

        exitEditWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WishlistActivity.class);
                startActivity(intent);
            }
        });

    }


//Todo  Dawnie: Update Wish in data base
//    private void activateOnSaveEditWish() {
//        floatingActionButton_save_wish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                WishList wish = new WishList();
//                wish.setTitle(title.getText().toString());
//                wish.setCost(Integer.parseInt(cost.getText().toString()));
//                wish.setSaved(0);
//                //wish.setImage(wishPicture);
//                ((WishlistActivity) getActivity()).db.addWish(wish);
//                Intent intent = new Intent(getActivity(), WishlistActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
}


