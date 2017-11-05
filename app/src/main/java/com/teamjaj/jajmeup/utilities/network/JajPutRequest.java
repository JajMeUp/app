package com.teamjaj.jajmeup.utilities.network;


import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

public class JajPutRequest extends JajRequest<Void> {

    public JajPutRequest(String token, String url, Response.Listener<Void> responseListener, Response.ErrorListener listener) {
        super(token, Method.PUT, url, responseListener, listener);
    }

    @Override
    protected Response<Void> parseNetworkResponse(NetworkResponse response) {
        if (response.statusCode != 204) {
            return Response.error(new VolleyError(response));
        }

        return Response.success(null, null);
    }
}
