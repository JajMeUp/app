package com.wakemeup.ektoplasma.valou.wakemeup.fragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.wakemeup.ektoplasma.valou.wakemeup.R;

import org.json.JSONObject;

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
        AlarmTextView = (TextView) view.findViewById(R.id.AlarmText);
        //AlarmTextView.setText(mavideo.getTitleQuietly("Sw9uicEGjGw"));
        alarmTimePicker = (TimePicker) view.findViewById(R.id.ClockAlarmPicker);
        alarmTimePicker.setIs24HourView(true);


       // return localInflater.inflate(R.layout.fragment_main, container, false);

        return view;
    }


}
