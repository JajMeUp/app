package com.teamjaj.agourd.valoulou.jajmeup.fragments;

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
import android.widget.SearchView;

import com.teamjaj.agourd.valoulou.jajmeup.R;
import com.teamjaj.agourd.valoulou.jajmeup.adaptaters.UserListAdapter;
import com.teamjaj.agourd.valoulou.jajmeup.services.ProfileService;

public class AddFriendFragment extends Fragment {
    private final ProfileService profileService = new ProfileService();

    private UserListAdapter userListAdapter;

    private final BroadcastReceiver searchResultReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            userListAdapter.setUserList(profileService.getLastSearchResults());
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userListAdapter = new UserListAdapter(getContext());
        getActivity().registerReceiver(
                searchResultReceiver,
                new IntentFilter(ProfileService.BROADCAST_PROFILE_SEARCH_RESULTS)
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_friend_fragment, container, false);

        ListView userList = (ListView) view.findViewById(R.id.add_friend_user_list);
        userList.setAdapter(userListAdapter);

        SearchView searchView = (SearchView) view.findViewById(R.id.search_user);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                profileService.findByName(getContext(), newText);
                return false;
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        profileService.findByName(getContext(), "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(searchResultReceiver);
    }
}
