package com.example.cholesterol;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class CholesterolData extends List{

    private static HashMap<String, Patient> patientDetail = new HashMap<>();

    public static HashMap<String, Patient> getPatientDetail(){
        return patientDetail;
    }

    public static void setPatientDetail(HashMap<String, Patient> patientDetail1){
        patientDetail = patientDetail1;
    }



    public static void setPatients(ArrayList<String> patients, Context context, final RecyclerView recyclerView) throws JSONException {

        for (int i=0; i<patients.size(); i++){

            getCholesterol(patients.get(i), context, recyclerView, new APIListener() {
                @Override
                public void onError(String message) {
                }

                @Override
                public void onResponse(JSONObject response, int counter) throws JSONException {





                }
            });




        }


        }

        public static void getCholesterol(final String patientID, final Context context, final RecyclerView recyclerView, final APIListener listener){
            RequestQueue queue = volleyHandler.getInstance(context).getQueue();

            String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?_count=13&code=2093-3&patient=" + patientID + "&_sort=-date&_format=json";


            try {
                JsonObjectRequest jsonObjectRequest =
                        new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            int total = response.getInt("total");
                                            if (total>0){
                                                JSONArray entry = response.getJSONArray("entry");
                                                double cholValue = entry.getJSONObject(0).getJSONObject("resource").getJSONObject("valueQuantity").getDouble("value");
                                                String cholUnit = entry.getJSONObject(0).getJSONObject("resource").getJSONObject("valueQuantity").getString("unit");

                                                Patient patient = new Patient(patientID, cholValue+cholUnit);
                                                patientDetail.put(patientID, patient);


                                            }

                                        } catch (JSONException e) {
                                        }


                                    }
                                }, new Response.ErrorListener(){
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
            }


        }

}
