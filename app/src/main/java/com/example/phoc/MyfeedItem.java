package com.example.phoc;

public class MyfeedItem{
    String title;
    String Comment;
    String Date;

    public MyfeedItem(String title, String comment, String date) {
        this.title = title;
        this.Comment = comment;
        this.Date = date;
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
}
