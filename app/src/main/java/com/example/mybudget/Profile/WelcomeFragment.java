package com.example.mybudget.Profile;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybudget.R;

public class WelcomeFragment extends AppCompatDialogFragment {
    private static final String TAG = "GoalReachedDialog";
    private ImageView image_hero_welcome;
    private TextView welcome_screen_message;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_goal_half_reached, container, false);
        image_hero_welcome = view.findViewById(R.id.image_welcome_hero);
        welcome_screen_message = view.findViewById(R.id.goal_reached_txt);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
        }

    public void setAvatar() {
        SharedPreferences settings = getActivity().getSharedPreferences("themePreferenceFile", 0);
        int imageResId = settings.getInt("imageResId", -1);
        if(imageResId != -1){
            Drawable d=getActivity().getDrawable(imageResId);
            image_hero_welcome.setImageDrawable(d);
        }
    }
}
