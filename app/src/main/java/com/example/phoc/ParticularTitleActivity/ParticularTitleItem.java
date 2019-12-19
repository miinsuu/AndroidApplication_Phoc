package com.example.phoc.ParticularTitleActivity;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ParticularTitleItem {
    String comment;
    String userName;
    String date;
    int phocNum;
    public String userId;
    public String imgUrl;
    String postId;
    public String exifJsonString;
    String title;
    public boolean isPhoccedFlag = false;

    public ParticularTitleItem(String json, String id) {
        JsonElement ele = new JsonParser().parse(json);
        JsonObject obj = ele.getAsJsonObject();
        Log.d("Post", obj.toString());
        Log.e("주제여부확인", obj.get("theme").getAsString());

        this.title = obj.get("theme").getAsString();
        this.comment = obj.get("content").getAsString();
        this.date = obj.get("createdAt").getAsString();
        this.userName = obj.get("nick").getAsString();
        this.userId = obj.get("userId").getAsString();
        this.imgUrl = obj.get("img").getAsString();
        this.exifJsonString = obj.get("camera").getAsString();
        this.postId = id;
        this.phocNum = obj.get("num_phoc").getAsInt();
    }

    public String getTitle() {
        return title;
    }

    public String getExifJsonString() {
        return exifJsonString;
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

    public int getPhocNum() {
        return phocNum;
    }

    public void setPhocNum(int phocNum) {
        this.phocNum = phocNum;
    }
}
