package com.example.mybudget.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.mybudget.R;
/**
 * The fragment shows avatars in registration page
 * @author Benish
 */
public class AvatarFragment extends Fragment {
    private static final String TAG = "Avatar Fragment";
    public static final String PREFS_NAME = "themePreferenceFile";
    public static final String KEY_THEME_RES_ID = "themeResId";
    public static final String KEY_DRAWABLE_RES_ID = "imageResId";
    private View view;
    private GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //view = inflater.inflate(R.layout.fragment_avatar_layout, container, false);
        try {
            view = inflater.inflate(R.layout.fragment_avatar_layout, container, false);
            gridView = (GridView) view.findViewById(R.id.gridview1);
            gridView.setAdapter(new AvatarGridView(view.getContext()));
            }catch(InflateException e) {
                e.printStackTrace();
            }

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {
                    Log.v(TAG, "Item At" + position + "Clicked");
                    switch (position) {
                        case 0:
                            saveAndSwitchTheme(R.style.AppTheme_CookieMonster, R.drawable.cookie);
                            break;
                        case 1:
                            saveAndSwitchTheme(R.style.AppTheme_CrazyMonster, R.drawable.crazy);
                            break;
                        case 2:
                            saveAndSwitchTheme(R.style.AppTheme_GirlMonster, R.drawable.girl);
                            break;
                        case 3:
                            saveAndSwitchTheme(R.style.AppTheme_Science, R.drawable.science);
                            break;
                    }
                }
            });
        return view;
    }

    private void saveAndSwitchTheme(@StyleRes int themeRes, int imageRes) {
        SharedPreferences sharedPrefs = this.getActivity().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(KEY_THEME_RES_ID, themeRes);
        editor.putInt(KEY_DRAWABLE_RES_ID, imageRes);
        editor.apply();
        Intent intent1 = new Intent(this.getActivity(), RegisterActivity.class);
        intent1.putExtra("editProfile", "add");
        startActivity(intent1);
    }

}
