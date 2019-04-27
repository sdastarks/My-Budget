package com.example.mybudget.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybudget.R;

import java.util.List;
import java.util.ArrayList;


public class AvatarGridView extends BaseAdapter {

    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;


    public AvatarGridView(Context context) {
        mInflater = LayoutInflater.from(context);

        mItems.add(new Item(R.drawable.cookie_bg));
        mItems.add(new Item(R.drawable.crazy_bg));
        mItems.add(new Item(R.drawable.girl_bg));
        mItems.add(new Item(R.drawable.science_bg));
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).drawableId;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;
        TextView name;

        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
            v.setTag(R.id.picture_avatar, v.findViewById(R.id.picture_avatar));
        }

        picture = (ImageView) v.getTag(R.id.picture_avatar);

        Item item = getItem(position);

        picture.setImageResource(item.drawableId);
        return v;
        /*ImageView imageView = new ImageView(context);
        //imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
        //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      //  imageView.setPadding(8,8,8,8);
        imageView.setImageResource(thumbImages[position]);
        return imageView;*/
    }

    //Add all images to arraylist


    private static class Item {
        public final int drawableId;

        Item(int drawableId) {

            this.drawableId = drawableId;
        }
    }
}

