package com.example.cholesterol.ServerCalls;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cholesterol.Objects.Patient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class BloodPressureData {



    public static void getBloodPressure(final HashMap<String, Patient> patients, final HashMap<String, Patient> monitoredPatients, final Context context, final RecyclerView recyclerView){
        RequestQueue queue = VolleyHandler.getInstance(context).getQueue();

        final Object[] patientsBundle = patients.keySet().toArray();

        for (final Object patientID : patientsBundle){
            String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?_count=13&code=55284-4&patient=" + patientID +  "&_sort=-date&_format=json";

            final String patientIDStr = patientID.toString();

            try{
                final JsonObjectRequest jsonObjectRequest =
                        new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            int total = response.getInt("total");
                                            if (total > 0) {
                                                cleanBP(response, patients, monitoredPatients, patientIDStr, recyclerView, context);
                                            }
                                        } catch (JSONException | ParseException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        100000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.start();
                queue.add(jsonObjectRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void cleanBP(JSONObject response, HashMap<String, Patient> patientHashMap, HashMap<String, Patient> monitoredPatients, String patientID, final RecyclerView recyclerView, final Context context) throws JSONException, ParseException {
        JSONArray entry = response.getJSONArray("entry");
        double systolicBP = entry.getJSONObject(0).getJSONObject("resource").getJSONArray("component").getJSONObject(1).getJSONObject("valueQuantity").getInt("value");
        String systolicBPUnit = entry.getJSONObject(0).getJSONObject("resource").getJSONArray("component").getJSONObject(1).getJSONObject("valueQuantity").getString("unit");

        double diasystolicBP = entry.getJSONObject(0).getJSONObject("resource").getJSONArray("component").getJSONObject(0).getJSONObject("valueQuantity").getInt("value");
        String diasystolicBPUnit = entry.getJSONObject(0).getJSONObject("resource").getJSONArray("component").getJSONObject(0).getJSONObject("valueQuantity").getString("unit");

        String effectiveDate = entry.getJSONObject(0).getJSONObject("resource").getString("effectiveDateTime");

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        Date result;

        result = df.parse(effectiveDate);

        patientHashMap.get(patientID).setSystolic(systolicBP + systolicBPUnit);











    }

}
