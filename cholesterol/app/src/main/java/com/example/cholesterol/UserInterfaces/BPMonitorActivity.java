package com.example.cholesterol.UserInterfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cholesterol.R;

public class BPMonitorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp_monitor);

        getSupportActionBar().setTitle("Blood Pressure Monitor");



    }
}
