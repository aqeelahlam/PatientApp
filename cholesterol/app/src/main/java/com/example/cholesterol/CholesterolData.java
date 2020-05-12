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
import java.util.TreeMap;

public class CholesterolData extends List{

    private static ArrayList<ArrayList<String>> cholesterolData;

    public static ArrayList<ArrayList<String>> getCholesterolData() {
        return cholesterolData;
    }

    public static void setCholesterolData(ArrayList<ArrayList<String>> cholesterolData1){
        cholesterolData = cholesterolData1;
    }

    public static String chol;

    public static String getChol(){
        return chol;
    }
    public static void setChol(String chol1){
        chol = chol1;
    }



    public static void cleanPatientList(JSONObject response, Context context, final RecyclerView recyclerView) throws JSONException {
        ArrayList<ArrayList<String>> patientDetailsList = new ArrayList<>();
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> cholList = new ArrayList<>();

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

                    if (!cholList.contains(patientID)) {

                        getCholLevel(patientID, context);
                        cholList.add(getChol());
                        nameList.add(name);
                    }

                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
        patientDetailsList.add(cholList);
        patientDetailsList.add(nameList);
        setCholesterolData(patientDetailsList);
        Log.d("final", String.valueOf(patientDetailsList));
        PatientListAdapter patientListAdapter = new PatientListAdapter(patientDetailsList);
        recyclerView.setAdapter(patientListAdapter);
    }

    public static void getCholLevel(String patientID, final Context context) {

        RequestQueue queue2 = volleyHandler.getInstance(context).getQueue();

        String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?_count=13&code=2093-3&patient=" + patientID + "&_sort=date&_format=json";

        Log.d("url", url);

        try {
            if (patientID.isEmpty()) {
                Toast.makeText(MainActivity.context, "Cannot be left empty", Toast.LENGTH_LONG).show();
            } else {
                JsonObjectRequest stringRequest =
                        new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            JSONArray entry = response.getJSONArray("entry");

                                            double a = entry.getJSONObject(0).getJSONObject("resource").getJSONObject("valueQuantity").getDouble("value");
                                            String b = entry.getJSONObject(0).getJSONObject("resource").getJSONObject("valueQuantity").getString("unit");

                                            final String cholLevel = String.valueOf(a) + b;

                                            Log.d("chol", cholLevel);

                                            setChol(cholLevel);

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











}
