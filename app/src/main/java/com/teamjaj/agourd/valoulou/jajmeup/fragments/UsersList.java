package com.teamjaj.agourd.valoulou.jajmeup.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.teamjaj.agourd.valoulou.jajmeup.dtos.Profile;
import com.teamjaj.agourd.valoulou.jajmeup.services.FriendshipService;
import com.teamjaj.agourd.valoulou.jajmeup.services.ProfileService;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.Caller;
import  com.teamjaj.agourd.valoulou.jajmeup.R;
import com.teamjaj.agourd.valoulou.jajmeup.adaptaters.UsersAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class UsersList extends Fragment {

    HashMap<String, java.util.List<String>> UsersCategory;
    List<String> ListUsers;
    private ExpandableListView ExpList;
    UsersAdapter adapter;
    private SearchView searchV;
    //private String[] Userstring;

    ProfileService profileService = new ProfileService();

    private BroadcastReceiver searchResultReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<String> newList = new ArrayList<>();
            for(Profile profile : profileService.getLastSearchResults()) {
                newList.add(profile.getDisplayName());
            }
            adapter.updateUsersList(newList, UsersCategory);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ExpList = (ExpandableListView) view.findViewById(R.id.expandableListPerson);
       // Userstring = getResources().getStringArray(R.array.);
        setList();
        adapter = new UsersAdapter(getActivity(), UsersCategory, ListUsers);
        ExpList.setAdapter(adapter);
        registerForContextMenu(ExpList);

        /*******************************************************
         * Listener pour le click sur la liste
         ******************************************************/

        ExpList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        ExpList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String name = adapter.getChild(groupPosition, childPosition).toString();
                /*TODO
                        -message du voteur*/
                if(Caller.getCurrentLink() != null)
                {
                    Caller.setCurrentReceiver(name);
                    DialogFragmentMessage dialog = DialogFragmentMessage.newInstance();
                    dialog.show(getFragmentManager(), "fragmentDialog");
                }
                else Toast.makeText(getActivity(), "S'il vous plaît, partagez un lien Youtube avant de cliquer ici.", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchV = (SearchView) view.findViewById(R.id.inputSearch);
        searchV.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchV.setIconifiedByDefault(false);
        searchV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //adapter.filterData(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                profileService.findByName(getActivity().getApplicationContext(), newText);
                //adapter.filterData(newText);
                return false;
            }
        });
       //searchV.setOnCloseListener(this);

        getActivity().registerReceiver(
                searchResultReceiver,
                new IntentFilter(ProfileService.BROADCAST_PROFILE_SEARCH_RESULTS)
        );
        return view;
    }


    protected Dialog onCreateDialog(int id) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.send_message, null))
                .setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.notsend, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    @Override
    public void onResume()
    {
        super.onResume();
       // adapter.notifyDataSetChanged();
        setList();
        adapter.updateUsersList(ListUsers, UsersCategory);//clear
        registerForContextMenu(ExpList);
    }

    private void setList()
    {
        String autorisation = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("prefWhoWakeMe", null);

        if(autorisation != null)
            UsersCategory = getData(autorisation);
        else
            UsersCategory = getData("Mes amis");//Value at start
        ListUsers = new ArrayList<>(UsersCategory.keySet());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        int groupPosition = ExpandableListView.getPackedPositionGroup(info.packedPosition);
        int childPosition = ExpandableListView.getPackedPositionChild(info.packedPosition);

        String selection = adapter.getChild(groupPosition, childPosition).toString();

        if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {

        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            menu.setHeaderTitle(selection);
            menu.add(0, 1, 1, "Réveiller");
            if(adapter.getGroup(groupPosition).toString() == "Tous les utilisateurs")
            {
                menu.add(1, 2, 2, "Ajouter");
            }
            else
            {
                menu.add(1, 3, 3, "Supprimer");
            }

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case 1://Lancement App YouTube
                ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();
                int type = ExpandableListView.getPackedPositionType(info.packedPosition);
                int groupPosition = ExpandableListView.getPackedPositionGroup(info.packedPosition);
                int childPosition = ExpandableListView.getPackedPositionChild(info.packedPosition);

                String selection = adapter.getChild(groupPosition, childPosition).toString();
                Intent LaunchIntent =  getActivity().getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
                startActivity(LaunchIntent);
                break;
            case 2://Ajout d'ami
                info = (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();
                type = ExpandableListView.getPackedPositionType(info.packedPosition);
                groupPosition = ExpandableListView.getPackedPositionGroup(info.packedPosition);
                childPosition = ExpandableListView.getPackedPositionChild(info.packedPosition);

                selection = adapter.getChild(groupPosition, childPosition).toString();
                Caller.addFriend(selection);
                break;
            case 3://Supression ami
                info = (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();
                type = ExpandableListView.getPackedPositionType(info.packedPosition);
                groupPosition = ExpandableListView.getPackedPositionGroup(info.packedPosition);
                childPosition = ExpandableListView.getPackedPositionChild(info.packedPosition);

                selection = adapter.getChild(groupPosition, childPosition).toString();
                Toast.makeText(getActivity(), "Ami retiré de la liste", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private HashMap<String, List<String>> getData(String autorisation)
    {

        HashMap<String, List<String>> UsersDetails = new HashMap<String, List<String>>();

        Caller.getBddAmi();


        List<String> Amis = Caller.getAmi();
        System.out.println("Liste -> "+Amis);
        UsersDetails.put("Amis", Amis);

        if(autorisation.equals("Tout le monde"))
        {
            Caller.getBddWorld();

            List<String> ToutLeMonde = Caller.getWorld();
            UsersDetails.put("Tous les utilisateurs", ToutLeMonde);
        }

        return UsersDetails;
    }

}
