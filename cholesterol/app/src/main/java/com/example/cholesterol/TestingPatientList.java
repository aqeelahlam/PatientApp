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

public class TestingPatientList extends List {

    private static ArrayList<ArrayList<String>> patientList;
    private static ArrayList<JSONObject> jsonData = new ArrayList<>();


    public static ArrayList<ArrayList<String>> getPatientList() {
        return patientList;
    }

    public static void setPatientList(ArrayList<ArrayList<String>> ids) {
        patientList = ids;
    }


    public static void addJsonData(JSONObject response) {
        if (response != null) {
            jsonData.add(response);
        }
    }


    public static ArrayList<JSONObject> getJsonData() {
        return jsonData;
    }


    public static void getPatientList(String practitionerID, final Context context, final RecyclerView recyclerView) {
        RequestQueue queue = volleyHandler.getInstance(context).getQueue();

        String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Practitioner/" + practitionerID + "?_format=json";

        try {
            if (practitionerID.isEmpty()) {
                Toast.makeText(MainActivity.context, "Cannot be left empty", Toast.LENGTH_LONG).show();
            } else {

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
                queue.start();
                queue.add(stringRequest);
            }

        } catch (Exception e) {
        }
        Log.d("final", String.valueOf(getJsonData()));

    }








    public static void aux_getPatientList(JSONObject response, final Context context, final RecyclerView recyclerView) {
        RequestQueue queue = volleyHandler.getInstance(context).getQueue();

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
            String firstUrl = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Encounter?_include=Encounter.participant.individual&_include=Encounter.patient&participant.identifier="
                    + identifier + "&_sort=-date&_count=100&_format=json";


            try {
                Log.d("firstUrl", firstUrl);

                JsonObjectRequest stringRequest =
                        new JsonObjectRequest(Request.Method.GET, firstUrl, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            addJsonData(response);
                                            Log.d("1st", String.valueOf(response));
                                            int counter = 1;
                                            Log.d("counter", String.valueOf(counter));

                                            JSONArray link = response.getJSONArray("link");
                                            for (int i = 0; i < link.length(); i++) {
                                                if (link.getJSONObject(i).getString("relation").equals("next")) {
                                                    makeRequestRecursive(response, context, counter);
                                                }
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
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

                queue.start();
                queue.add(stringRequest);

            } catch (Exception e) {
                e.printStackTrace();
            }
//            cleanPatientList(jsonData);
        }
    }



    public static void makeRequestRecursive(JSONObject response, final Context context, int counter) throws JSONException {
        RequestQueue queue = volleyHandler.getInstance(context).getQueue();

        String url = null;

        JSONArray link = response.getJSONArray("link");
        for (int i = 0; i < link.length(); i++) {
            if (link.getJSONObject(i).getString("relation").equals("next")) {
                url = link.getJSONObject(i).getString("url");
            }
        }
        Log.d("nextURL", url);
        counter++;
        Log.d("counter", String.valueOf(counter));


        if (counter <= 20) {



            try {
                final int finalCounter = counter;
                JsonObjectRequest stringRequest =
                        new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            addJsonData(response);
                                            Log.d("nextData", String.valueOf(response));

                                            JSONArray link2 = response.getJSONArray("link");


                                            for (int i = 0; i < link2.length(); i++) {
                                                if (link2.getJSONObject(i).getString("relation").equals("next"))   {
                                                    makeRequestRecursive(response, context, finalCounter);
                                                }
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
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
                queue.start();
                queue.add(stringRequest);
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
        PatientListAdapter patientListAdapter = new PatientListAdapter(patientDetailsList);
        recyclerView.setAdapter(patientListAdapter);
    }
}
