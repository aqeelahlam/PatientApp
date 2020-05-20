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
import com.example.cholesterol.List;
import com.example.cholesterol.Patient;
import com.example.cholesterol.VolleyHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PatientData extends List {

    private static HashMap<String, Patient> patientDetail = new HashMap<>();

    public static HashMap<String, Patient> getPatientDetail(){
        return patientDetail;
    }

    public static void setPatientDetail(HashMap<String, Patient> patientDetail1){
        patientDetail = patientDetail1;
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

    public static void getDetailedPatient(final HashMap<String, Patient> patients, final Context context, final RecyclerView recyclerView){

        RequestQueue queue = VolleyHandler.getInstance(context).getQueue();

        final String patientID = "29163";


        String url ="https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Patient/" + patientID + "?_format=json";

        try {

            JsonObjectRequest jsonObjectRequest =
                    new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        cleanDetails(response, patients, patientID ,recyclerView);

                                    } catch (JSONException e) {
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

    public static void cleanDetails(JSONObject response, HashMap<String, Patient> patientHashMap, String patientID, final RecyclerView recyclerView) throws JSONException {

        String gender = response.getString("gender");
        String birthDate = response.getString("birthDate");

        JSONObject address = response.getJSONArray("address").getJSONObject(0);
        String addressLine = address.getJSONArray("line").getString(0);
        String city = address.getString("city");
        String state = address.getString("state");
        String postalCode = address.getString("postalCode");
        String country = address.getString("country");


        Log.d("gender", gender);
        Log.d("birthDate", birthDate);
        Log.d("addressLine", addressLine);
        Log.d("city", city);
        Log.d("state", state);
        Log.d("postalCode", postalCode);
        Log.d("country", country);


        patientHashMap.get(patientID).setExtraDetails(birthDate, gender, addressLine, city, postalCode, state, country);




//      We will then pass the hashmap to the recycler view to show the results.
//        PatientListAdapter patientListAdapter = new PatientListAdapter(patientHashMap);
//        recyclerView.setAdapter(patientListAdapter);

    }


}
