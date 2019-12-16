package com.example.phoc.SubscribeUserListActivity;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SubscribeUserListItem {
    String userName;
    public String userId;

    public SubscribeUserListItem(String json, String id) {
        JsonElement ele = new JsonParser().parse(json);
        JsonObject obj = ele.getAsJsonObject();

        this.userName = obj.get("nick").getAsString();
        this.userId = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
