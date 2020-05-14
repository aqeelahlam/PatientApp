package com.example.cholesterol;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
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

public class PractitionerIdentifier extends List {

    private static ArrayList<Practitioner> practitioners = new ArrayList<>();

    public static ArrayList<Practitioner> getPractitioners(){ return practitioners; }

    public static void setPractitioners(ArrayList<Practitioner> practitioners1){ practitioners = practitioners1; }

    public static Practitioner getPractitionerID(String practitionerID, final Context context) {
        RequestQueue queue = volleyHandler.getInstance(context).getQueue();

        String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Practitioner/" + practitionerID + "?_format=json";

        final Practitioner[] practitioner = new Practitioner[1];

        try {
            if(practitionerID.isEmpty()){
                Toast.makeText(MainActivity.context, "Invalid Request", Toast.LENGTH_LONG).show();
            } else{
                JsonObjectRequest stringRequest =
                        new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {

                                            String HPidentifier = null;
                                            String ident1 = null;
                                            String ident2 = null;

                                            JSONArray identifier = response.getJSONArray("identifier");
                                            ident1 = identifier.getJSONObject(0).getString("system");
                                            ident2 = identifier.getJSONObject(0).getString("value");

                                            HPidentifier = ident1 + "%7C" + ident2;

                                            practitioner[0] = new Practitioner(HPidentifier);

//                                            practitioners.add(practitioner);

                                        } catch (JSONException e) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return practitioner[0];

    }
}
