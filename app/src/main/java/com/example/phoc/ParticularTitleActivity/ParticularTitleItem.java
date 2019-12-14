package com.example.phoc.ParticularTitleActivity;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ParticularTitleItem {
    String comment;
    String userName;
    String date;
    public String userId;
    public String imgUrl;
    String postId;

    public ParticularTitleItem(String json, String id) {
        JsonElement ele = new JsonParser().parse(json);
        JsonObject obj = ele.getAsJsonObject();
        Log.d("Post", obj.toString());

        this.comment = obj.get("content").getAsString();
        this.date = obj.get("createdAt").getAsString();
        this.userName = obj.get("nick").getAsString();
        this.userId = obj.get("userId").getAsString();
        this.imgUrl = obj.get("img").getAsString();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
