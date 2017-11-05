package com.teamjaj.jajmeup.adaptaters;

/**
 * Created by Valentin on 15/07/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.teamjaj.jajmeup.fragments.ClockActivity;
import com.teamjaj.jajmeup.fragments.UsersList;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends FragmentStatePagerAdapter {
    private static final int CLOCK_TAB_POSITION = 0;
    private static final int FRIEND_TAB_POSITION = 1;

    private static final String CLOCK_TAB_TITLE = "Mon réveil";
    private static final String FRIEND_TAB_TITLE = "Mes amis";

    private List<Fragment> fragments = new ArrayList<>();

    public PageAdapter(FragmentManager fm) {
        super(fm);

        fragments.add(new ClockActivity());
        fragments.add(new UsersList());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case CLOCK_TAB_POSITION:
                return CLOCK_TAB_TITLE;
            case FRIEND_TAB_POSITION:
                return FRIEND_TAB_TITLE;
            default:
                return "";
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}