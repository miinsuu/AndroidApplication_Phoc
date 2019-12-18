package com.example.phoc.SubscribeUserActivity;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SubscribeUserItem {
    public String title;
    public String comment;
    public String userName;
    public String date;
    public String imgUri;
    public String userId;
    public String exifJsonString;
    public String phocNum;

    public SubscribeUserItem(String json, String id) {
        JsonElement ele = new JsonParser().parse(json);
        JsonObject obj = ele.getAsJsonObject();

        this.userId = obj.get("userId").getAsString();
        this.title = obj.get("theme").getAsString();
        this.comment = obj.get("content").getAsString();
        this.userName = obj.get("nick").getAsString();
        this.date = obj.get("createdAt").getAsString();
        this.imgUri = obj.get("img").getAsString();
        this.exifJsonString = obj.get("camera").getAsString();
    }

    public String getExifJsonString() {
        return exifJsonString;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getImgUri(){ return this.imgUri;}

    public String getPhocNum() {
        return phocNum;
    }

    public void setPhocNum(String phocNum) {
        this.phocNum = phocNum;
    }
}
