package com.example.mybudget.WishList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;

import com.example.mybudget.Models.Entry;
import com.example.mybudget.Models.WishList;
import com.example.mybudget.Profile.RegisterActivity;
import com.example.mybudget.R;
import com.example.mybudget.WishList.WishFragment;
import com.example.mybudget.WishList.WishlistActivity;

import java.time.LocalDate;

    /**
     * Dialog asking User to register befor proceeding to locked functionalities
     * @author Anastasija
     *
     */

    public class RegisterRequestDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
            builder.setTitle("Information")
                    .setMessage("Please Register to continue")
                    .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(), RegisterActivity.class);
                            intent.putExtra("editProfile", "add");
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dismiss();
                        }
                    });
            return builder.create();
        }

    }

