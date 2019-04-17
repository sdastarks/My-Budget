package com.example.mybudget;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class AvatarFragment extends Fragment {
    private static final String TAG = "Avatar Fragment";

}

class GridAdapter extends BaseAdapter{
    Context context;

    public GridAdapter(Context context1){
        context = context1;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
