package com.wakemeup.ektoplasma.valou.wakemeup.adaptaters;

/**
 * Created by Valentin on 15/07/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wakemeup.ektoplasma.valou.wakemeup.fragments.ClockActivity;
import com.wakemeup.ektoplasma.valou.wakemeup.fragments.UsersList;

public class PageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            /*case 0:
                HistoryActivity tab0 = new HistoryActivity();
                return tab0;*/
            case 0:
                ClockActivity tab1 = new ClockActivity();
                return tab1;
            case 1:
                UsersList tab2 = new UsersList();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}