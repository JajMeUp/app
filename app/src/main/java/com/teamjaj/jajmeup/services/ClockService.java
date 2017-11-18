package com.teamjaj.jajmeup.services;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Response;
import com.teamjaj.jajmeup.activities.YoutubeActivity;
import com.teamjaj.jajmeup.dtos.Alarm;
import com.teamjaj.jajmeup.utilities.network.JajGetObjectRequest;
import com.teamjaj.jajmeup.utilities.network.JajPostRequest;
import com.teamjaj.jajmeup.utilities.network.JajRequest;
import com.teamjaj.jajmeup.utilities.network.listeners.DefaultErrorListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClockService extends AbstractService {

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
                        Alarm alarm = new Alarm(response);
                        Intent startYT = new Intent(context, YoutubeActivity.class);
                        startYT.putExtra("link", alarm.getURL());
                        startYT.putExtra("message", alarm.getMessage());
                        startYT.putExtra("voter", alarm.getVoterName());
                        Intent intent = new Intent();
                        intent.setAction("jajmeup.messagereveil");
                        //intent.putExtra("voter",voter);
                        //intent.putExtra("message_voter", message_voter);
                        context.sendBroadcast(intent);
                        context.startActivity(startYT);

                    } catch (JSONException ignore) {
                    }
            }
        };

        JajRequest<JSONObject> request = new JajGetObjectRequest(
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

        Response.Listener<Void> responseListener = new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                Toast.makeText(ctx, "A vote !", Toast.LENGTH_SHORT).show();
            }
        };

        JajPostRequest request = new JajPostRequest(
                getToken(ctx),
                computeRequestURL(ctx, "/api/alarm/create"),
                new JSONObject(params),
                responseListener,
                new DefaultErrorListener(ctx)
        );
        queueRequest(ctx, request);
    }
}
