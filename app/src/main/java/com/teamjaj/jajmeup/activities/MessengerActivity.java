package com.teamjaj.jajmeup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.teamjaj.jajmeup.adaptaters.CustomAdapterMessenger;
import com.teamjaj.jajmeup.utilities.Caller;
import  com.teamjaj.jajmeup.R;
import com.teamjaj.jajmeup.utilities.MessengerClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Valentin on 27/11/2016.
 */

public class MessengerActivity extends AppCompatActivity {

    String Name;

    Caller messengerCall;

    ArrayList<MessengerClass> messageList;

    File cache;

    CustomAdapterMessenger adapter;
    EditText messEnvoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        ListView mainListView = (ListView) findViewById(R.id.listMessenger);
        Intent myIntent = getIntent(); // gets the previously created intent
        Name = myIntent.getStringExtra("Name");
        setTitle(Name);
        messageList = new ArrayList<MessengerClass>();


        cache = new File(getCacheDir(), Name+"_cache");
        //cache.delete();
        messageList = ReadCache(Name+"_cache");

        ArrayList<String> listMsg;
        if (Caller.getNewMessages() != null)
            listMsg = new ArrayList<String>(Caller.getNewMessages());
        else listMsg = new ArrayList<String>();

        Iterator<String> x = listMsg.iterator();
        while (x.hasNext()) {
            String message = (String) x.next();
            messageList.add(new MessengerClass(message, false));
            SaveInCache(Name+" : "+message);
        }

        adapter = new CustomAdapterMessenger(messageList, this);
        mainListView.setAdapter(adapter);

        Button button= (Button) findViewById(R.id.EnvoiMessenger);
        messEnvoi = (EditText) findViewById(R.id.MessToSend);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageList.add(new MessengerClass(messEnvoi.getText().toString(), true));
                SaveInCache(messengerCall.getPseudonyme()+" : "+messEnvoi.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

    }

    private ArrayList<MessengerClass> ReadCache(String cache_name)
    {
        ArrayList<MessengerClass> tmpList = new ArrayList<MessengerClass>();
        MessengerClass tmpMess = new MessengerClass("Bonjour", false);
        //cache = new File(getCacheDir(), cache_name+"_cache");
        int compt = 0;

        System.out.println("Bonjour -> "+cache.getAbsolutePath());

        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(cache));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
                if(line.startsWith(Name+" : "))
                {
                    tmpMess = new MessengerClass(line.replace(Name+" : ", ""), false);
                    tmpList.add(compt, tmpMess);
                }
                else
                {
                    tmpMess = new MessengerClass(line.replace(messengerCall.getPseudonyme()+" : ", ""), true);
                    tmpList.add(compt, tmpMess);
                }
                compt++;
            }
            br.close();
        }
        catch (IOException e) {
            Log.e("Exception", "Error read cache: " + e.toString());
        }
        System.out.println("Bonjour -> Cache : "+text);

        return tmpList;
    }

    private void SaveInCache(String write)
    {
        //cache = new File(getCacheDir(), cache_name+"_cache");

        try
        {
            FileOutputStream fOut = new FileOutputStream(cache, true);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(write+"\n");

            myOutWriter.close();

            fOut.flush();
            fOut.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "Error write cache: " + e.toString());
        }
    }

}
