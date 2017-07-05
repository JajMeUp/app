package com.teamjaj.agourd.valoulou.jajmeup.adaptaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.view.Gravity;

import  com.teamjaj.agourd.valoulou.jajmeup.R;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.MessengerClass;

import java.util.ArrayList;

/**
 * Created by Valentin on 27/11/2016.
 */

public class CustomAdapterMessenger extends BaseAdapter implements ListAdapter {

    private ArrayList<MessengerClass> listMess;
    private Context context;

    public CustomAdapterMessenger(ArrayList<MessengerClass> listMess, Context context) {
        this.listMess  = new ArrayList<MessengerClass>();
        this.listMess = listMess;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listMess.size();
    }

    @Override
    public Object getItem(int pos) {
        return listMess.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        MessengerClass message = (MessengerClass) listMess.get(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.messenger_row, null);
        }
        TextView msg = (TextView) view.findViewById(R.id.message_text);

        msg.setText(message.body);
        LinearLayout layout = (LinearLayout) view
                .findViewById(R.id.bubble_layout);
        LinearLayout parent_layout = (LinearLayout) view
                .findViewById(R.id.linear_bubble);

        if (message.monMess) {
            layout.setBackgroundResource(R.drawable.bubble2);
            parent_layout.setGravity(Gravity.RIGHT);
        }

        else {
            layout.setBackgroundResource(R.drawable.bubble1);
            parent_layout.setGravity(Gravity.LEFT);
        }

        return view;
    }
}