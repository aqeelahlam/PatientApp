package com.example.cholesterol;

import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class detailedPatientTable extends Table{

//    public static JSONObject getPatientDetails(String patientID, final ArrayList<String> CholList, final RecyclerView recyclerView) {
//        JSONObject results = null;
//        String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?_count=13&code=2093-3&patient=" + patientID + "&_sort=-date&_format=json";
//        try {
//            if (patientID.isEmpty()){
//                Toast.makeText(MainActivity.context, "Cannot be left empty", Toast.LENGTH_LONG).show();
//            }else {
//                APIRequest.makeRequest(url);
//                results = APIData.getResponse();
//            }
//
//        } catch(Exception e) {
//        }
//
//        if (results != null) {
//            try {
//                ArrayList<Patient> patients = new ArrayList<>();
//                String test = null;
//                double cholValue;
//                Patient patient;
//                String chol = null;
//                String CholLevel = null;
//                String unitValue;
//                JSONArray entry = results.getJSONArray("entry");
//                for (int i = 0; i < entry.length(); i++) {
//                    try {
//                        cholValue = entry.getJSONObject(i).getJSONObject("resource").getJSONObject("valueQuantity").getDouble("value");
//                        unitValue = entry.getJSONObject(i).getJSONObject("resource").getJSONObject("valueQuantity").getString("unit");
//                        chol = String.valueOf(cholValue);
//
//                        CholLevel = chol + unitValue;
//
//                        patient = new Patient(chol);
//
//                        CholList.add(CholLevel);
//
//                        patients.add(patient);
//
//                        Log.d("chol", String.valueOf(patients));
//                    } catch (Exception e) {
//                    }
//
//                }
//                CholesterolAdapter cholesterolAdapter = new CholesterolAdapter(CholList);
//                recyclerView.setAdapter(cholesterolAdapter);
//            } catch (Exception e) {
//
//            }
//        }
//        return results;
//    }
}
