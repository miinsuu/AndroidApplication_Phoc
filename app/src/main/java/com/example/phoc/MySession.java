package com.example.phoc;

import android.util.Log;

import com.example.phoc.DatabaseConnection.DataListener;
import com.example.phoc.DatabaseConnection.DatabaseQueryClass;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

public class MySession {
    private static MySession session = new MySession();
    private String email = null;
    private String nickname;
    private String userId;


    private MySession(){

    }
    public static MySession getSession(){
        return session;
    }
    public void putUserInfo(String email)
    {
        this.email = email;
        DatabaseQueryClass.User.getUserInfoByEmail(email, new DataListener() {
            @Override
            public void getData(Object data) {
                JsonElement element = new JsonParser().parse(data.toString());
                JsonObject jobj = element.getAsJsonObject();
                nickname = jobj.get("nick").toString();
                userId = jobj.get("userId").toString();

                Log.d("login", nickname + " : " +userId );
            }
        });
    }
    public boolean isLoggedIn(){
        if(email != null)
            return true;
        else
            return false;
    }
    public String getUserId(){
        return this.userId;
    }
    public void clearSession() {
         this.session = new MySession();
    }
}
