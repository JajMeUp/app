package com.teamjaj.jajmeup.adaptaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.teamjaj.jajmeup.R;
import com.teamjaj.jajmeup.dtos.Profile;

import java.util.Collections;
import java.util.List;

public class UserListAdapter extends BaseAdapter implements ListAdapter {

    private Context ctx;
    private List<Profile> userList;

    public UserListAdapter(Context ctx)
    {
        this.ctx = ctx;
        this.userList = Collections.emptyList();
    }

    public void setUserList(List<Profile> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return userList.get(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.user_list_item, parent, false);
        }

        TextView profileDisplayName = (TextView) view.findViewById(R.id.user_list_item_user_name);
        profileDisplayName.setText(userList.get(position).getDisplayName());

        return view;
    }
}
