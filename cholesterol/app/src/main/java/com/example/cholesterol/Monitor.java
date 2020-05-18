package com.example.cholesterol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

public class Monitor extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_monitor);

        recyclerView = findViewById(R.id.MonitorRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
