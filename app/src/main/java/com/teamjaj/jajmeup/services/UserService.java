package com.teamjaj.jajmeup.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.teamjaj.jajmeup.activities.LoginActivity;
import com.teamjaj.jajmeup.activities.MainActivity;
import com.teamjaj.jajmeup.utilities.network.JajPostRequest;
import com.teamjaj.jajmeup.utilities.network.JajRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserService extends AbstractService {

    private final static String PREFS_NAME = "NETWORK_DATA";
    private final static String PREF_SESSION_TOKEN = "TOKEN";

    public void register(final Context ctx, String username, String displayName, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("displayName", displayName);
        params.put("password", password);

        Response.Listener<Void> responseListener = new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                Toast.makeText(ctx, "Ton inscription chez les JAJs s'est bien passée ! Connecte toi !", Toast.LENGTH_LONG).show();
                Intent login = new Intent(ctx, LoginActivity.class);
                ctx.startActivity(login);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    JSONObject responseErrors = new JSONObject(new String(error.networkResponse.data));
                    Toast.makeText(ctx, responseErrors.getString("defaultMessage"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(ctx, "F**k ! Y'a un blèm, ton inscription passe pas et on comprend pas pourquoi ...", Toast.LENGTH_LONG).show();
                }
            }
        };

        JajRequest request = new JajPostRequest(
                getToken(ctx),
                computeRequestURL(ctx, "/register"),
                new JSONObject(params),
                responseListener,
                errorListener);
        queueRequest(ctx, request);
    }

    public void login(final Context ctx, String username, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String token = response.getString("token");
                    SharedPreferences.Editor editor = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
                    editor.putString(PREF_SESSION_TOKEN, token);
                    editor.apply();
                    Intent login = new Intent(ctx, MainActivity.class);
                    ctx.startActivity(login);
                } catch (JSONException e) {
                    Toast.makeText(ctx,
                            "Hum ... on dirait bien que le serveur dit n'importe quoi, pas moyen de t'authentifier :/",
                            Toast.LENGTH_LONG).show();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    JSONObject responseErrors = new JSONObject(new String(error.networkResponse.data));
                    Toast.makeText(ctx, responseErrors.getString("defaultMessage"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(ctx, "Mouais, y'a eu un soucis avec le serveur, mais je saurais pas de dire quoi exactement ...", Toast.LENGTH_SHORT).show();
                }
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                computeRequestURL(ctx, "/login"),
                new JSONObject(params),
                responseListener,
                errorListener);
        queueRequest(ctx, request);
    }
}
