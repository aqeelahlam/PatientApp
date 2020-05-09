package com.example.cholesterol;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class patientList extends List{


    public static void getIdentifier(String practitionerID){
        JSONObject results = null;
        String identifier = null;
        String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Practitioner/" + practitionerID + "?_format=json";

        try {
            if(practitionerID.isEmpty()){
                Toast.makeText(MainActivity.context, "Cannot be left empty", Toast.LENGTH_LONG).show();
            } else {
                APIRequest.makeRequest(url);
                results = APIData.getResponse();
            }

        } catch (Exception e){
        }

        if (results != null){
            try{
                String identifier1 = null;
                String identifier2 = null;
                JSONArray identify = results.getJSONArray("identifier");
                identifier1 = identify.getJSONObject(0).getString("system");
                identifier2 = identify.getJSONObject(0).getString("value");

                identifier = identifier1 + "|" + identifier2;
                APIData.setHpIdentifier(identifier);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static JSONObject getPatientList(String practitionerID) {

//        getIdentifier(practitionerID);
//        String identifier = APIData.getHpIdentifier();
        String identifier = "http://hl7.org/fhir/sid/us-npi|500";

        JSONObject results = null;

//        String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Encounter?participant.identifier=" + identifier + "&_include=Encounter.participant.individual&_include=Encounter.patient&_format=json";

        String url = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Encounter?_include=Encounter.participant.individual&_include=Encounter.patient&participant.identifier="  + identifier + "&_format=json";
        try {
            APIRequest.makeRequest(url);
            Log.d("url", String.valueOf(url));

            results = APIData.getResponse();
            Log.d("inner", String.valueOf(results));


        } catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }


}
