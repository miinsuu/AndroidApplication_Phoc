package com.example.phoc.MyFeedActivity;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MyFeedItem {
    public String postId;
    public String title;
    public String Comment;
    public String Date;
    public String imgUri;
    public String exifJsonString;
    public String phocNum;

    public MyFeedItem(String postId, String json) {
        JsonElement ele = new JsonParser().parse(json);
        JsonObject obj = ele.getAsJsonObject();
        Log.d("Post", obj.toString());


        this.title = obj.get("theme").getAsString();;
        this.Comment = obj.get("content").getAsString();
        this.Date = obj.get("createdAt").getAsString();
        this.imgUri = obj.get("img").getAsString();
        this.exifJsonString = obj.get("camera").getAsString();
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }
    public String getImgUri() {
        return this.imgUri;
    }

    public String getPostId(){return this.postId;}

    public String getPhocNum() {
        return phocNum;
    }

    public void setPhocNum(String phocNum) {
        this.phocNum = phocNum;
    }
}
