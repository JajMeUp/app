package com.teamjaj.jajmeup.dtos;


import org.json.JSONException;
import org.json.JSONObject;

public class Profile {

    private Long id;
    private String displayName;

    public Profile(JSONObject data) throws JSONException {
        setId(data.getLong("id"));
        setDisplayName(data.getString("displayName"));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
