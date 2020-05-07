package com.example.cholesterol;

import org.json.JSONObject;

public class APIData {
    private static JSONObject response;

    public APIData(JSONObject response){
        this.response = response;

    }

    public static void setResponse(JSONObject response) {
        APIData.response = response;
    }

    public static JSONObject getResponse() {
        return response;
    }
}
