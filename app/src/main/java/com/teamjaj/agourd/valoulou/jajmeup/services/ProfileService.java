package com.teamjaj.agourd.valoulou.jajmeup.services;


import android.content.Context;

import com.android.volley.Response;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.network.PostRequest;
import com.teamjaj.agourd.valoulou.jajmeup.utilities.network.listeners.DefaultErrorListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileService extends AbstractService {

    public void update(final Context context, String visibility) {
        Map<String, String> params = new HashMap<>();
        params.put("visibility", visibility);

        Response.Listener<Void> responseListener = new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
            }
        };

        PostRequest request = new PostRequest(
                computeRequestURL(context, "/api/profile/update"),
                new JSONObject(params),
                responseListener,
                new DefaultErrorListener(context));
        queueRequest(context, request);
    }
}
