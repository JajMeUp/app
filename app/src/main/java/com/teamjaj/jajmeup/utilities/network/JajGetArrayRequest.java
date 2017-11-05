package com.teamjaj.jajmeup.utilities.network;


import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;

public class JajGetArrayRequest extends JajRequest<JSONArray> {

    public JajGetArrayRequest(String token, String url, Response.Listener<JSONArray> responseListener, Response.ErrorListener listener) {
        super(token, Method.GET, url, responseListener, listener);
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        if(response.statusCode != 200) {
            return Response.error(new VolleyError(response));
        }

        try {
            return Response.success(new JSONArray(new String(response.data)), null);
        } catch (JSONException e) {
            return Response.error(new VolleyError(response));
        }
    }
}
