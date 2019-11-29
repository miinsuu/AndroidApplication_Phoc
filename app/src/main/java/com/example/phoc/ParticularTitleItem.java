package com.example.phoc;

public class ParticularTitleItem {
    String comment;
    String userName;
    String date;

    public ParticularTitleItem(String comment, String userName, String date) {
        this.comment = comment;
        this.userName = userName;
        this.date = date;
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
