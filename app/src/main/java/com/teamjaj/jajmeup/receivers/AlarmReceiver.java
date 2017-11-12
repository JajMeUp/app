package com.teamjaj.jajmeup.receivers;

/**
 * Created by Valentin on 14/07/2016.
 */

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.teamjaj.jajmeup.activities.MainActivity;
import com.teamjaj.jajmeup.services.AlarmService;
import com.teamjaj.jajmeup.services.ClockService;

public class AlarmReceiver extends WakefulBroadcastReceiver {


    @Override
    public void onReceive(final Context context, Intent intent) {
        MainActivity inst = MainActivity.instance();
        Log.d("AlarmReceiver", "Alarm sonne");
        ClockService alarm = new ClockService();
        alarm.requestLastAlarm(context);
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}
