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
import com.example.cholesterol.UserInterfaces.MainActivity;
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


public class CholesterolData extends MedicalObservations {

    public CholesterolData() {
        super();
    }

    @Override
    public void cleanObservation(final String job, final int totalObservationTypes, boolean graphView, JSONObject response, HashMap<String, Patient> monitoredPatients, String patientID, RecyclerView recyclerView, Context context, int counter, int max_length) throws JSONException, ParseException {
        int total = response.getInt("total");
        if (total > 0) {
//          We use the response
            JSONArray entry = response.getJSONArray("entry");
            double cholValue = entry.getJSONObject(0).getJSONObject("resource").getJSONObject("valueQuantity").getDouble("value");
            String cholUnit = entry.getJSONObject(0).getJSONObject("resource").getJSONObject("valueQuantity").getString("unit");
            String effectiveDate = entry.getJSONObject(0).getJSONObject("resource").getString("effectiveDateTime");

            String result;

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
            Date d = df.parse(effectiveDate);
            df.applyPattern("dd-M-yyyy hh:mm:ss");
            result = df.format(d);

            if (job.equals("Update")) {
                cholValue = Math.random() * 100;
            }

//          Here we set the latest cholesterol values for each patient with record of cholesterol Level.
            monitoredPatients.get(patientID).setCholesterol(cholValue + cholUnit);
            monitoredPatients.get(patientID).setEffectiveDateChol(result);
        }



        // if it is an update api call
        if (job.equals("Update")) {
            // display values only if totalObservationTypes == 1. > 1 means that there are other api calls to make
            if (!graphView) {
                if (totalObservationTypes == 1 && counter == max_length) {
                    MonitorAdapter monitorAdapter = new MonitorAdapter(MainActivity.getMonitoredPatients(), MainActivity.context);
                    MonitorActivity.refresh(monitorAdapter);
                }
            }
        }
    }

    @Override
    public void cleanLatestXObservations(String job, int totalObservationTypes, int X, JSONObject response, HashMap<String, Patient> monitoredPatients, String patientID, RecyclerView recyclerView, Context context, int counter, int max_length) throws JSONException, ParseException {

    }

}
