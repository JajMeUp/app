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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.teamjaj.jajmeup.R;
import com.teamjaj.jajmeup.activities.MainActivity;
import com.teamjaj.jajmeup.adaptaters.UserListAdapter;
import com.teamjaj.jajmeup.services.ClockService;
import com.teamjaj.jajmeup.services.ProfileService;

public class JajFragment extends Fragment implements AdapterView.OnItemClickListener {

    private UserListAdapter adapter;
    private ProfileService profileService = new ProfileService();
    private ClockService clockService = new ClockService();

    private SearchView searchView;

    public String sharedLink;

    private BroadcastReceiver searchResultReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            adapter.setUserList(profileService.getLastSearchResults());
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new UserListAdapter(getContext());
        getActivity().registerReceiver(
                searchResultReceiver,
                new IntentFilter(ProfileService.BROADCAST_PROFILE_SEARCH_RESULTS)
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ListView listView = (ListView) view.findViewById(R.id.expandableListPerson);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        searchView = (SearchView) view.findViewById(R.id.inputSearch);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                profileService.findFriendByName(getContext(), newText);
                return false;
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        profileService.findFriendByName(getContext(), "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(searchResultReceiver);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // TODO - Get the Youtube link

        Bundle target = new Bundle();
        target.putString("target", String.valueOf(id));
        target.putString("sharedlink", ((MainActivity) getActivity()).getSharedLink());
        ((MainActivity) getActivity()).setSharedLink("");
        DialogFragmentPasteLink fragParamReveil = new DialogFragmentPasteLink();
        fragParamReveil.setArguments(target);
        fragParamReveil.show(getFragmentManager(), "YouTube Link");
    }
}
