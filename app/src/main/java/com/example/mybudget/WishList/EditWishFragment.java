package com.example.mybudget.WishList;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private EditText editTitle;
    private EditText editCost;
    private ImageView editWishPicture;
    private FloatingActionButton floatingActionButton_save_wish;
    private FloatingActionButton exitEditWish;
    private int index;
    private int dbid;
    private  WishList wish2Edit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_wish, container, false);

        index = getArguments().getInt("indexEdit");
        editTitle = view.findViewById(R.id.edit_title);
        editCost = view.findViewById(R.id.edit_cost);

        dbid = ((WishlistActivity) getActivity()).id;
        wish2Edit=((WishlistActivity) getActivity()).db.returnWish(dbid);

        editTitle.setHint(wish2Edit.getTitle());
        editCost.setHint(""+wish2Edit.getCost());
        editWishPicture = view.findViewById(R.id.edit_wish_picture);
        exitEditWish = view.findViewById(R.id.floatingActionButton_exit_edit_wish);
        floatingActionButton_save_wish = view.findViewById(R.id.floatingActionButton_save_edit_wish);

        activateOnExitEditWish();
        activateOnSaveEditWish();

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

    private void activateOnSaveEditWish() {
        floatingActionButton_save_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dbid =((WishlistActivity) getActivity()).id;
                WishList wish = ((WishlistActivity) getActivity()).db.returnWish(dbid);
                String title = editTitle.getText().toString();
                int cost = Integer.parseInt(editCost.getText().toString());
                Log.d(TAG, "EditWish: " + title);
                ((WishlistActivity) getActivity()).db.updateWish(dbid, title, cost, wish.getSaved(), wish.getImage());
                Intent intent = new Intent(getActivity(), WishlistActivity.class);
                startActivity(intent);
            }
        });
    }
}
