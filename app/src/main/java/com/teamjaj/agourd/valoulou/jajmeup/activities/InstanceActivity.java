package com.teamjaj.agourd.valoulou.jajmeup.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.teamjaj.agourd.valoulou.jajmeup.utilities.Caller;

/**
 * Created by ektoplasma on 29/08/16.
 */
public class InstanceActivity extends AppCompatActivity {

    private static final int NETWORK_ERROR = 1;
    private static final int YOUTUBE_ERROR = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = this;
        IntentFilter volleyerrorfilter = new IntentFilter();
        volleyerrorfilter.addAction("volley.error.message");
        registerReceiver(volleyerrorreceiver, volleyerrorfilter);
        IntentFilter yterrorfilter = new IntentFilter();
        yterrorfilter.addAction("youtube.error.message");
        registerReceiver(yterrorreceiver, yterrorfilter);

        Caller.setCtx(ctx);
        Caller.storePersistantCookieString();
        if(Caller.getCookieInstance() != null)
        {
            Caller.checkCookie();
        }
        else
        {
            Intent signIntent = new Intent(ctx, SignActivity.class);
            ctx.startActivity(signIntent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(volleyerrorreceiver);
    }


    private BroadcastReceiver volleyerrorreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            displayAlert(NETWORK_ERROR);
        }
    };

    private BroadcastReceiver yterrorreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            displayAlert(YOUTUBE_ERROR);
        }
    };

    private void displayAlert(int errorid)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(errorid == NETWORK_ERROR)
        {
            builder.setMessage("Erreur réseau detectée.").setCancelable(
                    false).setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    });
        }
        if(errorid == YOUTUBE_ERROR)
        {
            builder.setMessage("Erreur : L'application Youtube n'est pas installée.").setCancelable(
                    false).setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    });
        }
        AlertDialog alert = builder.create();
        alert.show();
    }
}
