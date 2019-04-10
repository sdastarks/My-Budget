package com.example.mybudget;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/*
This class is used to adapt Wish List view to display wish image and progress bar
Author: Anastasija Gurejeva
 */



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mWishNames = new ArrayList<>();
    private ArrayList<Integer> mWishPrices = new ArrayList<>();
    private ArrayList<String> mWishImages = new ArrayList<>();
    private ArrayList<Integer> mSavingProgress = new ArrayList<>();
    private Context mContext;


    public RecyclerViewAdapter(ArrayList<String> mWishNames, ArrayList<Integer> mWishPrices, ArrayList<String> mWishImages, ArrayList<Integer> mSavingProgress, Context mContext) {
        this.mWishNames = mWishNames;
        this.mWishPrices = mWishPrices;
        this.mWishImages = mWishImages;
        this.mSavingProgress = mSavingProgress;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_wish_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: called item added");

        Glide.with(mContext)
                .asBitmap()
                .load(mWishImages.get(i))
                .into(viewHolder.wishImage);

        viewHolder.wishName.setText(mWishNames.get(i));
        viewHolder.wishPrice.setText("Price: " + mWishPrices.get(i) + " Kr");
        viewHolder.progressBarHorizontal.setMax(mWishPrices.get(i));
        viewHolder.savingProgress.setText("Saved: " + mSavingProgress.get(i) + " Kr");
        viewHolder.progressBarHorizontal.setProgress(mSavingProgress.get(i));
        viewHolder.progressBarHorizontal.setScaleY(5f);
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Place holder method. Need to add intent to send to the fragment

            }
        });


    }

    @Override
    public int getItemCount() {
        return mWishNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView wishImage;
        TextView wishName;
        TextView wishPrice;
        TextView savingProgress;
        ProgressBar progressBarHorizontal;
        ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wishImage = itemView.findViewById(R.id.wish_image);
            wishName = itemView.findViewById(R.id.text_wish_name);
            wishPrice = itemView.findViewById(R.id.wish_price);
            savingProgress = itemView.findViewById(R.id.saving_progress);
            progressBarHorizontal = itemView.findViewById(R.id.wish_progressBar);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}


