package com.teamjaj.jajmeup.adaptaters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import  com.teamjaj.jajmeup.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Valentin on 03/08/2016.
 */
public class UsersAdapter extends BaseExpandableListAdapter {

    private Context ctx;
    private HashMap<String, List<String>> UsersCategory;
    private List<String> ListUsers;
    private List<String> OriginalListUsers;
    private List<String> OriginalAllUser;
    private List<String> OriginalFriends;


    public UsersAdapter(Context ctx, HashMap<String, List<String>> UsersCategory, List<String> ListUsers)
    {
        this.ctx = ctx;
        this.UsersCategory = UsersCategory;
        this.ListUsers = ListUsers;
        this.OriginalListUsers = ListUsers;
        this.OriginalAllUser =  new ArrayList<String>();
        this.OriginalFriends = new ArrayList<String>();
        saveList();

    }

    public void updateUsersList(List<String> newlist, HashMap<String, List<String>> newhash) {
        if(!ListUsers.isEmpty())
            ListUsers.clear();
        if(!OriginalListUsers.isEmpty())
            OriginalListUsers.clear();
        UsersCategory = newhash;
        ListUsers = newlist;
        OriginalListUsers.addAll(newlist);
        // saveList();
        this.notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return ListUsers.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return UsersCategory.get(ListUsers.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return ListUsers.get(i);
    }

    @Override
    public Object getChild(int parent, int child) {
        return UsersCategory.get(ListUsers.get(parent)).get(child);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int parent, int child) {
        return child;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertView, ViewGroup parentView) {
        String groupTitle = (String) getGroup(parent);
        if(convertView == null)
        {
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.parent_list, parentView, false);
        }
        TextView parentTextView = (TextView) convertView.findViewById(R.id.ParentTxt);
        parentTextView.setTypeface(null, Typeface.BOLD);
        parentTextView.setText(groupTitle);
        return convertView;
    }

    @Override
    public View getChildView(int parent, int child, boolean lastChild, View convertView, ViewGroup parentView) {
        String ChildTitle = (String) getChild(parent, child);
        if(convertView == null)
        {
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.user_list_item, parentView, false);
        }
        TextView child_textview = (TextView) convertView.findViewById(R.id.user_list_item_user_name);
        child_textview.setText(ChildTitle);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private void saveList()
    {
        if(!OriginalAllUser.isEmpty())
            OriginalAllUser.clear();
        if(!OriginalFriends.isEmpty())
            OriginalFriends.clear();

        for(int groupPosition=0; groupPosition<OriginalListUsers.size(); groupPosition++)
        {

                for (int childPosition = 0; childPosition < UsersCategory.get(OriginalListUsers.get(groupPosition)).size(); childPosition++) {
                    if (groupPosition == 0)
                        OriginalFriends.add(UsersCategory.get(OriginalListUsers.get(groupPosition)).get(childPosition).toString());
                    else
                        OriginalAllUser.add(UsersCategory.get(OriginalListUsers.get(groupPosition)).get(childPosition).toString());
                }
        }
    }

    public void filterData(String query)
    {
        if(!OriginalListUsers.isEmpty())
        {
            if(query.isEmpty())
            {
                UsersCategory.put("Amis", OriginalFriends);
                UsersCategory.put("Tous les utilisateurs", OriginalAllUser);
            } else {
                for(int groupPosition=0; groupPosition<OriginalListUsers.size(); groupPosition++)
                {
                    List<String> temp = new ArrayList<String>();
                    for(int childPosition = 0; childPosition <  UsersCategory.get(OriginalListUsers.get(groupPosition)).size() ; childPosition++)
                    {
                        if(UsersCategory.get(OriginalListUsers.get(groupPosition)).get(childPosition).toString().contains(query))
                        {
                            temp.add(UsersCategory.get(OriginalListUsers.get(groupPosition)).get(childPosition).toString());
                        }

                    }
                    if(groupPosition == 0)
                        UsersCategory.put("Amis", temp);
                    else
                        UsersCategory.put("Tous les utilisateurs", temp);
                }
            }
            notifyDataSetChanged();
        }

    }


}
