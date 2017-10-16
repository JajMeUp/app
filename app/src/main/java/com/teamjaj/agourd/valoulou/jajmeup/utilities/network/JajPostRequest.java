package com.teamjaj.agourd.valoulou.jajmeup.utilities.network;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.Map;

public class JajPostRequest extends JajRequest<Void> {

    private JSONObject payload;

    public JajPostRequest(String token, String url, JSONObject payload, Response.Listener<Void> listener, Response.ErrorListener errorListener) {
        super(token, Method.POST, url, listener, errorListener);

        this.payload = payload;
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> headers = super.getHeaders();
        headers.put("Content-type", "application/json");

        return headers;
    }

    @Override
    public byte[] getBody() {
        return payload.toString().getBytes();
    }

    @Override
    protected Response<Void> parseNetworkResponse(NetworkResponse response) {
        if (response.statusCode != 201 && response.statusCode != 204) {
            // If status is NOT "201 - Created" or "204 - No Content" then it's a failure
            return Response.error(new VolleyError(response));
        }

        return Response.success(null, null);
    }
}
