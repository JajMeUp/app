package com.teamjaj.agourd.valoulou.jajmeup.adaptaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.teamjaj.agourd.valoulou.jajmeup.R;
import com.teamjaj.agourd.valoulou.jajmeup.dtos.PendingFriendship;
import com.teamjaj.agourd.valoulou.jajmeup.services.FriendshipService;

import java.util.List;

public class PendingFriendshipAdapter extends BaseAdapter implements ListAdapter {

    private Context context;
    private FriendshipService friendshipService; // TODO make the adapter not using directly the service ?

    private List<PendingFriendship> pendingFriendshipList;

    public PendingFriendshipAdapter(Context context, FriendshipService friendshipService) {
        this.context = context;
        this.friendshipService = friendshipService;

        this.pendingFriendshipList = friendshipService.getPendingFriendships();
    }

    public void setPendingFriendshipList(List<PendingFriendship> pendingFriendshipList) {
        this.pendingFriendshipList = pendingFriendshipList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return pendingFriendshipList.size();
    }

    @Override
    public Object getItem(int pos) {
        return pendingFriendshipList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pendingFriendshipList.get(pos).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.demande_ami_row, null);
        }

        final TextView listItemText = (TextView)view.findViewById(R.id.string_text);
        listItemText.setText(pendingFriendshipList.get(position).getRequesterName());

        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
        Button addBtn = (Button)view.findViewById(R.id.add_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                friendshipService.refuseFriend(context, getItemId(position));
                friendshipService.getPendingFriendshipRequest(context);
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                friendshipService.acceptFriend(context, getItemId(position));
                friendshipService.getPendingFriendshipRequest(context);
            }
        });

        return view;
    }
}