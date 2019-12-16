package com.example.phoc.TitleListActivity;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TitleListItem {
    String title;

    public TitleListItem(String json) {
        JsonElement ele = new JsonParser().parse(json);
        JsonObject obj = ele.getAsJsonObject();

        this.title = obj.get("name").getAsString();

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
