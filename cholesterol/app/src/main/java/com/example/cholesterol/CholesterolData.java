package com.example.cholesterol;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cholesterol.adapters.PatientListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class CholesterolData extends List{

//    private static HashMap<String, Patient> patientDetail = new HashMap<>();

//    public static HashMap<String, Patient> getPatientDetail(){
//        return patientDetail;
//    }
//
//    public static void setPatientDetail(HashMap<String, Patient> patientDetail1){
//        patientDetail = patientDetail1;
//    }


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
        RequestQueue queue = volleyHandler.getInstance(context).getQueue();

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
                                                cleanChol(response, patients, monitoredPatients, patientID ,recyclerView);
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

    public static void cleanChol(JSONObject response, HashMap<String, Patient> patientHashMap, HashMap<String, Patient> monitoredPatients, String patientID, final RecyclerView recyclerView) throws JSONException, ParseException {

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

}
