package com.example.cholesterol.UserInterfaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cholesterol.Adapters.MonitorAdapter;
import com.example.cholesterol.Observable.NTimer;
import com.example.cholesterol.Objects.Patient;
import com.example.cholesterol.R;
import com.example.cholesterol.ServerCalls.PatientData;
import java.util.HashMap;

public class MonitorActivity extends AppCompatActivity {

    private static RecyclerView monitorRecyclerView;
    private static HashMap<String, Patient> monitored = new HashMap<>();
    private static TextView name;
    private static TextView birthdate;
    private static TextView gender;
    private static TextView address;
    private static EditText NRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      Inflates the layout:
        setContentView(R.layout.activity_monitor);

//      This will set the title of the toolbar
        getSupportActionBar().setTitle("Monitored Patients");

//      This is used to obtain the recyclerView
        monitorRecyclerView = findViewById(R.id.monitor_recycler);
        monitorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//      This is used to obtain the TextViews
        name = findViewById(R.id.name);
        birthdate = findViewById(R.id.birthdate);
        gender = findViewById(R.id.gender);
        address = findViewById(R.id.address);
        NRefresh = findViewById(R.id.refresh);

    }

    /**
     * This is function that will be invoked when the start button is clicked
     * @param view viewObject: Start Button
     */
    public void startBtn(View view) {
        String N_Value = NRefresh.getText().toString();
//      If we don't put a value for:
        if(N_Value.isEmpty()){
            Toast.makeText(this, "Please enter a Refresh Value", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
//          Obtaining the HashMap of Monitored Patients
            monitored = MainActivity.getMonitoredPatients();
//          Passing the HashMap to the MonitorAdapter to populate the RecyclerView
            final MonitorAdapter monitorAdapter = new MonitorAdapter(monitored, this);
            monitorRecyclerView.setAdapter(monitorAdapter);
//          This is used to update the cholesterol levels at N_value (second(s)) intervals
            int NValue = Integer.parseInt(N_Value);
            NTimer.setN(NValue);
            NTimer.resetN();
            NTimer nTimer = new NTimer();
            nTimer.addObserver(monitorAdapter);
            nTimer.startTimer();
        }
    }

    /**
     * This function is used to subscribe to the observable and starts the timer
     * @param monitorAdapter The adapter for the recycler View
     */
    public static void refresh(MonitorAdapter monitorAdapter) {
        monitorRecyclerView.setAdapter(monitorAdapter);
        NTimer.resetN();
        NTimer nTimer = new NTimer();
        nTimer.addObserver(monitorAdapter);
        nTimer.startTimer();
    }

    /**
     * This function is used to obtain the details of a patient once it has been selected
     * @param patientID Patient Identification
     * @param context Context
     */
    public static void setDetails(String patientID, Context context){
        monitored = MainActivity.getPatientDetailsMap();
        PatientData.getDetailedPatient(monitored, patientID, context);

        Patient current = monitored.get(patientID);
        if(current.getGender() != null){
            String Name = current.getName();
            String Birthdate = current.getBirthDate();
            String Gender = current.getGender();
            String addressLine = current.getAddressLine();
            String city = current.getCity();
            String state = current.getState();
            String postalCode = current.getPostalCode();
            String country = current.getCountry();
            String fullAddress = addressLine + ", " + city + ", " + state + ", " + postalCode + ", " + country;

//          Here we bind the details to the TextView
            name.setText(Name);
            birthdate.setText(Birthdate);
            gender.setText(Gender);
            address.setText(fullAddress);

        } else {
            String Waiting = "waiting for server";
            name.setText(Waiting);
            birthdate.setText(Waiting);
            gender.setText(Waiting);
            address.setText(Waiting);
        }
    }
}
