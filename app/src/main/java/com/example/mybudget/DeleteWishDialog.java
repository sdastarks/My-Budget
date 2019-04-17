package com.example.mybudget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
/**
 * Fragment creates a dialog allowing the user
 * to delete a wish or abort the procedure
 * @author Daniel Beadleson
 *
 */

public class DeleteWishDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setTitle("Information")
                .setMessage("Are you sure you would like to delete this wish?")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int dbid =((WishlistActivity) getActivity()).id;
                        ((WishlistActivity) getActivity()).db.deleteWish(dbid);
                        Intent intent = new Intent(getActivity(), WishlistActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_wish_fragment, new WishFragment())
                                .commit();
                    }
                });
        return builder.create();
    }
}
