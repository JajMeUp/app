package com.teamjaj.agourd.valoulou.jajmeup.dtos;

import org.json.JSONException;
import org.json.JSONObject;

public class PendingFriendship {
    private Long id;
    private String requesterName;

    public PendingFriendship(JSONObject data) throws JSONException {
        setId(data.getLong("id"));
        setRequesterName(data.getString("requesterName"));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }
}
