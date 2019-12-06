package com.example.phoc.SubscribeUserActivity;

public class SubscribeUserItem {
    String title;
    String comment;
    String userName;
    String date;
    String imgUri;

    public SubscribeUserItem(String title, String comment, String userName, String date, String imgUri) {
        this.title = title;
        this.comment = comment;
        this.userName = userName;
        this.date = date;
        this.imgUri = imgUri;
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

}
