package com.teamjaj.agourd.valoulou.jajmeup.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.teamjaj.agourd.valoulou.jajmeup.R;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.QueueSingleton;

public abstract class AbstractService {

    private final String PREFS_NAME = "NETWORK_DATA";
    private final String PREF_SESSION_TOKEN = "TOKEN";

    protected String computeRequestURL(Context context, String URI) {
        return String.format("http://%s:8080%s", context.getResources().getString(R.string.hostname_server), URI);
    }

    protected void queueRequest(Context context, Request request) {
        QueueSingleton.getInstance(context).addToRequestQueue(request);
    }

    protected String getToken(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(PREF_SESSION_TOKEN, "");
    }

    protected void setToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(PREF_SESSION_TOKEN, token);
        editor.apply();
    }
}
