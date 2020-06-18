package com.example.cholesterol.UserInterfaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cholesterol.Adapters.BPMonitorAdapter;
import com.example.cholesterol.Adapters.MonitorAdapter;
import com.example.cholesterol.Observable.NTimer;
import com.example.cholesterol.R;
import com.example.cholesterol.ServerCalls.ObservationHandler;
import com.example.cholesterol.graphs.graphActivity;

public class BPMonitorActivity extends AppCompatActivity {

    private static RecyclerView bpMonitorRecyclerView;

    public static Context context;

    public static RecyclerView getBPMonitorRecyclerView() {
        return bpMonitorRecyclerView;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp_monitor);

        getSupportActionBar().setTitle("Blood Pressure Monitor");

//        This is used to obtain the recyclerView
        bpMonitorRecyclerView = findViewById(R.id.bp_recycler);
        bpMonitorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ObservationHandler.getObservation("firstCall", 1, "XBP", false, MonitorAdapter.getHighSystolic(), this, bpMonitorRecyclerView);

    }

    public void systolicStartBtn(View view){
        final BPMonitorAdapter bpMonitorAdapter = new BPMonitorAdapter(MonitorAdapter.getHighSystolic(), this);
        bpMonitorRecyclerView.setAdapter(bpMonitorAdapter);

//        int NValue = Integer.parseInt(MonitorActivity.NRefresh.getText().toString());
//        NTimer.setN(NValue);
        NTimer.setN(10);
        NTimer.resetN();
        NTimer nTimer = new NTimer();
        nTimer.addObserver(bpMonitorAdapter);
        nTimer.startTimer();

    }


    public static void refresh(BPMonitorAdapter bpMonitorAdapter) {
        bpMonitorRecyclerView.setAdapter(bpMonitorAdapter);
        NTimer.resetN();
        NTimer nTimer = new NTimer();
        nTimer.addObserver(bpMonitorAdapter);
        nTimer.startTimer();
    }
}
