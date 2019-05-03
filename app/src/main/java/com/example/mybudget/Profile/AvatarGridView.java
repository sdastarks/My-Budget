package com.example.mybudget.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybudget.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom Adapter for Gridview to show avatars for theme selection
 * @author Benish
 */
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
        View view1 = view;
        ImageView picture;
        TextView name;
        if (view1 == null) {
            view1 = mInflater.inflate(R.layout.grid_item, viewGroup, false);
            view1.setTag(R.id.picture_avatar, view1.findViewById(R.id.picture_avatar));
        }
        picture = (ImageView) view1.getTag(R.id.picture_avatar);
        Item item = getItem(position);
        picture.setImageResource(item.drawableId);
        return view1;
    }

    //Returns the position and id of image from drawable
    private static class Item {
        public final int drawableId;
            Item(int drawableId) {
            this.drawableId = drawableId;
        }
    }
}

