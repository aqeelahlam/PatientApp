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
    public void cleanObservation(JSONObject response, HashMap<String, Patient> patientHashMap, HashMap<String, Patient> monitoredPatients, String patientID, RecyclerView recyclerView, Context context) throws JSONException, ParseException {
//      We use the response
        JSONArray entry = response.getJSONArray("entry");
        double cholValue = entry.getJSONObject(0).getJSONObject("resource").getJSONObject("valueQuantity").getDouble("value");
        String cholUnit = entry.getJSONObject(0).getJSONObject("resource").getJSONObject("valueQuantity").getString("unit");
        String effectiveDate = entry.getJSONObject(0).getJSONObject("resource").getString("effectiveDateTime");

//      Change to appropriate format
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        Date result;

        result = df.parse(effectiveDate);

//      Here we set the latest cholesterol values for each patient with record of cholesterol Level.
        patientHashMap.get(patientID).setCholesterol(cholValue + cholUnit);
        patientHashMap.get(patientID).setEffectiveDate(result.toString());

        try {
            MainActivity.setMonitoredPatients(patientID, patientHashMap.get(patientID));
        } catch ( Exception e) {

        }
    }

    @Override
    public void cleanUpdatedObservation(ArrayList<JSONObject> responseList, HashMap<String, Patient> patientHashMap, HashMap<String, Patient> monitoredPatients, Object[] patientsBundle, Context context) throws JSONException, ParseException, InterruptedException {
        //      We use the response
        for(int i = 0; i < responseList.size(); i++) {

//          We Parse the JSON to get the required values here:
            JSONArray entry = responseList.get(i).getJSONArray("entry");
//            double cholValue = entry.getJSONObject(0).getJSONObject("resource").getJSONObject("valueQuantity").getDouble("value");
            String cholUnit = entry.getJSONObject(0).getJSONObject("resource").getJSONObject("valueQuantity").getString("unit");
            String effectiveDate = entry.getJSONObject(0).getJSONObject("resource").getString("effectiveDateTime");


//          If you want to see the observer changing, UnComment this line below and comment the above 'cholValue'
            double cholValue = Math.random()*100;

            // Change to appropriate format
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
