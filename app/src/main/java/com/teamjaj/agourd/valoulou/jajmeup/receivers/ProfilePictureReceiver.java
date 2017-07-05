package com.teamjaj.agourd.valoulou.jajmeup.receivers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.teamjaj.agourd.valoulou.jajmeup.activities.SettingsActivity;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.BitmapUtils;

public class ProfilePictureReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SettingsActivity settingsActivity = (SettingsActivity) context;
        Bundle extra = intent.getExtras();
        if(extra != null)
        {
            Bitmap picture = BitmapUtils.stringToBitmap(extra.getString("data"));
            Uri uri = settingsActivity.saveProfilePictureLocally(picture);
            if(uri != null) {
                settingsActivity.setProfilePicture(uri, picture);
            }
        }
    }
}
