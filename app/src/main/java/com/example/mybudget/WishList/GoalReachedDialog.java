package com.example.mybudget.WishList;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybudget.Models.WishList;
import com.example.mybudget.R;
/**
 * Dialog fragment, which notifies the user that they saved enough moeny for their goal;
 * @author Anastasija Gurejeva
 *
 */
public class GoalReachedDialog extends  AppCompatDialogFragment {
    private static final String TAG = "GoalReachedDialog";
    private Button mCancelDialog;
    private ImageView mImageHeroGoalReached;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_goal_reached, container, false);
        mCancelDialog = view.findViewById(R.id.btn_cancel_goal_reached_dialog);
        mImageHeroGoalReached = view.findViewById(R.id.imageHero_goal_reached);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
            }
        });

        setAvatar();
        return view;
    }

    public void setAvatar() {
        SharedPreferences settings = getActivity().getSharedPreferences("themePreferenceFile", 0);
        int imageResId = settings.getInt("imageResId", -1);
        if (imageResId != -1) {
            Drawable d = getActivity().getDrawable(imageResId);
            mImageHeroGoalReached.setImageDrawable(d);
        }
    }
}
