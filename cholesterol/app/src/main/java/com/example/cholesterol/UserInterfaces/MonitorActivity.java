package com.example.cholesterol.UserInterfaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cholesterol.Adapters.MonitorAdapter;
import com.example.cholesterol.Observable.NTimer;
import com.example.cholesterol.Objects.Patient;
import com.example.cholesterol.R;
import com.example.cholesterol.ServerCalls.PatientData;

import java.text.BreakIterator;
import java.util.HashMap;

public class MonitorActivity extends AppCompatActivity {

    public static RecyclerView monitorRecyclerView;
    static HashMap<String, Patient> monitored = new HashMap<>();
    private static TextView name;
    private static TextView birthdate;
    private static TextView gender;
    private static TextView address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        getSupportActionBar().setTitle("Monitored Patients");

        monitorRecyclerView = findViewById(R.id.monitor_recycler);
        monitorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        name = findViewById(R.id.name);
        birthdate = findViewById(R.id.birthdate);
        gender = findViewById(R.id.gender);
        address = findViewById(R.id.address);

    }

    public void startBtn(View view) {
        monitored = MainActivity.getMonitoredPatients();

        final MonitorAdapter monitorAdapter = new MonitorAdapter(monitored, this);
        monitorRecyclerView.setAdapter(monitorAdapter);


        NTimer.resetN();
        NTimer nTimer = new NTimer();
        nTimer.addObserver(monitorAdapter);
        nTimer.startTimer();

    }

    public static void refresh(MonitorAdapter monitorAdapter) {

        monitorRecyclerView.setAdapter(monitorAdapter);

        NTimer.resetN();
        NTimer nTimer = new NTimer();
        nTimer.addObserver(monitorAdapter);
        nTimer.startTimer();
    }

    public static void setDetails(String patientID, Context context){
//        monitored = MainActivity.getMonitoredPatients();
        monitored = MainActivity.getPatientDetailsMap();
        try {
            PatientData.getDetailedPatient(monitored, patientID, context);
        } catch (Exception e) {
        }

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

            name.setText(Name);
            birthdate.setText(Birthdate);
            gender.setText(Gender);
            address.setText(fullAddress);

        }else {
            name.setText("waiting for server");
            birthdate.setText("waiting for server");
            gender.setText("waiting for server");
            address.setText("waiting for server");
        }





    }




}
