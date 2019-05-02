package com.example.mybudget.Profile;

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

import com.example.mybudget.Home.MainActivity;
import com.example.mybudget.R;

public class WelcomeFragmentDialog extends  AppCompatDialogFragment {
    private static final String TAG = "Welcome Fragment Dialog";
    private Button cancel_fragment;
    private TextView welcome_msg_txt;
    private ImageView mImageHeroWelcomeScreen;
    private ImageView mImageHeroRegistered;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome_screen, container, false);
        mImageHeroWelcomeScreen = view.findViewById(R.id.image_welcome_hero);
        mImageHeroRegistered = view.findViewById(R.id.imageWelcomeMonster);
        cancel_fragment = view.findViewById(R.id.btn_cancel_welcome_fragment);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        cancel_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
                Intent intentLoadMainPage = new Intent (getActivity(), MainActivity.class);
                startActivity(intentLoadMainPage);
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
            mImageHeroRegistered.setImageDrawable(d);
        }
    }
}
