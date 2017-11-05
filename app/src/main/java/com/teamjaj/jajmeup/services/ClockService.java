package com.teamjaj.jajmeup.services;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.teamjaj.jajmeup.activities.LoginActivity;
import com.teamjaj.jajmeup.dtos.Alarm;
import com.teamjaj.jajmeup.utilities.QueueSingleton;
import com.teamjaj.jajmeup.utilities.network.JajGetObjectRequest;
import com.teamjaj.jajmeup.utilities.network.PostRequest;
import com.teamjaj.jajmeup.utilities.network.listeners.DefaultErrorListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClockService extends AbstractService {

    public static final String BROADCAST_REFRESH_CLOCK_ALARM_REQUEST = "com.teamjaj.jajmeup.CLOCK_ALARM";

    private Alarm lastAlarm;

    public Alarm getLastAlarm() {
        return lastAlarm;
    }

    public void requestLastAlarm(final Context context)
    {
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    try {
                        lastAlarm = new Alarm(response);
                        Intent broadcast = new Intent(BROADCAST_REFRESH_CLOCK_ALARM_REQUEST);
                        context.sendBroadcast(broadcast);
                    } catch (JSONException ignore) {
                        Log.e("ClockService", "Error while parsing Alarm from server response");
                    }
            }
        };

        JajGetObjectRequest request = new JajGetObjectRequest(
                getToken(context),
                computeRequestURL(context, "/api/alarm/lastalarm"),
                responseListener,
                new DefaultErrorListener(context));
        queueRequest(context, request);
    }

    public void makeVote(final Context context, String URL, String message, long target)
    {
        Map<String, String> params = new HashMap<>();
        params.put("link", URL);
        params.put("message", message);
        params.put("targetID", Long.toString(target));

        Response.Listener<Void> responseListener = new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                Toast.makeText(context, "Ton vote a bien été pris en compte", Toast.LENGTH_LONG).show();
                Intent login = new Intent(context, LoginActivity.class);
                context.startActivity(login);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    JSONObject responseErrors = new JSONObject(new String(error.networkResponse.data));
                    Toast.makeText(context, responseErrors.getString("defaultMessage"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(context, "Ton vote n'a pas été pris en compte c'est toi qui a le seum", Toast.LENGTH_LONG).show();
                }
            }
        };

        PostRequest request = new PostRequest(
                computeRequestURL(context, "/api/alarm/create"),
                new JSONObject(params),
                responseListener,
                errorListener);
        QueueSingleton.getInstance(context).addToRequestQueue(request);
    }
}
