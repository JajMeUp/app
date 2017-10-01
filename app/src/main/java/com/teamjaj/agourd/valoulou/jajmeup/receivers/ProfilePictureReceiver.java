package com.teamjaj.agourd.valoulou.jajmeup.receivers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.teamjaj.agourd.valoulou.jajmeup.activities.SettingsActivity;

public class ProfilePictureReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SettingsActivity settingsActivity = (SettingsActivity) context;
        Bundle extra = intent.getExtras();
        if(extra != null)
        {
            // SAVE PICTURE
        }
    }
}
