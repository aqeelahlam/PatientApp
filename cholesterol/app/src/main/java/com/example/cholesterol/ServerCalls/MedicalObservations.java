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
import com.example.cholesterol.Objects.Patient;
import com.example.cholesterol.UserInterfaces.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class MedicalObservations {


    MedicalObservations() {
    }


    public abstract void cleanObservation(JSONObject response, HashMap<String, Patient> patientHashMap, HashMap<String, Patient> monitoredPatients, String patientID, final RecyclerView recyclerView, final Context context) throws JSONException, ParseException;


    public abstract void cleanUpdatedObservation(ArrayList<JSONObject> responseList, HashMap<String, Patient> patientHashMap, HashMap<String, Patient> monitoredPatients, Object[] patientsBundle, Context context) throws JSONException, ParseException, InterruptedException;
}

