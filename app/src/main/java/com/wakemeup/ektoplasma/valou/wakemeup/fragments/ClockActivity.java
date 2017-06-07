package com.wakemeup.ektoplasma.valou.wakemeup.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.wakemeup.ektoplasma.valou.wakemeup.R;

/**
 * Created by Valentin on 24/07/2016.
 */
public class ClockActivity extends Fragment {

    public static TextView AlarmTextView;
    public static TimePicker alarmTimePicker;

    public void setAlarmText(String alarmText)
    {
        AlarmTextView.setText(alarmText);
    }

    public int GetHours ()
    {
        return alarmTimePicker.getCurrentHour();
    }

    public int GetMinutes ()
    {
        return alarmTimePicker.getCurrentMinute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.DialogTheme);
        //LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);


        View view = inflater.inflate(R.layout.fragment_main, container, false);
        //View view = localInflater.inflate(R.layout.fragment_main, container, false);
        //AlarmTextView.setText(mavideo.getTitleQuietly("Sw9uicEGjGw"));
        alarmTimePicker = (TimePicker) view.findViewById(R.id.ClockAlarmPicker);
        alarmTimePicker.setIs24HourView(true);


       // return localInflater.inflate(R.layout.fragment_main, container, false);

        return view;
    }


}
