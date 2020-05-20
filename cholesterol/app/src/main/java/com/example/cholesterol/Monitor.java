package com.example.cholesterol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cholesterol.adapters.MonitorAdapter;

import java.util.HashMap;

public class Monitor extends AppCompatActivity {

    public static RecyclerView monitorRecyclerView;
    HashMap<String, Patient> monitored = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        monitorRecyclerView = findViewById(R.id.monitor_recycler);
        monitorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void start(View view) {
        monitored = MainActivity.getMonitoredPatients();

        MonitorAdapter monitorAdapter = new MonitorAdapter(monitored);
        monitorRecyclerView.setAdapter(monitorAdapter);

        NTimer.resetN();
        NTimer nTimer = new NTimer();
        nTimer.addObserver(monitorAdapter);
        nTimer.startTimer();

    }


    public static void refresh(MonitorAdapter monitorAdapter) throws InterruptedException {

//        HashMap<String, Patient> testMap = new HashMap<>();
//        MonitorAdapter test = new MonitorAdapter(testMap);
//        monitorRecyclerView.setAdapter(test);
//
//        Thread.sleep(5000);

        monitorRecyclerView.setAdapter(monitorAdapter);

        NTimer.resetN();
        NTimer nTimer = new NTimer();
        nTimer.addObserver(monitorAdapter);
        nTimer.startTimer();
    }



}
