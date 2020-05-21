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
import com.example.cholesterol.UserInterfaces.MonitorActivity;
import com.example.cholesterol.Objects.Patient;
import com.example.cholesterol.Adapters.PatientListAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class CholesterolData {

//    private static HashMap<String, Patient> patientDetail = new HashMap<>();

//    public static HashMap<String, Patient> getPatientDetail(){
//        return patientDetail;
//    }
//
//    public static void setPatientDetail(HashMap<String, Patient> patientDetail1){
//        patientDetail = patientDetail1;
//    }

    public static String placeHolder;

    public CholesterolData() {
        placeHolder = "Testing";
    }


    /**
     * This Function is used to send a request to the server
     * Request Type: Obtain Cholesterol Level of Patients
     * @param patients - This holds the HashMap of the Patients
     * @param context - Context
     * @param recyclerView -This refers to the recycler view that has been passed in from the
     *                      Main Activity
     *
     */

    public static void getCholesterol(final HashMap<String, Patient> patients, final HashMap<String, Patient> monitoredPatients, final Context context, final RecyclerView recyclerView){
        RequestQueue queue = VolleyHandler.getInstance(context).getQueue();

        final Object[] patientsBundle = patients.keySet().toArray();

        assert patientsBundle != null;
        for(int i = 0; i < patientsBundle.length; i++){

            String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?_count=13&code=2093-3&patient=" + patientsBundle[i] + "&_sort=-date&_format=json";

//            Log.d("PatientID", (String) patientsBundle[i]);

            final String patientID = patientsBundle[i].toString();

            try {

                JsonObjectRequest jsonObjectRequest =
                        new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            int total = response.getInt("total");
                                            if (total > 0){
//                                                Log.d("PeopleWithChol", patientID);
                                                cleanChol(response, patients, monitoredPatients, patientID ,recyclerView, context);
                                            }
                                            else {
                                                patients.remove(patientID);
                                            }

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

    /**
     * This function is used to add the cholesterol levels into the hashmap & display it in the
     * Recycler View
     * @param response - This holds the JSON object which we use to parse
     * @param patientHashMap - This holds the HashMap of the Patients
     * @param patientID - This is used to access a Patient Object of the HashMap
     * @param recyclerView - This refers to the recycler view that has been passed in from the
     *                     Main Activity
     * @throws JSONException
     *
     */

    public static void cleanChol(JSONObject response, HashMap<String, Patient> patientHashMap, HashMap<String, Patient> monitoredPatients, String patientID, final RecyclerView recyclerView, final Context context) throws JSONException, ParseException {

//      We use the response
        JSONArray entry = response.getJSONArray("entry");
        double cholValue = entry.getJSONObject(0).getJSONObject("resource").getJSONObject("valueQuantity").getDouble("value");
        String cholUnit = entry.getJSONObject(0).getJSONObject("resource").getJSONObject("valueQuantity").getString("unit");
        String effectiveDate = entry.getJSONObject(0).getJSONObject("resource").getString("effectiveDateTime");

//      Change to appropiate format
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        Date result;

        result = df.parse(effectiveDate);

//      Here we set the latest cholesterol values for each patient with record of cholesterol Level.
        patientHashMap.get(patientID).setCholesterol(cholValue + cholUnit);
        patientHashMap.get(patientID).setEffectiveDate(result.toString());

//      We will then pass the hashmap to the recycler view to show the results.
        PatientListAdapter patientListAdapter = new PatientListAdapter(patientHashMap, monitoredPatients);
        recyclerView.setAdapter(patientListAdapter);

    }



    private static ArrayList<JSONObject> updatedData = new ArrayList<>();

    /**
     * This Function is used to add API responses to updatedData
     * @param response - This is the API response
     *
     */
    public static void addUpdatedData(JSONObject response) {
        if (response != null) {
            updatedData.add(response);
        }
    }

    /**
     * This Function is used to get updatedData
     *
     */
    public static ArrayList<JSONObject> getUpdatedData() {
        return updatedData;
    }

    /**
     * This Function is used to reset updatedDate
     *
     */
    public static void resetUpdatedData() {
        updatedData = new ArrayList<>();
    }


    /**
     * This Function is used to call the API to get the latest cholesterol levels for the monitored patients
     * @param patients - This holds the HashMap of the Patients
     * @param monitoredPatients - This holds the HashMap of the monitored patients
     * @param context - Context
     *
     */
    public static void getUpdate(final HashMap<String, Patient> patients, final HashMap<String, Patient> monitoredPatients, final Context context) {

        resetUpdatedData();

        final Object[] patientsBundle = monitoredPatients.keySet().toArray();

        int counter = 0;
        final int bundleLength = patientsBundle.length;


        assert patientsBundle != null;
        while (counter < bundleLength) {
            counter++;
            String patientId = String.valueOf(patientsBundle[counter-1]);

            String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?_count=13&code=2093-3&patient=" + patientId + "&_sort=-date&_format=json";

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
                        cleanUpdatedChol(getUpdatedData(), patients, monitoredPatients, patientsBundle, context);
                    }
                }
            });

        }
    }


    /**
     * This Function is used to handle the API calls from getUpdate(), since we need to make multiple API calls
     * @param url - The url to call
     * @param context - Context
     * @param counter - a counter
     * @param listner - the APIListener
     *
     */
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


    /**
     * This Function is used to clean the API responses from getUpdate() and updateHandler()
     * @param responseList - This holds the list of API responses
     * @param patientHashMap - This holds the HashMap of all patients
     * @param monitoredPatients - This holds the HashMap of the monitored patients
     * @param patientsBundle - This holds the list of keys of patients needed
     * @param context - Context
     *
     */
    public static void cleanUpdatedChol(ArrayList<JSONObject> responseList, HashMap<String, Patient> patientHashMap, HashMap<String, Patient> monitoredPatients, Object[] patientsBundle, Context context) throws JSONException, ParseException, InterruptedException {

//      We use the response
        for(int i = 0; i < responseList.size(); i++) {
            JSONArray entry = responseList.get(i).getJSONArray("entry");
//            double cholValue = entry.getJSONObject(0).getJSONObject("resource").getJSONObject("valueQuantity").getDouble("value");
            String cholUnit = entry.getJSONObject(0).getJSONObject("resource").getJSONObject("valueQuantity").getString("unit");
            String effectiveDate = entry.getJSONObject(0).getJSONObject("resource").getString("effectiveDateTime");

            double cholValue = Math.random()*100;

            //      Change to appropiate format
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
            Date result;

            result = df.parse(effectiveDate);

            String patientID = patientsBundle[i].toString();


            //      Here we set the latest cholesterol values for each patient with record of cholesterol Level.
            patientHashMap.get(patientID).setCholesterol(cholValue + cholUnit);
            patientHashMap.get(patientID).setEffectiveDate(result.toString());

            monitoredPatients.get(patientID).setCholesterol(cholValue + cholUnit);
            monitoredPatients.get(patientID).setEffectiveDate(result.toString());
        }


        MonitorAdapter monitorAdapter = new MonitorAdapter(monitoredPatients, context);
        MonitorActivity.refresh(monitorAdapter);

    }



}
