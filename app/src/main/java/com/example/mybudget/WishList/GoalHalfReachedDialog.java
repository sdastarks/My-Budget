package com.example.mybudget.WishList;

import android.content.SharedPreferences;
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

import com.example.mybudget.R;

/**
 * Dialog fragment, which notifies the user that they are half way to save for their goal
 * @author Anastasija Gurejeva
 *
 */
public class GoalHalfReachedDialog extends  AppCompatDialogFragment {
    private static final String TAG = "GoalReachedDialog";
    private Button mCancelHalfWayDialog;
    private TextView mPoints;
    private ImageView mImageHeroGoalHalfReached;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.dialog_goal_half_reached,container,false);
        mCancelHalfWayDialog = view.findViewById(R.id.btn_cancel_half_goal_reached_dialog);
        mPoints = view.findViewById(R.id.txt_points_half_goal_reached);
        mImageHeroGoalHalfReached = view.findViewById(R.id.imageHero_goal_half_reached);



        mCancelHalfWayDialog.setOnClickListener(new View.OnClickListener() {
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
        if(imageResId != -1){
            Drawable d=getActivity().getDrawable(imageResId);
            mImageHeroGoalHalfReached.setImageDrawable(d);
        }
    }


}
