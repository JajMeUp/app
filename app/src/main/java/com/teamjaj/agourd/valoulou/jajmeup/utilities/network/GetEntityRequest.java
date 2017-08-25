package com.teamjaj.agourd.valoulou.jajmeup.utilities.network;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GetEntityRequest extends Request<JSONObject> {

    private Map<String, String> params;
    private Response.Listener<JSONObject> listener;

    public GetEntityRequest(String url, Map<String, String> params, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);

        this.params = params;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");

        return headers;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        if(response.statusCode != 200) {
            return Response.error(new VolleyError(response));
        }

        try {
            return Response.success(new JSONObject(new String(response.data)), null);
        } catch (JSONException e) {
            return Response.error(new VolleyError(response));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }
}
