package com.example.cholesterol.graphs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.gauge.pointers.Bar;
import com.anychart.enums.Position;
import com.example.cholesterol.Objects.Patient;
import com.example.cholesterol.R;
import com.example.cholesterol.ServerCalls.ObservationHandler;
import com.example.cholesterol.UserInterfaces.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class graphActivity extends AppCompatActivity {

    AnyChartView anyChartView;
    Cartesian column;
    Handler handler;



    private static HashMap<String, Patient> monitoredPatientsObtainedMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        anyChartView = findViewById(R.id.any_chart_view);
        setupBarChart();

    }

    public void setupBarChart(){

        monitoredPatientsObtainedMap = MainActivity.getMonitoredPatients();

        if(monitoredPatientsObtainedMap.isEmpty()){
            Toast.makeText(this, "No patients to Monitor", Toast.LENGTH_LONG).show();
        }

        column = AnyChart.column();
        handler = new Handler();


        List<DataEntry> data = new ArrayList<>();

        final Object[] keys = monitoredPatientsObtainedMap.keySet().toArray();

        for(int i=0; i<monitoredPatientsObtainedMap.size(); i++){
            final String chol = monitoredPatientsObtainedMap.get(keys[i]).getCholesterol();
            String numericChol = chol.replaceAll("[^\\d\\.]", "");
            double finalChol = Double.parseDouble(numericChol);
            final String name = monitoredPatientsObtainedMap.get(keys[i]).getName();

            data.add(new ValueDataEntry(name, finalChol));

        }


        column.data(data);
        anyChartView.setChart(column);

//        column.labels().enabled(true).format("{%y}");
        column.labels().enabled(true);
//        column.tooltip().format("Patient Name: {%y}");

//      Padding Between Graphs
        column.barGroupsPadding(2);

//        column.animation(true);
        column.height();
        column.title("Total Cholesterol mg/dL");

        final int delayMillis = 10000;
//        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            public void run() {
                updateBarChart();

                handler.postDelayed(this, delayMillis);
            }
        };
        handler.postDelayed(runnable, delayMillis);

    }

    public void updateBarChart() {

        ObservationHandler.getObservation("Update", 1, "Chol", true, monitoredPatientsObtainedMap, MainActivity.context, MainActivity.getRecyclerView());

        List<DataEntry> data = new ArrayList<>();

        final Object[] keys = monitoredPatientsObtainedMap.keySet().toArray();

        for(int i=0; i<monitoredPatientsObtainedMap.size(); i++){
            final String chol = monitoredPatientsObtainedMap.get(keys[i]).getCholesterol();
            String numericChol = chol.replaceAll("[^\\d\\.]", "");
            double finalChol = Double.parseDouble(numericChol);
            final String name = monitoredPatientsObtainedMap.get(keys[i]).getName();

            Log.d("chol", String.valueOf(finalChol));

            data.add(new ValueDataEntry(name, finalChol));

        }

        column.data(data);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        handler.removeCallbacksAndMessages(null);
    }

}
