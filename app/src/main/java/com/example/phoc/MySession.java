package com.example.phoc;

public class MySession {
    private static MySession session = new MySession();
    private String email = null;
    private String nickname;


    private MySession(){

    }
    public static MySession getSession(){
        return session;
    }
    public void putUserInfo(String email)
    {
        this.email = email;
       //this.nickname = nickname;
    }
    public boolean isLoggedIn(){
        if(email != null)
            return true;
        else
            return false;
    }
}
