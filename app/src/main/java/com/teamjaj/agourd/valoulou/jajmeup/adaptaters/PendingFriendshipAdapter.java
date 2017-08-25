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
import com.teamjaj.agourd.valoulou.jajmeup.services.FriendshipService;

public class PendingFriendshipAdapter extends BaseAdapter implements ListAdapter {

    private FriendshipService friendshipService;
    private Context context;

    public PendingFriendshipAdapter(Context context, FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
        this.context = context;
    }

    @Override
    public int getCount() {
        return friendshipService.getPendingFriendships().size();
    }

    @Override
    public Object getItem(int pos) {
        return friendshipService.getPendingFriendships().get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return friendshipService.getPendingFriendships().get(pos).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.demande_ami_row, null);
        }

        final TextView listItemText = (TextView)view.findViewById(R.id.string_text);
        listItemText.setText(friendshipService.getPendingFriendships().get(position).getRequesterName());

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