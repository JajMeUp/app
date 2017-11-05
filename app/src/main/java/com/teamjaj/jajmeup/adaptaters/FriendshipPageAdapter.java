package com.teamjaj.jajmeup.adaptaters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.teamjaj.jajmeup.fragments.AddFriendFragment;
import com.teamjaj.jajmeup.fragments.PendingFriendshipFragment;

import java.util.ArrayList;
import java.util.List;

public class FriendshipPageAdapter extends FragmentPagerAdapter {

    private static final int PENDING_REQUEST_TAB_POSITION = 0;
    private static final int SEARCH_FRIEND_TAB_POSITION = 1;

    private static final String PENDING_REQUEST_TAB_TITLE = "Ils veulent être ton pote !";
    private static final String SEARCH_FRIEND_TAB_TITLE = "Cherche tes amis !";

    private List<Fragment> fragments = new ArrayList<>();

    public FriendshipPageAdapter(FragmentManager fm) {
        super(fm);

        fragments.add(new PendingFriendshipFragment());
        fragments.add(new AddFriendFragment());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case PENDING_REQUEST_TAB_POSITION:
                return PENDING_REQUEST_TAB_TITLE;
            case SEARCH_FRIEND_TAB_POSITION:
                return SEARCH_FRIEND_TAB_TITLE;
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
