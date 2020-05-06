package com.example.cholesterol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.cholesterol.APIRequest;

/*
* Android: 9.0 (Pie)
* API Level: 28
*
* Make sure the app is given "Internet" access within the AndroidManifest.xml  :-
* Place the dependency right below "package"
* <uses-permission android:name="android.permission.INTERNET" />
*
* I have used Volley to perform the relevant network requests.
* https://developer.android.com/training/volley
*
*
* */

public class MainActivity extends AppCompatActivity {
//START
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    TextView tv;
    ArrayList<String> cholesterol = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        CholesterolAdapter cholesterolAdapter = new CholesterolAdapter(cholesterol);

        recyclerView.setAdapter(cholesterolAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        tv = findViewById(R.id.tv);
    }

    public void trialBtn(View view) {

        EditText keyword;
        keyword = findViewById(R.id.editText);
        String patientID = keyword.getText().toString();




//        makeRequest(patientID);

    }

//        private void makeRequest(String keywords) {
//            RequestQueue queue = Volley.newRequestQueue(this);
//            String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?_count=13&code=2093-3&patient=" + keywords + "&_sort=date&_format=json";
//
//            JsonObjectRequest stringRequest =
//                    new JsonObjectRequest(Request.Method.GET, url, null,
//                            new Response.Listener<JSONObject>() {
//                                @Override
//                                public void onResponse(JSONObject response) {
//                                    try {
////                                      Create an array of patient items
//                                        ArrayList<Patient> patients = new ArrayList<>();
//                                        double cholValue;
//                                        Patient patient;
//                                        String chol;
//                                        String CholLevel;
//                                        String unitValue;
//                                        JSONArray entry = response.getJSONArray("entry");
//                                        for (int i = 0; i < entry.length(); i++) {
//                                            try {
//                                                cholValue = entry.getJSONObject(i).getJSONObject("resource").getJSONObject("valueQuantity").getDouble("value");
//                                                unitValue = entry.getJSONObject(i).getJSONObject("resource").getJSONObject("valueQuantity").getString("unit");
//                                                chol = String.valueOf(cholValue);
//
//                                                CholLevel = chol + unitValue;
//
//                                                patient = new Patient(chol);
//
////                                              The cholesterol Levels will be added to this ArrayList
//                                                cholesterol.add(CholLevel);
//
//                                                patients.add(patient);
//
////                                              This is the sout:
//                                                Log.d("chol", String.valueOf(patients));
//                                            } catch (Exception e) {
//                                            }
//                                        }
//                                    } catch (Exception e) {
//
//                                    }
//                                }
//                            }, new Response.ErrorListener() {
//
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Log.d("stock", error.getMessage());
//                        }
//                    });
//            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                    50000,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//            queue.add(stringRequest);
//        }
}
