package com.wakemeup.ektoplasma.valou.wakemeup.receivers;

/**
 * Created by Valentin on 14/07/2016.
 */
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.wakemeup.ektoplasma.valou.wakemeup.services.AlarmService;
import com.wakemeup.ektoplasma.valou.wakemeup.utilities.Caller;
import com.wakemeup.ektoplasma.valou.wakemeup.fragments.ClockActivity;
import com.wakemeup.ektoplasma.valou.wakemeup.activities.MainActivity;

public class AlarmReceiver extends WakefulBroadcastReceiver {


    @Override
    public void onReceive(final Context context, Intent intent) {
        //this will update the UI with message
        MainActivity inst = MainActivity.instance();
        ClockActivity essai = new ClockActivity();
        Log.d("AlarmReceiver", "Alarm sonne");
        essai.setAlarmText("Le reveil sonne");
        Caller.getClockSong();
        //this will sound the alarm tone
        //this will sound the alarm once, if you wish to
        //raise alarm in loop continuously then use MediaPlayer and setLooping(true)
        /*Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();*/

        //this will send a notification message
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}
