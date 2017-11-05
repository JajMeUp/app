package com.teamjaj.jajmeup.adaptaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import  com.teamjaj.jajmeup.R;

import java.util.ArrayList;

/**
 * Created by Valentin on 26/11/2016.
 */

public class CustomAdapterMessage extends BaseAdapter implements ListAdapter {

    private ArrayList<String> listpseudo;
    private ArrayList<String> listmessage;
    private Context context;

    public CustomAdapterMessage(ArrayList<String> listpseudo, ArrayList<String> listmessage, Context context) {
        this.listpseudo = new ArrayList<String>();
        this.listmessage = new ArrayList<String>();
        this.listpseudo = listpseudo;
        this.listmessage = listmessage;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listpseudo.size();
    }

    @Override
    public Object getItem(int pos) {
        return listpseudo.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.message_row, null);
        }

        final TextView listPseudoView = (TextView)view.findViewById(R.id.rowMessPseudo);
        final TextView listMessageView = (TextView)view.findViewById(R.id.rowMessMessage);
        listPseudoView.setText(listpseudo.get(position));
        listMessageView.setText(listmessage.get(position));

        return view;
    }
}