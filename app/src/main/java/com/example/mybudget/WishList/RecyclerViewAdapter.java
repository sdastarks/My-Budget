package com.example.mybudget.WishList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mybudget.R;

import java.util.ArrayList;

/*
This class is used to adapt Wish List view to display wish image and progress bar
Author: Anastasija Gurejeva
 */



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Integer> mWishId = new ArrayList<>();
    private ArrayList<String> mWishNames = new ArrayList<>();
    private ArrayList<Integer> mWishPrices = new ArrayList<>();
    private ArrayList<String> mWishImages = new ArrayList<>();
    private ArrayList<Integer> mSavingProgress = new ArrayList<>();


    private Context mContext;
    private OnWishListener mOnWishListener;


    public RecyclerViewAdapter(ArrayList<Integer> mWishId ,ArrayList<String> mWishNames, ArrayList<Integer> mWishPrices,
                               ArrayList<String> mWishImages, ArrayList<Integer> mSavingProgress,
                               Context mContext, OnWishListener onWishListener) {

        //added the database instance to retreive all added wished to display
             this.mWishId = mWishId;
             this.mWishNames = mWishNames;
             this.mWishPrices = mWishPrices;
             this.mWishImages = mWishImages;
             this.mSavingProgress = mSavingProgress;
             this.mContext = mContext;
             this.mOnWishListener = onWishListener;
         }



     //Method creates a Layout view for the Wish List
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_wish_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view, mOnWishListener);
        return viewHolder;
    }


     // Method pass Wish values to the wish section
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: called item added");

        Glide.with(mContext)
                .asBitmap()
                .load(mWishImages.get(i))
                .into(viewHolder.wishImage);

        viewHolder.wishName.setText(mWishNames.get(i));
        viewHolder.wishPrice.setText("Price: " + mWishPrices.get(i) + " sek");
        viewHolder.progressBarHorizontal.setMax(mWishPrices.get(i));
        viewHolder.savingProgress.setText("Saved: " + mSavingProgress.get(i) + " sek");
        viewHolder.progressBarHorizontal.setProgress(mSavingProgress.get(i));
        viewHolder.progressBarHorizontal.setScaleY(5f);
    }


     // Method returns size of the WishList
    @Override
    public int getItemCount() {
        return mWishNames.size();
    }


     //View Holder class initiates elements inside the Wish section
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView wishImage;
        TextView wishName;
        TextView wishPrice;
        TextView savingProgress;
        ProgressBar progressBarHorizontal;
        ConstraintLayout parentLayout;
        OnWishListener onWishListener;

        public ViewHolder(@NonNull View itemView, OnWishListener onWishListener) {
            super(itemView);

            wishImage = itemView.findViewById(R.id.wish_image);
            wishName = itemView.findViewById(R.id.text_wish_name);
            wishPrice = itemView.findViewById(R.id.wish_price);
            savingProgress = itemView.findViewById(R.id.saving_progress);
            progressBarHorizontal = itemView.findViewById(R.id.wish_progressBar);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            this.onWishListener = onWishListener;

            itemView.setOnClickListener(this);
        }

         @Override
         public void onClick(View v) {
            onWishListener.onWishClick(getAdapterPosition());

         }
     }

    public interface OnWishListener {
        void onWishClick(int position);
    }
}


