package com.example.cholesterol;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ObtainPractitionerID {

    public static void makeRequest(String url){
        RequestQueue queue = Volley.newRequestQueue(MainActivity.context);

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    APIData.setResponse(response);
                                } catch (Exception e) {

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("stock", error.getMessage());
                            }
                        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        queue.add(jsonObjectRequest);
    }

    public static JSONObject getIdentifier(String practitionerID){
        JSONObject results = null;
        String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Practitioner/" + practitionerID + "?_format=json";

        try {
            if(practitionerID.isEmpty()){
                Toast.makeText(MainActivity.context, "Cannot be left empty", Toast.LENGTH_LONG).show();
            }else {
                makeRequest(url);
                results = APIData.getResponse();
            }

        }catch (Exception e){
        }

        if (results != null){
            try{
                String identifier1 = null;
                String identifier2 = null;
                String identifier = null;
                JSONArray identify = results.getJSONArray("identifier");
                identifier1 = identify.getJSONObject(0).getString("system");
                identifier2 = identify.getJSONObject(0).getString("value");

                identifier = identifier1 + "|" + identifier2;



                Log.d("IDEN", identifier);




            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }






}
