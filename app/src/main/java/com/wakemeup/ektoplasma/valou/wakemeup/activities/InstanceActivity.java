package com.wakemeup.ektoplasma.valou.wakemeup.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.wakemeup.ektoplasma.valou.wakemeup.utilities.Caller;

/**
 * Created by ektoplasma on 29/08/16.
 */
public class InstanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = this;
        IntentFilter volleyerrorfilter = new IntentFilter();
        volleyerrorfilter.addAction("volley.error.message");
        registerReceiver(volleyerrorreceiver, volleyerrorfilter);

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
            displayAlert();
        }
    };

    private void displayAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Erreur réseau detectée.").setCancelable(
                false).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
