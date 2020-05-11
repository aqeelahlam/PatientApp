package com.example.cholesterol;

import android.content.Context;
import android.util.Log;
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

public class patientList extends List {

    private static JSONObject jsonResults;


    public static JSONObject getJsonResults() {
        return jsonResults;
    }

    public static void setJsonResults(JSONObject response) {
        jsonResults = response;
    }


    public static void getPatientList(String practitionerID, final Context context){
        RequestQueue queue2 = volleyHandler.getInstance(context).getQueue();

        String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Practitioner/" + practitionerID + "?_format=json";

        try {
            if(practitionerID.isEmpty()){
                Toast.makeText(MainActivity.context, "Cannot be left empty", Toast.LENGTH_LONG).show();
            } else {

                JsonObjectRequest stringRequest =
                        new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>(){
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            aux_getPatientList(response, context);
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


                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        100000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue2.start();
                queue2.add(stringRequest);
            }

        } catch (Exception e){
        }
    }


    public static void aux_getPatientList(JSONObject response, Context context){
        RequestQueue queue2 = volleyHandler.getInstance(context).getQueue();

        String identifier = null;
        if (response != null){
            try{
                String identifier1 = null;
                String identifier2 = null;
                JSONArray identify = response.getJSONArray("identifier");
                identifier1 = identify.getJSONObject(0).getString("system");
                identifier2 = identify.getJSONObject(0).getString("value");

                identifier = identifier1 + "%7C" + identifier2;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (identifier != null) {
            try {
                String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Encounter?_include=Encounter.participant.individual&_include=Encounter.patient&participant.identifier="
                        + identifier + "&_format=json";
                Log.d("url", url);

                JsonObjectRequest stringRequest =
                        new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>(){
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            setJsonResults(response);
                                            Log.d("setResponse", String.valueOf(response));
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


                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        100000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                queue2.start();
                queue2.add(stringRequest);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
