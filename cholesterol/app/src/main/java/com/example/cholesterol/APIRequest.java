package com.example.cholesterol;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class APIRequest {



    public static void makeRequest2(String url, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest stringRequest =
                new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>(){
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
//                                    Log.d("response", String.valueOf(response));
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


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);
    }



//    public static JSONObject getCholesterol(String patientID) {
//        String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?_count=13&code=2093-3&patient=" + patientID + "&_sort=-date&_format=json";
//
//        makeRequest2(url);
//        JSONObject results = APIData.getResponse();
//
//    }



    public static void makeRequest(String keywords, final ArrayList<String> CholList, Context context, final RecyclerView recyclerView) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?_count=13&code=2093-3&patient=" + keywords + "&_sort=date&_format=json";

        JsonObjectRequest stringRequest =
                new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
//                                Create an array of patient items
                                    ArrayList<Patient> patients = new ArrayList<>();
                                    String test = null;
                                    double cholValue;
                                    Patient patient;
                                    String chol = null;
                                    String CholLevel = null;
                                    String unitValue;
                                    JSONArray entry = response.getJSONArray("entry");
                                    for (int i = 0; i < entry.length(); i++) {
                                        try {
                                            cholValue = entry.getJSONObject(i).getJSONObject("resource").getJSONObject("valueQuantity").getDouble("value");
                                            unitValue = entry.getJSONObject(i).getJSONObject("resource").getJSONObject("valueQuantity").getString("unit");
                                            chol = String.valueOf(cholValue);

                                            CholLevel = chol + unitValue;

                                            patient = new Patient(chol);

                                            CholList.add(CholLevel);

                                            patients.add(patient);

                                            Log.d("chol", String.valueOf(patients));
                                        } catch (Exception e) {
                                        }

                                    }
                                    CholesterolAdapter cholesterolAdapter = new CholesterolAdapter(CholList);
                                    recyclerView.setAdapter(cholesterolAdapter);
                                } catch (Exception e) {

                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("stock", error.getMessage());
                    }

                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);
    }



}
