package com.teamjaj.jajmeup.utilities.network.listeners;


import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.teamjaj.jajmeup.activities.SignActivity;

public class DefaultErrorListener implements Response.ErrorListener {

    private Context context;

    public DefaultErrorListener(Context context) {
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        if (error.networkResponse != null && error.networkResponse.statusCode == 401) {
            Intent signIntent = new Intent(context, SignActivity.class);
            context.startActivity(signIntent);
        }
    }
}
