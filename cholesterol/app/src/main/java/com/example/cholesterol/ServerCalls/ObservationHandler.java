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

public class ObservationHandler {


    public static void getObservation(final String job, final int totalObservationTypes, final String observationType, final boolean graphView, final HashMap<String, Patient> monitoredPatients, final Context context, final RecyclerView recyclerView){
        RequestQueue queue = VolleyHandler.getInstance(context).getQueue();

        final Object[] patientsBundle = monitoredPatients.keySet().toArray();

        assert patientsBundle != null;
        for(int i = 0; i < patientsBundle.length; i++){

            String url = "";
            if (observationType.equals("Chol")) {
                url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?_count=13&code=2093-3&patient=" + patientsBundle[i] + "&_sort=-date&_format=json";
            }
            else if (observationType.equals("BP") || observationType.equals("XBP")) {
                url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?_count=13&code=55284-4&patient=" + patientsBundle[i] +  "&_sort=-date&_format=json";

            }

            final String patientID = patientsBundle[i].toString();
//            Log.d("PatientID", patientID);

            try {

                final int counter = i;
                final int max_length = patientsBundle.length - 1;
                JsonObjectRequest jsonObjectRequest =
                        new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            int total = response.getInt("total");
                                            if (total > 0){
//                                                Log.d("PeopleWithobservation", patientID);
                                                if (observationType.equals("Chol")){
                                                    CholesterolData cholesterolData = new CholesterolData();
                                                    cholesterolData.cleanObservation(job, totalObservationTypes, graphView, response, monitoredPatients, patientID ,recyclerView, context, counter, max_length);
                                                }
                                                else if (observationType.equals("BP")) {
                                                    BloodPressureData bloodPressureData = new BloodPressureData();
                                                    bloodPressureData.cleanObservation(job, totalObservationTypes, graphView, response, monitoredPatients, patientID ,recyclerView, context, counter, max_length);
                                                }
                                                else if (observationType.equals("XBP")) {
                                                    BloodPressureData bloodPressureData = new BloodPressureData();
                                                    bloodPressureData.cleanLatestXObservations(job, totalObservationTypes, 5, response, monitoredPatients, patientID ,recyclerView, context, counter, max_length);
                                                }
                                            }
//                                            else {
//
////                                              WE replace the values here
//                                                patients.remove(patientID);
////                                                patients.remove(patientID);
//                                            }

                                        } catch (JSONException | ParseException e) {
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
    }


//    private static ArrayList<JSONObject> updatedData = new ArrayList<>();
//
//    /**
//     * This Function is used to add API responses to updatedData
//     * @param response - This is the API response
//     *
//     */
//    public static void addUpdatedData(JSONObject response) {
//        if (response != null) {
//            updatedData.add(response);
//        }
//    }
//
//    /**
//     * This Function is used to get updatedData
//     *
//     */
//    public static ArrayList<JSONObject> getUpdatedData() {
//        return updatedData;
//    }
//
//    /**
//     * This Function is used to reset updatedDate
//     *
//     */
//    public static void resetUpdatedData() {
//        updatedData = new ArrayList<>();
//    }
//
//
//    /**
//     * This Function is used to call the API to get the latest cholesterol levels for the monitored patients
//     * @param patients - This holds the HashMap of the Patients
//     * @param monitoredPatients - This holds the HashMap of the monitored patients
//     * @param context - Context
//     *
//     */
//    public static void getUpdatedObservation(final String observationType, final HashMap<String, Patient> patients, final HashMap<String, Patient> monitoredPatients, final Context context) {
//
//        resetUpdatedData();
//
//        final Object[] patientsBundle = monitoredPatients.keySet().toArray();
//
//        int counter = 0;
//        final int bundleLength = patientsBundle.length;
//
//
//        assert patientsBundle != null;
//        while (counter < bundleLength) {
//            counter++;
//            final String patientId = String.valueOf(patientsBundle[counter-1]);
//
//            String url = "";
//            if (observationType.equals("Chol")) {
//                url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?_count=13&code=2093-3&patient=" + patientId + "&_sort=-date&_format=json";
//            }
//            else if (observationType.equals("BP")) {
//                url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?_count=13&code=55284-4&patient=" + patientId +  "&_sort=-date&_format=json";
//
//            }
//
//            updateHandler(url, context, counter, new APIListener() {
//                @Override
//                public void onError(String message) {
//
//                }
//
//                @Override
//                public void onResponse(JSONObject response, int counter2) throws JSONException, ParseException, InterruptedException {
//                    addUpdatedData(response);
//                    ArrayList<JSONObject> results = getUpdatedData();
//                    Log.d("currentResultsSize", String.valueOf(results.size()));
//
//                    if (getUpdatedData().size() == monitoredPatients.size()) {
//                        if (observationType.equals("Chol")){
//                            CholesterolData cholesterolData = new CholesterolData();
//                            cholesterolData.cleanUpdatedObservation(getUpdatedData(), patients, monitoredPatients, patientsBundle, context);
//                        }
//                        else if (observationType.equals("BP")) {
//                            BloodPressureData bloodPressureData = new BloodPressureData();
//                            bloodPressureData.cleanUpdatedObservation(getUpdatedData(), patients, monitoredPatients, patientsBundle, context);
//                        }
//                    }
//                }
//            });
//
//        }
//    }
//
//
//    /**
//     * This Function is used to handle the API calls from getUpdate(), since we need to make multiple API calls
//     * @param url - The url to call
//     * @param context - Context
//     * @param counter - a counter
//     * @param listener - the APIListener
//     *
//     */
//    public static void updateHandler(final String url, final Context context, final int counter, final APIListener listener) {
//        RequestQueue queue = VolleyHandler.getInstance(context).getQueue();
//
//        try {
//
//            JsonObjectRequest jsonObjectRequest =
//                    new JsonObjectRequest(Request.Method.GET, url, null,
//                            new Response.Listener<JSONObject>() {
//                                @Override
//                                public void onResponse(JSONObject response) {
//                                    try {
//                                        listener.onResponse(response, counter);
//                                    } catch (JSONException | ParseException | InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//                        }
//                    });
//            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
//                    100000,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            queue.start();
//            queue.add(jsonObjectRequest);
//
//        } catch (Exception e) {
//        }
//    }



}
