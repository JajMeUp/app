package com.teamjaj.agourd.valoulou.jajmeup.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.teamjaj.agourd.valoulou.jajmeup.R;
import com.teamjaj.agourd.valoulou.jajmeup.adaptaters.FriendshipPageAdapter;

public class FriendshipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.friend_management);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.friend_management_view_pager);
        final FriendshipPageAdapter adapter = new FriendshipPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }
}
