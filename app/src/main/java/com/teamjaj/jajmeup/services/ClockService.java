package com.teamjaj.jajmeup.services;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.teamjaj.jajmeup.dtos.Alarm;
import com.teamjaj.jajmeup.utilities.network.JajGetObjectRequest;
import com.teamjaj.jajmeup.utilities.network.JajPostRequest;
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

    public void makeVote(final Context ctx, String URL, String message, long target) {
        Map<String, String> params = new HashMap<>();
        params.put("targetId", Long.toString(target));
        params.put("link", URL);
        params.put("message", message);

        Response.Listener<Void> responseListerner = new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                Toast.makeText(ctx, "A vote !", Toast.LENGTH_SHORT).show();
            }
        };

        JajPostRequest request = new JajPostRequest(
                getToken(ctx),
                computeRequestURL(ctx, String.format("/api/alarm/create")),
                new JSONObject(params),
                responseListerner,
                new DefaultErrorListener(ctx)
        );
        queueRequest(ctx, request);
    }
}
