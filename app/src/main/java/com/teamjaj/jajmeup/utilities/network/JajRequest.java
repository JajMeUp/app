package com.teamjaj.jajmeup.utilities.network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

public abstract class JajRequest<T> extends Request<T> {

    private String token;
    private Response.Listener<T> responseListener;

    public JajRequest(String token, int method, String url, Response.Listener<T> responseListener, Response.ErrorListener listener) {
        super(method, url, listener);

        this.token = token;
        this.responseListener = responseListener;
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        headers.put("Accept", "application/json");

        return headers;
    }

    @Override
    protected void deliverResponse(T response) {
        Log.d(getClass().getSimpleName(), String.format("Delivering response for %s", getUrl()));
        responseListener.onResponse(response);
    }
}
