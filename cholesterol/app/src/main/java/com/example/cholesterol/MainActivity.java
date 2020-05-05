package com.example.cholesterol;

import androidx.appcompat.app.AppCompatActivity;
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
//END

    TextView tv;
//    ArrayList<Patient> patients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//START


        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//END
        tv = findViewById(R.id.tv);
    }

    public void trialBtn(View view) throws IOException, JSONException {

        EditText keyword;
        keyword = findViewById(R.id.editText);
        String patientID = keyword.getText().toString();
        makeRequest(patientID);

    }

        private void makeRequest(String keywords) {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?_count=13&code=2093-3&patient=" + keywords + "&_sort=date&_format=json";

            JsonObjectRequest stringRequest =
                    new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
//                                Create an array of patient items
//                                        ArrayList<Patient> patients = new ArrayList<>();

                                        String test = null;
                                        JSONObject entryList;
                                        JSONObject resource;
                                        JSONObject valueQuantity;
                                        JSONObject value;
                                        Patient patient;


                                        JSONArray entry = response.getJSONArray("entry");
                                        for (int i = 0; i < entry.length(); i++) {
                                            try {
                                                entryList = entry.getJSONObject(i);
                                                resource = entryList.getJSONObject("resource");

                                                //START
//                                                valueQuantity = resource.getJSONObject("valueQuantity");
//                                                value = valueQuantity.getJSONObject("value");
//                                                double cholesterol = value.getDouble("value");
//                                                patient = new Patient(cholesterol);
//                                                patients.add(patient);
                                                //END

                                                test = resource.getString("id");
                                            } catch (Exception e) {
                                            }
                                        }

                                        //START
//                                        adapter = new RecyclerAdapter(patients);
//                                        recyclerView.setAdapter(adapter);


                                        tv.setText(test.toString());
                                    } catch (Exception e) {

                                    }
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("stock", error.getMessage());
                        }
                    });
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(stringRequest);
        }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

//    private void makeRequest(String keywords) throws IOException, JSONException {
//        JSONObject json = readJsonFromUrl("https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?_count=13&code=2093-3&patient=" + keywords + "&_sort=date&_format=json");
//        JSONArray entry = json.getJSONArray("entry");
//
//
//        for(int i = 0; i < entry.length(); i++) {
//            JSONObject resource = entry.getJSONObject(i);
//            JSONObject valueQuantity = resource.getJSONObject("resource");
//
//            String id = valueQuantity.getString("id");
//
//            tv.setText(id);
//
//
//            JSONObject value = valueQuantity.getJSONObject("valueQuantity");
//            double cholesterol = value.getDouble("value");
//
//            patientLevel.add(cholesterol);
//
//
//
//        }
//    }
}
