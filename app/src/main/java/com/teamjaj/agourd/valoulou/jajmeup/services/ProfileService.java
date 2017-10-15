package com.teamjaj.agourd.valoulou.jajmeup.services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.teamjaj.agourd.valoulou.jajmeup.dtos.Profile;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.network.JajGetArrayRequest;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.network.JajPostRequest;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.network.listeners.DefaultErrorListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileService extends AbstractService {

    public static final String BROADCAST_PROFILE_SEARCH_RESULTS = "com.teamjaj.jajmeup.PROFILE_SEARCH_RESULTS";

    private List<Profile> lastSearchResults = new ArrayList<>();
    private boolean isSearchingProfiles = false;

    public List<Profile> getLastSearchResults() {
        return lastSearchResults;
    }

    public void update(final Context context, String visibility) {
        Map<String, String> params = new HashMap<>();
        params.put("visibility", visibility);

        Response.Listener<Void> responseListener = new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
            }
        };

        JajPostRequest request = new JajPostRequest(
                getToken(context),
                computeRequestURL(context, "/api/profile/update"),
                new JSONObject(params),
                responseListener,
                new DefaultErrorListener(context));
        queueRequest(context, request);
    }

    public void findByName(final Context context, String name) {
        if (isSearchingProfiles) {
            return; // Prevent to send another request if the previous one is not finished
        }

        isSearchingProfiles = true;
        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                lastSearchResults.clear();
                for(int i = 0; i < response.length(); i++) {
                    try {
                        lastSearchResults.add(new Profile(response.getJSONObject(i)));
                    } catch (JSONException e) {
                        Log.e(this.getClass().getSimpleName(), "Error while mapping Profiles");
                    }
                }
                Toast.makeText(context, String.format("Found %d profiles", lastSearchResults.size()), Toast.LENGTH_SHORT).show();
                Intent broadcast = new Intent(BROADCAST_PROFILE_SEARCH_RESULTS);
                context.sendBroadcast(broadcast);
                isSearchingProfiles = false;
            }
        };

        JajGetArrayRequest request = new JajGetArrayRequest(
                getToken(context),
                computeRequestURL(context, String.format("/api/profile/search?q=%s", name)),
                responseListener,
                new DefaultErrorListener(context)
        );
        queueRequest(context, request);
    }
}
