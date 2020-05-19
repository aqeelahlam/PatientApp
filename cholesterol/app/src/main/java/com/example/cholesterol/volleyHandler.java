package com.example.cholesterol;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class volleyHandler {
    private static volleyHandler instance;
    private RequestQueue queue;
    private static Context context;

    private volleyHandler(Context context) {
        volleyHandler.context = context;
        queue = getQueue();
    }

    public static synchronized volleyHandler getInstance(Context context) {
        if (instance == null) {
            instance = new volleyHandler(context);
        }
        return instance;
    }

    public RequestQueue getQueue() {
        if (queue == null) {
            // getApplicationContext() is key. It should not be activity context,
            // or else RequestQueue won’t last for the lifetime of your app
            queue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return queue;
    }

    public  void addToQueue(Request x) {
        getQueue().add(x);
    }

}