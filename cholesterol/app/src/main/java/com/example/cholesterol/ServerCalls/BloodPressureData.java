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
import com.example.cholesterol.Objects.Patient;
import com.example.cholesterol.UserInterfaces.MainActivity;
import com.example.cholesterol.UserInterfaces.MonitorActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BloodPressureData extends MedicalObservations {

    public BloodPressureData() {
        super();
    }


    @Override
    public void cleanObservation(final String job, final int totalObservationTypes, JSONObject response, HashMap<String, Patient> monitoredPatients, String patientID, RecyclerView recyclerView, Context context, int counter, int max_length) throws JSONException, ParseException {
        JSONArray entry = response.getJSONArray("entry");
        double systolicBP = entry.getJSONObject(0).getJSONObject("resource").getJSONArray("component").getJSONObject(1).getJSONObject("valueQuantity").getInt("value");
        String systolicBPUnit = entry.getJSONObject(0).getJSONObject("resource").getJSONArray("component").getJSONObject(1).getJSONObject("valueQuantity").getString("unit");
        systolicBPUnit = systolicBPUnit.replaceAll("[^A-Za-z]","");

        double diasystolicBP = entry.getJSONObject(0).getJSONObject("resource").getJSONArray("component").getJSONObject(0).getJSONObject("valueQuantity").getInt("value");
        String diasystolicBPUnit = entry.getJSONObject(0).getJSONObject("resource").getJSONArray("component").getJSONObject(0).getJSONObject("valueQuantity").getString("unit");
        diasystolicBPUnit = diasystolicBPUnit.replaceAll("[^A-Za-z]","");

        String effectiveDate = entry.getJSONObject(0).getJSONObject("resource").getString("effectiveDateTime");

        String result;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        Date d = df.parse(effectiveDate);
        df.applyPattern("dd-M-yyyy hh:mm:ss");
        result = df.format(d);


        if (job.equals("Update")) {
            systolicBP = Math.random()*100;
        }

        monitoredPatients.get(patientID).setSystolic(systolicBP + systolicBPUnit);
        monitoredPatients.get(patientID).setEffectiveDate(result);

//        Log.d("job", job);
        if (job.equals("Update") && counter == max_length) {
            MonitorAdapter monitorAdapter = new MonitorAdapter(MainActivity.getMonitoredPatients(), MainActivity.context);
            MonitorActivity.refresh(monitorAdapter);
        }
        else {

            try {
                MainActivity.setMonitoredPatients(patientID, monitoredPatients.get(patientID));
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void cleanLatestXObservations(String job, int totalObservationTypes, int X, JSONObject response, HashMap<String, Patient> monitoredPatients, String patientID, RecyclerView recyclerView, Context context, int counter, int max_length) throws JSONException, ParseException {
        JSONArray entry = response.getJSONArray("entry");

        String systolicBPUnit = entry.getJSONObject(0).getJSONObject("resource").getJSONArray("component").getJSONObject(1).getJSONObject("valueQuantity").getString("unit");
        systolicBPUnit = systolicBPUnit.replaceAll("[^A-Za-z]","");

        double systolicBP = 0;
        String effectiveDate;
        String result = "";
        for (int i=0; i < X; i++) {
            systolicBP = entry.getJSONObject(i).getJSONObject("resource").getJSONArray("component").getJSONObject(1).getJSONObject("valueQuantity").getInt("value");
            effectiveDate = entry.getJSONObject(i).getJSONObject("resource").getString("effectiveDateTime");

            if (job.equals("Update")) {
                systolicBP = Math.random()*100;
            }

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
            Date d = df.parse(effectiveDate);
            df.applyPattern("dd-M-yyyy hh:mm:ss");
            result = df.format(d);

            monitoredPatients.get(patientID).setXLatestBP(i, result, systolicBP + systolicBPUnit);
        }

        Log.d("job", job);
        ArrayList<String> latestX;
        for (int i=0; i < X; i++) {
            latestX = monitoredPatients.get(patientID).getXLatestBP(i);
            String currentEffectiveDateTime = latestX.get(0);
            String currentSystolicBP = latestX.get(1);
            Log.d("effectiveDateTime", currentEffectiveDateTime);
            Log.d("BP", currentSystolicBP);
        }
//        Log.d("job", job);
//        if (job.equals("Update") && counter == max_length) {
//            MonitorAdapter monitorAdapter = new MonitorAdapter(MainActivity.getMonitoredPatients(), MainActivity.context);
//            MonitorActivity.refresh(monitorAdapter);
//        }
//        else {
//
//            try {
//                MainActivity.setMonitoredPatients(patientID, monitoredPatients.get(patientID));
//            } catch (Exception e) {
//
//            }
//        }
    }

//    @Override
//    public void cleanUpdatedObservation(ArrayList<JSONObject> responseList, HashMap<String, Patient> patientHashMap, HashMap<String, Patient> monitoredPatients, Object[] patientsBundle, Context context) throws JSONException, ParseException, InterruptedException {
//
//        for(int i = 0; i < responseList.size(); i++) {
//
//            JSONArray entry = responseList.get(i).getJSONArray("entry");
////            double systolicBP = entry.getJSONObject(0).getJSONObject("resource").getJSONArray("component").getJSONObject(1).getJSONObject("valueQuantity").getInt("value");
//            String systolicBPUnit = entry.getJSONObject(0).getJSONObject("resource").getJSONArray("component").getJSONObject(1).getJSONObject("valueQuantity").getString("unit");
//            systolicBPUnit = systolicBPUnit.replaceAll("[^A-Za-z]","");
//
//            double diasystolicBP = entry.getJSONObject(0).getJSONObject("resource").getJSONArray("component").getJSONObject(0).getJSONObject("valueQuantity").getInt("value");
//            String diasystolicBPUnit = entry.getJSONObject(0).getJSONObject("resource").getJSONArray("component").getJSONObject(0).getJSONObject("valueQuantity").getString("unit");
//            diasystolicBPUnit = diasystolicBPUnit.replaceAll("[^A-Za-z]","");
//
//            String effectiveDate = entry.getJSONObject(0).getJSONObject("resource").getString("effectiveDateTime");
//
//
////          If you want to see the observer changing, UnComment this line below and comment the above 'systolicBP'
//            double systolicBP = Math.random()*100;
//
//            // Change to appropriate format
//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
//            Date result;
//
//            result = df.parse(effectiveDate);
//
//            String patientID = patientsBundle[i].toString();
//
//            patientHashMap.get(patientID).setSystolic(systolicBP + systolicBPUnit);
//            patientHashMap.get(patientID).setEffectiveDate(result.toString());
//
//            monitoredPatients.get(patientID).setSystolic(systolicBP + systolicBPUnit);
//            monitoredPatients.get(patientID).setEffectiveDate(result.toString());
//
//        }
//
//
//        MonitorAdapter monitorAdapter = new MonitorAdapter(monitoredPatients, context);
//        MonitorActivity.refresh(monitorAdapter);
//
//    }

}
