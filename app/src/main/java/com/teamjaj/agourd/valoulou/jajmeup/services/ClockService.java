package com.teamjaj.agourd.valoulou.jajmeup.services;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.teamjaj.agourd.valoulou.jajmeup.R;
import com.teamjaj.agourd.valoulou.jajmeup.activities.LoginActivity;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.QueueSingleton;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.network.JajGetArrayRequest;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.network.JajRequest;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.network.PostRequest;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.network.listeners.DefaultErrorListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    public void makeVote(final Context context, String URL, String message, long target)
    {
        Map<String, String> params = new HashMap<>();
        params.put("URL", URL);
        params.put("Message", message);
        params.put("Target", Long.toString(target));

        Response.Listener<Void> responseListener = new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                Toast.makeText(context, "Ton vote a bien ete pris en compte", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(context, "Ton vote n'a pas ete pris en compte c'est toi qui a le seum", Toast.LENGTH_LONG).show();
                }
            }
        };

        PostRequest request = new PostRequest(
                String.format("http://%s:8080/register", context.getResources().getString(R.string.hostname_server)),
                new JSONObject(params),
                responseListener,
                errorListener);
        QueueSingleton.getInstance(context).addToRequestQueue(request);
    }
}
