package com.wakemeup.ektoplasma.valou.wakemeup.receivers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.wakemeup.ektoplasma.valou.wakemeup.activities.SettingsActivity;
import com.wakemeup.ektoplasma.valou.wakemeup.utilities.BitmapUtils;

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
