package com.example.phoc.DatabaseConnection;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public interface DataListListener {
    public void getData(ArrayList<JsonObject> data);
}
