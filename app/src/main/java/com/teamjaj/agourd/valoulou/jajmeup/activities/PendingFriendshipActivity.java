package com.teamjaj.agourd.valoulou.jajmeup.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.teamjaj.agourd.valoulou.jajmeup.R;
import com.teamjaj.agourd.valoulou.jajmeup.adaptaters.PendingFriendshipAdapter;
import com.teamjaj.agourd.valoulou.jajmeup.services.FriendshipService;

public class PendingFriendshipActivity extends AppCompatActivity {

    private final FriendshipService friendshipService = new FriendshipService();
    private PendingFriendshipAdapter adapter;

    private BroadcastReceiver pendingFriendshipReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.demande_ami);

        adapter = new PendingFriendshipAdapter(this, friendshipService);

        ListView lView = (ListView)findViewById(R.id.listDemandeAmi);
        lView.setAdapter(adapter);

        registerReceiver(
                pendingFriendshipReceiver,
                new IntentFilter(FriendshipService.BROADCAST_REFRESH_PENDING_FRIENDSHIP_REQUEST)
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(pendingFriendshipReceiver);
    }
}
