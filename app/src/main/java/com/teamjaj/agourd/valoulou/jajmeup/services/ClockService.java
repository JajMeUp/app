package com.teamjaj.agourd.valoulou.jajmeup.services;


import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.network.JajGetArrayRequest;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.network.JajRequest;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.network.listeners.DefaultErrorListener;

import org.json.JSONArray;
import org.json.JSONException;

public class ClockService extends AbstractService {

    public static final String BROADCAST_REFRESH_CLOCK_ALARM_REQUEST = "com.teamjaj.jajmeup.CLOCK_ALARM";

    public String LastAlarmAdress = new String();

    public void getLastAlarmRequest(final Context context)
    {
        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                    try {
                        LastAlarmAdress = response.getJSONObject(0).getString("LastAlarm");
                    } catch (JSONException ignore) {
                    }
            }
        };

        JajRequest<JSONArray> request = new JajGetArrayRequest(
                getToken(context),
                computeRequestURL(context, "/api/alarm/lastalarm"),
                responseListener,
                new DefaultErrorListener(context));
        queueRequest(context, request);
    }

    public void makeVote(final Context context, String URL, long target)
    {
        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                    Toast.makeText(context, "Vote accepté !", Toast.LENGTH_SHORT);
            }
        };

        JajRequest<JSONArray> request = new JajGetArrayRequest(
                getToken(context),
                computeRequestURL(context, String.format("/api/alarm/makevote/%s", URL)),//passer deux paramètres ?
                responseListener,
                new DefaultErrorListener(context));
        queueRequest(context, request);
    }
}
