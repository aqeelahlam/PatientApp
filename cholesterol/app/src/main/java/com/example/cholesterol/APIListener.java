package com.example.cholesterol;

import org.json.JSONException;
import org.json.JSONObject;

public interface APIListener {
    void onError(String message);

    void onResponse(JSONObject response, int counter) throws JSONException;
}
