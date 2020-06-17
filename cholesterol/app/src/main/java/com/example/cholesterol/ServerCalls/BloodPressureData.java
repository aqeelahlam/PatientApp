package com.example.cholesterol.ServerCalls;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cholesterol.Adapters.MonitorAdapter;
import com.example.cholesterol.Objects.Patient;
import com.example.cholesterol.UserInterfaces.MainActivity;
import com.example.cholesterol.UserInterfaces.MonitorActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BloodPressureData {



    public static void getBloodPressure(final HashMap<String, Patient> patients, final HashMap<String, Patient> monitoredPatients, final Context context, final RecyclerView recyclerView){
        RequestQueue queue = VolleyHandler.getInstance(context).getQueue();

        final Object[] patientsBundle = patients.keySet().toArray();

        assert patientsBundle != null;
        for (final Object patientID : patientsBundle){
            String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?_count=13&code=55284-4&patient=" + patientID +  "&_sort=-date&_format=json";

            final String patientIDStr = patientID.toString();
            Log.d("PatientID", patientIDStr);


            try{
                final JsonObjectRequest jsonObjectRequest =
                        new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            int total = response.getInt("total");
                                            if (total > 0) {
                                                Log.d("PeopleWithBP", patientIDStr);
                                                cleanBP(response, patients, monitoredPatients, patientIDStr, recyclerView, context);
                                            }
                                            else {
                                                patients.remove(patientID);
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
        systolicBPUnit = systolicBPUnit.replaceAll("[^A-Za-z]","");

        double diasystolicBP = entry.getJSONObject(0).getJSONObject("resource").getJSONArray("component").getJSONObject(0).getJSONObject("valueQuantity").getInt("value");
        String diasystolicBPUnit = entry.getJSONObject(0).getJSONObject("resource").getJSONArray("component").getJSONObject(0).getJSONObject("valueQuantity").getString("unit");
        diasystolicBPUnit = diasystolicBPUnit.replaceAll("[^A-Za-z]","");

        String effectiveDate = entry.getJSONObject(0).getJSONObject("resource").getString("effectiveDateTime");

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        Date result;

        result = df.parse(effectiveDate);

        patientHashMap.get(patientID).setSystolic(systolicBP + systolicBPUnit);
        patientHashMap.get(patientID).setEffectiveDate(result.toString());

        try {
            MainActivity.setMonitoredPatients(patientID, patientHashMap.get(patientID));
        } catch ( Exception e) {

        }
    }



    private static ArrayList<JSONObject> updatedData = new ArrayList<>();


    public static void addUpdatedData(JSONObject response) {
        if (response != null) {
            updatedData.add(response);
        }
    }


    public static ArrayList<JSONObject> getUpdatedData() {
        return updatedData;
    }


    public static void resetUpdatedData() {
        updatedData = new ArrayList<>();
    }


    public static void getUpdateBP(final HashMap<String, Patient> patients, final HashMap<String, Patient> monitoredPatients, final Context context) {

        resetUpdatedData();

        final Object[] patientsBundle = monitoredPatients.keySet().toArray();

        int counter = 0;
        final int bundleLength = patientsBundle.length;


        assert patientsBundle != null;
        while (counter < bundleLength) {
            counter++;
            String patientId = String.valueOf(patientsBundle[counter-1]);

            String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?_count=13&code=55284-4&patient=" + patientId +  "&_sort=-date&_format=json";

            updateHandler(url, context, counter, new APIListener() {
                @Override
                public void onError(String message) {

                }

                @Override
                public void onResponse(JSONObject response, int counter2) throws JSONException, ParseException, InterruptedException {
                    addUpdatedData(response);
                    ArrayList<JSONObject> results = getUpdatedData();
                    Log.d("currentResultsSize", String.valueOf(results.size()));

                    if (getUpdatedData().size() == monitoredPatients.size()) {
                        cleanUpdatedBP(getUpdatedData(), patients, monitoredPatients, patientsBundle, context);
                    }
                }
            });

        }
    }


    public static void updateHandler(final String url, final Context context, final int counter, final APIListener listener) {
        RequestQueue queue = VolleyHandler.getInstance(context).getQueue();

        try {

            JsonObjectRequest jsonObjectRequest =
                    new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        listener.onResponse(response, counter);
                                    } catch (JSONException | ParseException | InterruptedException e) {
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
        }
    }


    public static void cleanUpdatedBP(ArrayList<JSONObject> responseList, HashMap<String, Patient> patientHashMap, HashMap<String, Patient> monitoredPatients, Object[] patientsBundle, Context context) throws JSONException, ParseException, InterruptedException {

        for(int i = 0; i < responseList.size(); i++) {

            JSONArray entry = responseList.get(i).getJSONArray("entry");
//            double systolicBP = entry.getJSONObject(0).getJSONObject("resource").getJSONArray("component").getJSONObject(1).getJSONObject("valueQuantity").getInt("value");
            String systolicBPUnit = entry.getJSONObject(0).getJSONObject("resource").getJSONArray("component").getJSONObject(1).getJSONObject("valueQuantity").getString("unit");
            systolicBPUnit = systolicBPUnit.replaceAll("[^A-Za-z]","");

            double diasystolicBP = entry.getJSONObject(0).getJSONObject("resource").getJSONArray("component").getJSONObject(0).getJSONObject("valueQuantity").getInt("value");
            String diasystolicBPUnit = entry.getJSONObject(0).getJSONObject("resource").getJSONArray("component").getJSONObject(0).getJSONObject("valueQuantity").getString("unit");
            diasystolicBPUnit = diasystolicBPUnit.replaceAll("[^A-Za-z]","");

            String effectiveDate = entry.getJSONObject(0).getJSONObject("resource").getString("effectiveDateTime");


//          If you want to see the observer changing, UnComment this line below and comment the above 'systolicBP'
            double systolicBP = Math.random()*100;

            // Change to appropriate format
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
            Date result;

            result = df.parse(effectiveDate);

            String patientID = patientsBundle[i].toString();

            patientHashMap.get(patientID).setSystolic(systolicBP + systolicBPUnit);
            patientHashMap.get(patientID).setEffectiveDate(result.toString());

            monitoredPatients.get(patientID).setSystolic(systolicBP + systolicBPUnit);
            monitoredPatients.get(patientID).setEffectiveDate(result.toString());

        }


        MonitorAdapter monitorAdapter = new MonitorAdapter(monitoredPatients, context);
        MonitorActivity.refresh(monitorAdapter);

    }

}
