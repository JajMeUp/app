package com.teamjaj.jajmeup.utilities.network;


import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class JajGetObjectRequest extends JajRequest<JSONObject> {

    public JajGetObjectRequest(String token, String url, Response.Listener<JSONObject> responseListener, Response.ErrorListener listener) {
        super(token, Method.GET, url, responseListener, listener);
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
}
