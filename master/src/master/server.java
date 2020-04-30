/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Aqeel
 */
public class server {
    
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
    
    public static void main(String[] args) throws IOException, JSONException {

//    JSONObject json = readJsonFromUrl("https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Observation?_count=13&code=2093-3&patient=3689&_sort=date&_format=json");
        
        
//    For an Individual Patient:
    JSONObject jsonPatient = readJsonFromUrl("https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/Patient/1?_format=json");
    
//    I obtain the key to the array with all the unique identifiers for a patient
    JSONArray extension = jsonPatient.getJSONArray("identifier");
    
    
//    CODE TO Obtain the identifiers of the patient.
//    for(int i = 0; i<extension.length(); i++){
//        JSONObject value = extension.getJSONObject(i);
//        
//        String finalValue = value.getString("value");
//        
//        System.out.println(finalValue);
//    }
    JSONObject marital = jsonPatient.getJSONObject("maritalStatus");
    JSONArray coding = marital.getJSONArray("coding");
//    System.out.println(coding);
    JSONObject code = coding.getJSONObject(0);
    
    String finalCode = code.getString("code");
    
    System.out.println("The patient is a " + finalCode);

//    System.out.println(jsonPatient.toString());
    
    
    
    
    
    
//    System.out.println(json.get("id"));
  }


}
