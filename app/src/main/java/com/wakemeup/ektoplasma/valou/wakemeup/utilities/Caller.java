package com.wakemeup.ektoplasma.valou.wakemeup.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wakemeup.ektoplasma.valou.wakemeup.R;
import com.wakemeup.ektoplasma.valou.wakemeup.activities.MainActivity;
import com.wakemeup.ektoplasma.valou.wakemeup.activities.SignActivity;
import com.wakemeup.ektoplasma.valou.wakemeup.activities.YoutubeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ektoplasma on 06/08/16.
 */
public final class Caller {

    private static String username;
    private static String pseudonyme;
    private static String cookieInstance;
    private static List<String> Ami;
    private static List<String> NewAmi;
    private static List<String> NewMessages;
    private static List<String> NewSenders;
    private static List<String> World;
    private static List<String> HistoryVoter;
    private static List<String> HistoryLink;
    private static Context ctx;
    private static String currentLink;
    private static String state;
    private static boolean instance = false;
    private static String currentReceiver;
    private static String currentMessage;
    private static String currentVoter;
    private static String currentVideoName;
    private static List<String> NewTinyMessages;
    private static List<String> NewTinySenders;
    //private static List<String> NewTinyDate;

    private final static String PREFS_NAME = "COOKIE_WMU";
    private final static String PREF_SESSION_COOKIE = "session_cookie";
    private final static String PREF_DEFAULT_STRING = "";

    public static List<String> getNewTinyMessages() {
        return NewTinyMessages;
    }

    public static void setNewTinyMessages(List<String> newTinyMessages) {
        NewTinyMessages = newTinyMessages;
    }

    public static List<String> getNewTinySenders() {
        return NewTinySenders;
    }

    public static void setNewTinySenders(List<String> newTinySenders) {
        NewTinySenders = newTinySenders;
    }

    public static String getCurrentVideoName() {
        return currentVideoName;
    }

    public static void setCurrentVideoName(String currentVideoName) {
        Caller.currentVideoName = currentVideoName;
    }

    public static List<String> getHistoryVoter() {
        return HistoryVoter;
    }

    public static void setHistoryVoter(List<String> historyVoter) {
        HistoryVoter = historyVoter;
    }

    public static List<String> getHistoryLink() {
        return HistoryLink;
    }

    public static void setHistoryLink(List<String> historyLink) {
        HistoryLink = historyLink;
    }

    public static String getCurrentVoter() {
        return currentVoter;
    }

    public static void setCurrentVoter(String currentVoter) {
        Caller.currentVoter = currentVoter;
    }

    public static String getCurrentMessage() {
        return currentMessage;
    }

    public static void setCurrentMessage(String currentMessage) {
        Caller.currentMessage = currentMessage;
    }

    public static String getCurrentReceiver() {
        return currentReceiver;
    }

    public static void setCurrentReceiver(String currentReceiver) {
        Caller.currentReceiver = currentReceiver;
    }

    public static List<String> getNewSenders() {
        return NewSenders;
    }

    public static void setNewSenders(List<String> newSenders) {
        NewSenders = newSenders;
    }

    public static List<String> getNewMessages() { return NewMessages; }

    public static void setNewMessages(List<String> newMessages) { NewMessages = newMessages; }

    public static List<String> getNewAmi() { return NewAmi; }

    public static void setNewAmi(List<String> newAmi) { NewAmi = newAmi; }

    public static String getPseudonyme() {
        return pseudonyme;
    }

    public static void setPseudonyme(String pseudonyme) {
        Caller.pseudonyme = pseudonyme;
    }

    public static boolean isInstance() {
        return instance;
    }

    public static void setInstance(boolean instance) {
        Caller.instance = instance;
    }

    public static String getState() {
        return state;
    }

    public static void setState(String state) {
        Caller.state = state;
    }

    public static String getCurrentLink() {
        return currentLink;
    }

    public static void setCurrentLink(String currentLink) {
        Caller.currentLink = currentLink;
    }

    public static Context getCtx() {
        return ctx;
    }

    public static void setCtx(Context ctx) {
        Caller.ctx = ctx;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Caller.username = username;
    }

    public static String getCookieInstance() {
        return cookieInstance;
    }

    public static void setCookieInstance(String cookieInstance) { Caller.cookieInstance = cookieInstance; }

    public static List<String> getAmi() {
        return Ami;
    }

    public static void setAmi(List<String> ami) {
        Ami = ami;
    }

    public static List<String> getWorld() {
        return World;
    }

    public static void setWorld(List<String> world) {
        World = world;
    }

    private static SharedPreferences getPrefs() {
        return ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void storePersistantCookieString() {
        String sessionCookie = getPrefs().getString(PREF_SESSION_COOKIE, PREF_DEFAULT_STRING);

        if(!sessionCookie.equals(PREF_DEFAULT_STRING))
        {
            cookieInstance = sessionCookie;
        }
    }

    public static void checkCookie()
    {
        Map<String, String> params = new HashMap<>();
        params.put("cookie", cookieInstance);
        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    System.out.println("Succes: "+succes);

                    if(succes != null && succes.matches("true")) {
                        String pseudo = jsonResponse.getString("pseudo");
                        System.out.println("Pseudonyme: "+pseudo);
                        pseudonyme = pseudo;
                        Intent mainIntent = new Intent(ctx, MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ctx.startActivity(mainIntent);
                    }
                    else{
                        SharedPreferences.Editor editor = getPrefs().edit();
                        editor.putString(PREF_SESSION_COOKIE, PREF_DEFAULT_STRING);
                        editor.apply();
                        cookieInstance = "";
                        pseudonyme = "";
                        System.out.println("Try again please.");
                        Intent signIntent = new Intent(ctx, SignActivity.class);
                        signIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ctx.startActivity(signIntent);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Intent intent = new Intent("volley.error.message",null);
                ctx.sendBroadcast(intent);
            }
        };
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/check.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);

    }

    public static void signup(String user, String pseudo, String password){
        Map<String, String> params = new HashMap<>();
        params.put("user", user);
        params.put("pseudonyme", pseudo);
        params.put("password",password);

        username = user;
        pseudonyme = pseudo;

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    System.out.println("Succes: "+succes);

                    if(succes != null && succes.matches("true")) {
                        String cookie = jsonResponse.getString("cookie");
                        System.out.println("Cookie: "+cookie);
                        cookieInstance = cookie;
                        SharedPreferences.Editor editor = getPrefs().edit();
                        editor.putString(PREF_SESSION_COOKIE, cookieInstance);
                        editor.apply();
                        Intent mainIntent = new Intent(ctx, MainActivity.class);
                        ctx.startActivity(mainIntent);
                    }
                    else{
                        username = "";
                        pseudonyme = "";
                        System.out.println("Try again please.");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/create.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);
    }

    public static void signin(String user, String password){
        Map<String, String> params = new HashMap<>();
        params.put("user", user);
        params.put("password",password);

        username = user;

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    System.out.println("Succes: "+succes);

                    if(succes != null && succes.matches("true")) {
                        String cookie = jsonResponse.getString("cookie");
                        String pseudo = jsonResponse.getString("pseudonyme");
                        System.out.println("Cookie: "+cookie + " Pseudonyme: "+pseudo);
                        cookieInstance = cookie;
                        pseudonyme = pseudo;
                        Intent mainIntent = new Intent(ctx, MainActivity.class);
                        ctx.startActivity(mainIntent);
                        SharedPreferences.Editor editor = getPrefs().edit();
                        editor.putString(PREF_SESSION_COOKIE, cookieInstance);
                        editor.putString("prefUsername", pseudonyme);
                        editor.apply();
                    }
                    else{
                        username = "";
                        System.out.println("Try again please.");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/login.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);
    }

    public static void setClockSong(final String message){

        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);
        params.put("member",currentReceiver);
        params.put("link",currentLink);
        params.put("message", message);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        System.out.println("Succes: "+succes);
                        Toast.makeText(ctx, "Vidéo envoyée.", Toast.LENGTH_LONG).show();
                    }
                    else{
                        System.out.println("Could not set alarm song to "+currentReceiver);
                        Toast.makeText(ctx, "echec...", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/vote.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);

    }

    public static void getClockSong(){
        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        System.out.println("Succes: "+succes);
                        String link = jsonResponse.getString("link");
                        currentMessage = jsonResponse.getString("message");
                        currentVoter = jsonResponse.getString("voter");
                        Intent intent = new Intent(ctx, YoutubeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("YTLINK", link);
                        ctx.startActivity(intent);
                    }
                    else{
                        System.out.println("Could not get alarm song.");//er416Ad3R1g
                        currentMessage = "Good Morning ! (réveil par défaut)";
                        Intent intent = new Intent(ctx, YoutubeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("YTLINK", "er416Ad3R1g");
                        ctx.startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/awake.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);
    }

    public static void getBddAmi(){

        Ami = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        JSONObject jsonAmis = jsonResponse.getJSONObject("amis");
                        Iterator x = jsonAmis.keys();

                        int i = 0;

                        while (x.hasNext()){
                            String key = (String) x.next();
                            Ami.add(jsonAmis.get(key).toString());
                            System.out.println("Ami "+i+": "+Ami.get(i));
                            i++;
                        }
                        Set<String> hs = new HashSet<>();
                        hs.addAll(Ami);
                        Ami.clear();
                        Ami.addAll(hs);

                    }
                    else{
                        System.out.println("Could not fetch friends");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //Intent intent = new Intent("volley.error.message",null);
                //ctx.sendBroadcast(intent);
            }
        };
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/read.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);

    }

    public static void getBddWorld(){

        World = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        JSONObject jsonWorld = jsonResponse.getJSONObject("world");
                        Iterator x = jsonWorld.keys();

                        int i = 0;

                        while (x.hasNext()){
                            String key = (String) x.next();
                            World.add(jsonWorld.get(key).toString());
                            System.out.println("World "+i+": "+World.get(i));
                            i++;
                        }
                        Set<String> hs = new HashSet<>();
                        hs.addAll(World);
                        World.clear();
                        World.addAll(hs);

                    }
                    else{
                        System.out.println("Could not fetch world");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/world.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);

    }

    public static void getBddHistory(){

        HistoryVoter = new ArrayList<>();
        HistoryLink = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        JSONObject jsonVoters = jsonResponse.getJSONObject("voters");
                        JSONObject jsonLinks = jsonResponse.getJSONObject("ytlinks");
                        Iterator x = jsonVoters.keys();
                        Iterator y = jsonLinks.keys();

                        int i = 0;

                        while (x.hasNext() && y.hasNext()){
                            String key = (String) x.next();
                            String key_s = (String) y.next();
                            HistoryVoter.add(jsonVoters.get(key).toString());
                            HistoryLink.add(jsonLinks.get(key_s).toString());
                            System.out.println("HistoryVoter "+i+": "+HistoryVoter.get(i)+" link : "+HistoryLink.get(i));
                            i++;
                        }

                    }
                    else{
                        System.out.println("Could not fetch history");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/history.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);

    }

    public static void addFriend(final String friend)
    {

        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);
        params.put("friend", friend);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        Toast.makeText(ctx, "Demande d'ajout envoyée.", Toast.LENGTH_LONG).show();
                    }
                    else{
                        System.out.println("Could not send invite to "+friend+".");
                        Toast.makeText(ctx, "echec...", Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/add.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);
    }

    public static void sendMessage(final String message, final String voteur)
    {

        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);
        params.put("message", message);
        params.put("person", voteur);
        //TODO security

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        Toast.makeText(ctx, "Message envoyé.", Toast.LENGTH_LONG).show();
                    }
                    else{
                        System.out.println("Could not send message "+message+" to "+voteur+".");
                        Toast.makeText(ctx, "echec...", Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/send_msg.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);
    }

    public static void sendTinyMessage(final String message, final String user)
    {

        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);
        params.put("message", message);
        params.put("person", user);
        //TODO security

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        Toast.makeText(ctx, "Message envoyé.", Toast.LENGTH_LONG).show();
                    }
                    else{
                        System.out.println("Could not send message "+message+" to "+user+".");
                        Toast.makeText(ctx, "echec...", Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/send_tmsg.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);
    }

    public static void getNotif()
    {
        NewAmi = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);
        Log.d("ERROR getnotif", ctx.toString());

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        JSONObject jsonAmis = jsonResponse.getJSONObject("amis");
                        Iterator x = jsonAmis.keys();

                        int i = 0;

                        while (x.hasNext()){
                            String key = (String) x.next();
                            NewAmi.add(jsonAmis.get(key).toString());
                            System.out.println("NewAmi "+i+": "+NewAmi.get(i));
                            i++;
                        }
                        Set<String> hs = new HashSet<>();
                        hs.addAll(NewAmi);
                        NewAmi.clear();
                        NewAmi.addAll(hs);
                        Intent broadcast = new Intent("ekto.valou.badgebroadcast");
                        broadcast.putExtra("TYPE","friend");
                        broadcast.putExtra("COUNT",String.valueOf(i));
                        ctx.sendBroadcast(broadcast);
                    }
                    else{
                        Intent broadcast = new Intent("ekto.valou.badgebroadcast");
                        broadcast.putExtra("TYPE","friend");
                        broadcast.putExtra("COUNT",String.valueOf(0));
                        ctx.sendBroadcast(broadcast);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Intent intent = new Intent("volley.error.message");
                Log.d("ERROR", ctx.toString());
                ctx.sendBroadcast(intent);
            }
        };
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/notif.php",params, reponseListener, errorListener);
        /* ----- */
        NewMessages = new ArrayList<>();
        NewSenders = new ArrayList<>();
        Map<String, String> params2 = new HashMap<>();
        params2.put("cookie",cookieInstance);

        Response.Listener<JSONObject> reponseListener2= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        JSONObject jsonMessages = jsonResponse.getJSONObject("messages");
                        JSONObject jsonSenders = jsonResponse.getJSONObject("senders");
                        Iterator x = jsonMessages.keys();
                        Iterator y = jsonSenders.keys();

                        int i = 0;

                        while (x.hasNext() && y.hasNext()){
                            String key = (String) x.next();
                            String key_s = (String) y.next();
                            NewMessages.add(jsonMessages.get(key).toString());
                            NewSenders.add(jsonSenders.get(key_s).toString());
                            System.out.println("NewMessages "+i+": "+NewMessages.get(i)+" from : "+NewSenders.get(i));
                            i++;
                        }

                        Intent broadcast = new Intent("ekto.valou.badgebroadcast");
                        broadcast.putExtra("TYPE","message");
                        broadcast.putExtra("COUNT",String.valueOf(i));
                        ctx.sendBroadcast(broadcast);
                    }
                    else{
                        Intent broadcast = new Intent("ekto.valou.badgebroadcast");
                        broadcast.putExtra("TYPE","message");
                        broadcast.putExtra("COUNT",String.valueOf(0));
                        ctx.sendBroadcast(broadcast);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener2 = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        DataRequest requestor2 = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/notif_msg.php",params2, reponseListener2, errorListener2);
        /* -------  */
        NewTinyMessages = new ArrayList<>();
        NewTinySenders = new ArrayList<>();
        Map<String, String> params3 = new HashMap<>();
        params3.put("cookie",cookieInstance);

        Response.Listener<JSONObject> reponseListener3= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        JSONObject jsonMessages = jsonResponse.getJSONObject("messages");
                        JSONObject jsonSenders = jsonResponse.getJSONObject("senders");
                        Iterator x = jsonMessages.keys();
                        Iterator y = jsonSenders.keys();

                        int i = 0;

                        while (x.hasNext() && y.hasNext()){
                            String key = (String) x.next();
                            String key_s = (String) y.next();
                            NewTinyMessages.add(jsonMessages.get(key).toString());
                            NewTinySenders.add(jsonSenders.get(key_s).toString());
                            System.out.println("NewTinyMessages "+i+": "+NewTinyMessages.get(i)+" from : "+NewTinySenders.get(i));
                            i++;
                        }

                        Intent broadcast = new Intent("ekto.valou.badgebroadcast");
                        broadcast.putExtra("TYPE","message");
                        broadcast.putExtra("COUNT",String.valueOf(i));
                        ctx.sendBroadcast(broadcast);
                    }
                    else{
                        Intent broadcast = new Intent("ekto.valou.badgebroadcast");
                        broadcast.putExtra("TYPE","message");
                        broadcast.putExtra("COUNT",String.valueOf(0));
                        ctx.sendBroadcast(broadcast);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener3 = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        DataRequest requestor3 = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/notif_tiny.php",params3, reponseListener3, errorListener3);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);
        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor2);
        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor3);
    }

    public static void acceptFriend(final String friend)
    {

        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);
        params.put("friend", friend);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        Toast.makeText(ctx, "Ami ajouté.", Toast.LENGTH_LONG).show();
                    }
                    else{
                        System.out.println("Could not accept friend "+friend+".");
                        Toast.makeText(ctx, "echec...", Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/accept.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);
    }

    public static void refuseFriend(final String friend)
    {

        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);
        params.put("friend", friend);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        Toast.makeText(ctx, "Ami refusé.", Toast.LENGTH_LONG).show();
                    }
                    else{
                        System.out.println("Could not refuse friend "+friend+".");
                        Toast.makeText(ctx, "echec...", Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/refuse.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);
    }

    public static void nameYTvideo(String idVideo){
        Map<String, String> params = new HashMap<>();

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String title = response.getString("title");
                    if(title != null) {
                        Toast.makeText(ctx, "Nom de la vidéo :"+title, Toast.LENGTH_LONG).show();
                        currentVideoName = title;
                    }
                    else{
                        System.out.println("Could not fetch video name.");
                        Toast.makeText(ctx, "echec...", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        DataRequest requestor = new DataRequest(Request.Method.GET, "https://www.youtube.com/oembed?url=https://www.youtu.be/watch?v="+idVideo+"&format=json",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);

    }

    public static void newPref(String newPref)
    {
        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);
        params.put("pref", newPref);

        Response.Listener<JSONObject> reponseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String succes = jsonResponse.getString("succes");

                    if(succes != null && succes.matches("true")) {
                        Log.d("Caller", "OK");
                    }
                    else{
                        Log.d("Caller","Error");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        DataRequest requestor = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/pref.php",params, reponseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(requestor);
    }

    public static void sendPicture(final String picture)
    {

        Map<String, String> params = new HashMap<>();
        params.put("cookie",cookieInstance);
        params.put("image", picture);
        //TODO security

        Response.Listener<JSONObject> responseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String success = jsonResponse.getString("success");

                    if(!TextUtils.isEmpty(success) && success.matches("true")) {
                        Toast.makeText(ctx, "Photo de profil mise à jour !", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.e(Caller.class.getSimpleName(), "sendPicture: Error will sending the profile picture: " + jsonResponse.get("error"));
                        Toast.makeText(ctx, "Erreur lors de la synchronisation de la photo de profil ...", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e(Caller.class.getSimpleName(), "sendPicture: Bad response syntaxe ...");
                    Toast.makeText(ctx, "Erreur lors de la synchronisation de la photo de profil ...", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        DataRequest request = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/put_pic.php",params, responseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(request);
    }

    public static void getPicture()
    {
        Map<String, String> params = new HashMap<>();
        params.put("cookie", cookieInstance);
        //TODO security

        Response.Listener<JSONObject> responseListener= new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonResponse = response.getJSONObject("statut");
                    String success = jsonResponse.getString("success");

                    if(success != null && success.matches("true")) {
                        Log.d(this.getClass().getSimpleName(), "Profile picture successfully retrieved from the server");
                        Intent broadcast = new Intent("ekto.valou.picbroadcast");
                        broadcast.putExtra("data", jsonResponse.getString("picture"));
                        ctx.sendBroadcast(broadcast);
                    }
                    else {
                        Log.e(this.getClass().getSimpleName(), "Could NOT retrieved profile picture from the server: " + jsonResponse.getString("error"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        DataRequest request = new DataRequest(Request.Method.POST, "http://"+ ctx.getResources().getString(R.string.hostname_server) +"/get_pic.php", params, responseListener, errorListener);

        QueueSingleton.getInstance(ctx).addToRequestQueue(request);
    }
}
