package com.example.mybudget.WishList;


import android.content.Intent;
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
    private  WishList wish2Edit;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_edit_wish, container, false);

        index = getArguments().getInt("indexEdit");

        meditTitle = view.findViewById(R.id.edit_title);
        meditCost = view.findViewById(R.id.edit_cost);
        btn_exitEditWish= view.findViewById(R.id.btn_cancel_edit_wish);
        btn_saveEditWish=view.findViewById(R.id.btn_save_edit_wish);
        btn_deletwish=view.findViewById(R.id.floatingActionButton_delete_wish);

        dbid = ((WishlistActivity) getActivity()).id;
        wish2Edit=((WishlistActivity) getActivity()).db.returnWish(dbid);

        meditTitle.setHint(wish2Edit.getTitle());
        meditCost.setHint(""+wish2Edit.getCost());
        editWishPicture = view.findViewById(R.id.edit_wish_picture);


        activateOnExitEditWish();
        activateOnSaveEditWish();
        activateDeleteWish();
        return view;
    }
    /*
     * Method creates a dialog fragment allowing the user
     * to delete a wish or abort the procedure
     */
    public void activateDeleteWish(){
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
                int dbid =((WishlistActivity) getActivity()).id;
                WishList wish = ((WishlistActivity) getActivity()).db.returnWish(dbid);
                String title = meditTitle.getText().toString();
                int cost = Integer.parseInt(meditCost.getText().toString());
                Log.d(TAG, "EditWish: " + title);
                ((WishlistActivity) getActivity()).db.updateWish(dbid, title, cost, wish.getSaved(), wish.getImage());
                Intent intent = new Intent(getActivity(), WishlistActivity.class);
                startActivity(intent);
            }
        });
    }
}
