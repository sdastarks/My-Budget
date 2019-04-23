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
        meditTitle.setHint(wish2Edit.getTitle());
        meditCost.setHint(wish2Edit.getCost() + " SEK");


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
                        updateWish(wish2Edit.getTitle(), cost);
                    } else if (title.length() > 25) {
                        meditTitle.setError("Choose a smaller wish name");
                    } else {
                        updateWish(title, cost);
                    }

                } catch (Exception e) {
                    if (meditCost.getText().toString().trim().isEmpty()) {
                        Log.v(TAG, "if statment initialsed");
                        Log.v(TAG, "title: " + meditTitle.getText().toString() + " cost: " + wish2Edit.getCost());
                        updateWish(meditTitle.getText().toString(), wish2Edit.getCost());
                    } else {
                        meditCost.setError("Try Again");
                    }

                }

            }
        });
    }

    public void updateWish(String title, int cost) {
        ((WishlistActivity) getActivity()).db.updateWish(dbid, title, cost, wish2Edit.getSaved(), wish2Edit.getImage());
        Intent intent = new Intent(getActivity(), WishlistActivity.class);
        startActivity(intent);
    }
}
