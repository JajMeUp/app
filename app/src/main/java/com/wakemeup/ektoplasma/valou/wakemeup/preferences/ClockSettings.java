package com.wakemeup.ektoplasma.valou.wakemeup.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

import com.wakemeup.ektoplasma.valou.wakemeup.R;
import com.wakemeup.ektoplasma.valou.wakemeup.utilities.Caller;

/**
 * Created by Valentin on 22/11/2016.
 */

public class ClockSettings extends PreferenceActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.clocksettings);
        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        EditTextPreference editTextPref = (EditTextPreference) findPreference("prefReveilDefault");
        editTextPref
                .setSummary(sp.getString("prefReveilDefault", "Lien YouTube"));
        ListPreference lp = (ListPreference) findPreference("prefWhoWakeMe");
        lp.setSummary(sp.getString("prefWhoWakeMe", "Tout le monde"));
        Preference historydel = findPreference("prefHistory");
        Preference whowakemedel = findPreference("prefWhoWakeMe");
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferenceScreen.removePreference(historydel);
        preferenceScreen.removePreference(whowakemedel);
    }

    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }



    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        Preference pref = findPreference(key);
        String oldPref = (String)pref.getSummary();
        if (pref instanceof EditTextPreference) {
            EditTextPreference etp = (EditTextPreference) pref;
            pref.setSummary(etp.getText());
        }
        if (pref instanceof ListPreference) {
            ListPreference listp = (ListPreference) pref;
            pref.setSummary(listp.getEntry());
            if(key.equals("prefWhoWakeMe")){
                String newPref = (String) pref.getSummary();
                if(newPref.equals("Tout le monde"))
                {
                    Caller.newPref("world");
                }
                else if(newPref.equals("Seulement moi"))
                {
                    Caller.newPref("private");
                }
                else {
                    Caller.newPref("friends");
                }

            }
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("prefHistory",sharedPreferences.getBoolean("prefHistory", false));
        editor.commit();
    }
}
