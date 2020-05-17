package com.example.cholesterol;

import android.content.Context;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TreeMap;

public class testingPatientList_V1 extends List {

    private static ArrayList<ArrayList<String>> patientList;

    public static ArrayList<ArrayList<String>> getPatientList() {
        return patientList;
    }

    public static void setPatientList(ArrayList<ArrayList<String>> ids) {
        patientList = ids;
    }

    /**
     *
     * @param practitionerID
     * @param context
     * @param recyclerView
     */
    public static void getPatientList(String practitionerID, final Context context, final RecyclerView recyclerView) {

        RequestQueue queue2 = volleyHandler.getInstance(context).getQueue();

        String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Practitioner/" + practitionerID + "?_format=json";

        try {
            if (practitionerID.isEmpty()) {
                Toast.makeText(MainActivity.context, "Cannot be left empty", Toast.LENGTH_LONG).show();
            } else {

//              This will first use the url with practitioner ID to obtain the identifier for the practitioner.
                JsonObjectRequest stringRequest =
                        new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            aux_getPatientList(response, context, recyclerView);
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

        } catch (Exception e) {
        }
    }


    public static void aux_getPatientList(JSONObject response, final Context context, final RecyclerView recyclerView) {
        RequestQueue queue2 = volleyHandler.getInstance(context).getQueue();
        String identifier = null;
        if (response != null) {
            try {
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
                        + identifier + "&_sort=-date&_count=100&_format=json";
                Log.d("url", url);

                JsonObjectRequest stringRequest =
                        new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            cleanPatientList(response, recyclerView);
//                                            CholesterolData.cleanPatientList(response, context, recyclerView);

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

    public static void cleanPatientList(JSONObject response, final RecyclerView recyclerView) throws JSONException {
        ArrayList<ArrayList<String>> patientDetailsList = new ArrayList<>();
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> idList = new ArrayList<>();

        try {
            String idString;
            String patientID;
            String name;
            JSONArray entry = response.getJSONArray("entry");
            for (int i = 0; i < entry.length(); i++) {
                try {
                    idString = entry.getJSONObject(i).getJSONObject("resource").getJSONObject("subject").getString("reference");
                    name = (entry.getJSONObject(i).getJSONObject("resource").getJSONObject("subject").getString("display")).replaceAll("[\\d]", "");
                    patientID = idString.substring(8);

                    if (!idList.contains(patientID)) {
                        idList.add(patientID);
                        nameList.add(name);
                    }

                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
        patientDetailsList.add(idList);
        patientDetailsList.add(nameList);
        setPatientList(patientDetailsList);
        Log.d("final", String.valueOf(patientDetailsList));
//        PatientListAdapter patientListAdapter = new PatientListAdapter(patientDetailsList);
//        recyclerView.setAdapter(patientListAdapter);
    }


}