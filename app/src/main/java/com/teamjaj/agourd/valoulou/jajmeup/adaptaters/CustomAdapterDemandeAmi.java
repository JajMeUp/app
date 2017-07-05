package com.teamjaj.agourd.valoulou.jajmeup.adaptaters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.teamjaj.agourd.valoulou.jajmeup.utilities.Caller;
import  com.teamjaj.agourd.valoulou.jajmeup.R;

import java.util.ArrayList;

/**
 * Created by Valentin on 30/08/2016.
 */
public class CustomAdapterDemandeAmi extends BaseAdapter implements ListAdapter {

    private ArrayList<String> list = new ArrayList<String>();
    private Context context;



    public CustomAdapterDemandeAmi(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
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
            view = inflater.inflate(R.layout.demande_ami_row, null);
        }

        final TextView listItemText = (TextView)view.findViewById(R.id.string_text);
        listItemText.setText(list.get(position));

        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
        Button addBtn = (Button)view.findViewById(R.id.add_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent broadcast = new Intent("ekto.valou.badgebroadcast");
                broadcast.putExtra("TYPE","friend");
                broadcast.putExtra("COUNT",String.valueOf(getCount()-1));
                Caller.getCtx().sendBroadcast(broadcast);
                System.out.println("Broadcast envoyé");
                Caller.refuseFriend((String) listItemText.getText());
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent broadcast = new Intent("ekto.valou.badgebroadcast");
                broadcast.putExtra("TYPE","friend");
                broadcast.putExtra("COUNT",String.valueOf(getCount()-1));
                Caller.getCtx().sendBroadcast(broadcast);
                System.out.println("Broadcast envoyé");
                Caller.acceptFriend((String) listItemText.getText());
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}