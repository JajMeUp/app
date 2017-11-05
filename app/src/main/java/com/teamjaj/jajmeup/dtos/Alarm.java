package com.teamjaj.jajmeup.dtos;

import org.json.JSONException;
import org.json.JSONObject;

public class Alarm {
    private String voterName;
    private String URL;
    private String message;

    public Alarm(JSONObject data) throws JSONException {
        setVoterName(data.getString("voterName"));
        setURL(data.getString("URL"));
        setMessage(data.getString("message"));
    }

    public String getVoterName() { return voterName; }

    public String getURL() {
        return URL;
    }

    public String getMessage() { return message; }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setMessage(String message) { this.message = message; }

    public void setVoterName(String name) { this.voterName = name; }
}
