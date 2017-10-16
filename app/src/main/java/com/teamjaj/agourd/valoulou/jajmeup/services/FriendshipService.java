package com.teamjaj.agourd.valoulou.jajmeup.services;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Response;
import com.teamjaj.agourd.valoulou.jajmeup.dtos.PendingFriendship;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.network.JajGetArrayRequest;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.network.JajPostRequest;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.network.JajPutRequest;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.network.JajRequest;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.network.listeners.DefaultErrorListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FriendshipService extends AbstractService {

    public static final String BROADCAST_REFRESH_PENDING_FRIENDSHIP_REQUEST = "com.teamjaj.jajmeup.PENDING_FRIENDSHIP";

    private static List<PendingFriendship> pendingFriendships = new ArrayList<>();

    public List<PendingFriendship> getPendingFriendships() {
        return pendingFriendships;
    }

    public void getPendingFriendshipRequest(final Context context) {
        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pendingFriendships.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        pendingFriendships.add(new PendingFriendship(response.getJSONObject(i)));
                    } catch (JSONException ignore) {
                    }
                }
                updateList(context);
            }
        };

        JajRequest<JSONArray> request = new JajGetArrayRequest(
                getToken(context),
                computeRequestURL(context, "/api/friendship/pending"),
                responseListener,
                new DefaultErrorListener(context));
        queueRequest(context, request);
    }

    public void acceptFriend(final Context context, long id) {
        Response.Listener<Void> responseListener = new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                Toast.makeText(context, "Demande d'ami acceptée !", Toast.LENGTH_SHORT).show();
                getPendingFriendshipRequest(context);
            }
        };

        JajPutRequest request = new JajPutRequest(
                getToken(context),
                computeRequestURL(context, String.format("/api/friendship/accept/%d", id)),
                responseListener,
                new DefaultErrorListener(context)
        );
        queueRequest(context, request);
    }

    public void refuseFriend(final Context context, long id) {

        Response.Listener<Void> responseListener = new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                Toast.makeText(context, "Demande d'ami refusée.", Toast.LENGTH_SHORT).show();
                getPendingFriendshipRequest(context);
            }
        };

        JajPutRequest request = new JajPutRequest(
                getToken(context),
                computeRequestURL(context, String.format("/api/friendship/reject/%d", id)),
                responseListener,
                new DefaultErrorListener(context)
        );
        queueRequest(context, request);
    }

    public void sendFriendshipRequest(final Context context, long id) {

        Response.Listener<Void> responseListerner = new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                Toast.makeText(context, "Demande d'ami envoyée !", Toast.LENGTH_SHORT).show();
            }
        };

        JajPostRequest request = new JajPostRequest(
                getToken(context),
                computeRequestURL(context, String.format("/api/friendship/%d", id)),
                new JSONObject(),
                responseListerner,
                new DefaultErrorListener(context)
        );
        queueRequest(context, request);
    }

    private void updateList(Context context) {
        Intent broadcast = new Intent(BROADCAST_REFRESH_PENDING_FRIENDSHIP_REQUEST);
        context.sendBroadcast(broadcast);
    }
}
