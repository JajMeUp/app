package com.teamjaj.jajmeup.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.teamjaj.jajmeup.R;
import com.teamjaj.jajmeup.adaptaters.PendingFriendshipAdapter;
import com.teamjaj.jajmeup.services.FriendshipService;

public class PendingFriendshipFragment extends Fragment {

    private final FriendshipService friendshipService = new FriendshipService();

    private PendingFriendshipAdapter pendingFriendshipAdapter;

    private final BroadcastReceiver pendingFriendshipReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            pendingFriendshipAdapter.setPendingFriendshipList(friendshipService.getPendingFriendships());
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().registerReceiver(
                pendingFriendshipReceiver,
                new IntentFilter(FriendshipService.BROADCAST_REFRESH_PENDING_FRIENDSHIP_REQUEST)
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pendingFriendshipAdapter = new PendingFriendshipAdapter(getContext(), friendshipService);

        View view = inflater.inflate(R.layout.pending_friendship_fragment, container, false);
        ListView lView = (ListView) view.findViewById(R.id.list_pending_request);
        lView.setAdapter(pendingFriendshipAdapter);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(pendingFriendshipReceiver);
    }
}
