package com.example.cholesterol;

import org.json.JSONObject;

public class APIData {

    private static JSONObject response;
    private static String hpIdentifier;

    public APIData(JSONObject response){
        this.response = response;
    }

    public static void setResponse(JSONObject response) {
        APIData.response = response;
    }

    public static JSONObject getResponse() {
        return response;
    }

    public static void setHpIdentifier(String hpIdentifier) {
        APIData.hpIdentifier = hpIdentifier;
    }

    public static String getHpIdentifier() {
        return hpIdentifier;
    }
}
