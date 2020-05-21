package com.example.cholesterol.UserInterfaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;

import com.example.cholesterol.Adapters.MonitorAdapter;
import com.example.cholesterol.Observable.NTimer;
import com.example.cholesterol.Users.Patient;
import com.example.cholesterol.R;

import java.util.HashMap;

public class MonitorActivity extends AppCompatActivity {

    public static RecyclerView monitorRecyclerView;
    HashMap<String, Patient> monitored = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        getSupportActionBar().setTitle("Monitored Patients");

        monitorRecyclerView = findViewById(R.id.monitor_recycler);
        monitorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void startBtn(View view) {
        monitored = MainActivity.getMonitoredPatients();

        final MonitorAdapter monitorAdapter = new MonitorAdapter(monitored);
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




}
