package com.example.mybudget.WishList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;

import com.example.mybudget.R;
import com.example.mybudget.WishList.WishFragment;
import com.example.mybudget.WishList.WishlistActivity;

/**
 * Fragment creates a dialog allowing the user
 * to delete a wish or abort the procedure
 * @author Daniel Beadleson
 *
 */

public class DeleteWishDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final int index =getArguments().getInt("indexEdit");
        Log.v("DeleteWishDialog", "index: "+getArguments().getInt("indexEdit"));
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setTitle("Information")
                .setMessage("Are you sure you would like to delete this wish?")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        int favWish_dbID= sharedPref.getInt("favouriteWish",0);
                        int dbid =((WishlistActivity) getActivity()).id;
                        if (dbid==favWish_dbID){
                            sharedPref.edit().putInt("favouriteWish", 0).apply();
                        }
                        ((WishlistActivity) getActivity()).db.deleteWish(dbid);
                        Intent intent = new Intent(getActivity(), WishlistActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditWishFragment editWishFragmentCall = new EditWishFragment();
                        Bundle args = new Bundle();
                        args.putInt("indexEdit", index);
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_wish_fragment, editWishFragmentCall)
                                .commit();
                        editWishFragmentCall.setArguments(args);
                    }
                });
        return builder.create();
    }
}
