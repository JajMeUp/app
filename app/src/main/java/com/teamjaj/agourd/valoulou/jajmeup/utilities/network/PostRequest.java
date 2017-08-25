package com.teamjaj.agourd.valoulou.jajmeup.utilities.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostRequest extends Request<Void> {

    private Response.Listener<Void> listener;
    private JSONObject payload;

    public PostRequest(String url, JSONObject payload, Response.Listener<Void> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);

        this.listener = listener;
        this.payload = payload;
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return headers;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return payload.toString().getBytes();
    }

    @Override
    protected Response<Void> parseNetworkResponse(NetworkResponse response) {
        if (response.statusCode != 201) {
            // If status is NOT "201 - Created" then it's a failure
            return Response.error(new VolleyError(response));
        }

        return Response.success(null, null);
    }

    @Override
    protected void deliverResponse(Void response) {
        listener.onResponse(response);
    }
}
