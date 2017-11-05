package com.teamjaj.jajmeup.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.teamjaj.jajmeup.R;
import com.teamjaj.jajmeup.services.ProfileService;

public class ClockSettings extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String PREF_VISIBILITY_KEY = "pref_visibility";
    private ProfileService profileService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileService = new ProfileService();

        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(PREF_VISIBILITY_KEY)) { // TODO see how to do this in a generic way
            String visibility = sharedPreferences.getString(key, "");
            if (!visibility.isEmpty()) {
                profileService.update(this.getActivity().getApplicationContext(), visibility);
            }
        }
    }
}
