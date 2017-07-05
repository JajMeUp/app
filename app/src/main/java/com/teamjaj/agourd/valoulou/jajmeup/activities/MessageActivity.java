package com.teamjaj.agourd.valoulou.jajmeup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.teamjaj.agourd.valoulou.jajmeup.adaptaters.CustomAdapterMessage;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.Caller;
import  com.teamjaj.agourd.valoulou.jajmeup.R;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Valentin on 30/08/2016.
 */
public class MessageActivity  extends AppCompatActivity {

    private ListView mainListView ;
    CustomAdapterMessage adapter;
    private ArrayAdapter<String> listAdapter ;
    private ArrayList<String> listpseudo  = new ArrayList<>();
    private ArrayList<String> listmessage  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        ArrayList<String> listMsg;
        ArrayList<String> listSender;
        mainListView = (ListView) findViewById(R.id.listMessage);
        ArrayList<String> messagelist = new ArrayList<String>();
        listAdapter = new ArrayAdapter<String>(this, R.layout.message_row, messagelist);
        registerForContextMenu(mainListView);

        if (Caller.getNewMessages() != null)
            listMsg = new ArrayList<String>(Caller.getNewMessages());
        else listMsg = new ArrayList<String>();

        if (Caller.getNewSenders() != null)
            listSender = new ArrayList<String>(Caller.getNewSenders());
        else listSender = new ArrayList<String>();

        Iterator<String> x = listMsg.iterator();
        Iterator<String> y = listSender.iterator();

        while (x.hasNext() && y.hasNext()) {
            String message = (String) x.next();
            String pseudo = (String) y.next();
            //listAdapter.add( pseudo + " : " + message );
            listpseudo.add(pseudo);
            listmessage.add(message);
        }
        CustomAdapterMessage adapter = new CustomAdapterMessage(listpseudo, listmessage, this);
        mainListView.setAdapter(adapter);
        adapter = new CustomAdapterMessage(listpseudo, listmessage, this);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(MessageActivity.this, MessengerActivity.class);
                intent.putExtra("Name",listpseudo.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        ListView lv = (ListView) v;
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String pseudo = (String) lv.getItemAtPosition(acmi.position);

        menu.setHeaderTitle("Conversation avec "+pseudo);
        menu.add(0, 1, 1, "Supprimer la conversation");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case 1://Suppression conversation
                AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                String key = listpseudo.get(acmi.position);
                Toast.makeText(this, "Conversation avec "+key+" supprimée", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}