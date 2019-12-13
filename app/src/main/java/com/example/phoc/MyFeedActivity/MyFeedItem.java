package com.example.phoc.MyFeedActivity;

public class MyFeedItem {
    String postId;
    String title;
    String Comment;
    String Date;
    String imgUri;

    public MyFeedItem(String postId, String title, String comment, String date, String imgUri) {
        this.title = title;
        this.Comment = comment;
        this.Date = date;
        this.imgUri = imgUri;
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

}
