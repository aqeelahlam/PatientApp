package com.example.cholesterol.graphs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
import com.example.cholesterol.UserInterfaces.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class graphActivity extends AppCompatActivity {

    AnyChartView anyChartView;

    private static HashMap<String, Patient> patientDetailsMap = new HashMap<>();


    String[] months = {"Jan", "Feb", "Mar"};
    int[] earnings = {500, 800, 2000};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        anyChartView = findViewById(R.id.any_chart_view);
        setupBarChart();

    }

    public void setupBarChart(){

        patientDetailsMap = MainActivity.getMonitoredPatients();

        Cartesian column = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();

        final Object[] keys = patientDetailsMap.keySet().toArray();

        for(int i=0; i<patientDetailsMap.size(); i++){
            final String chol = patientDetailsMap.get(keys[i]).getCholesterol();
            String numericChol = chol.replaceAll("[^\\d\\.]", "");
            double finalChol = Double.parseDouble(numericChol);
            String name = patientDetailsMap.get(keys[i]).getName();

            data.add(new ValueDataEntry(name, finalChol));
        }


//
//        data.add(new ValueDataEntry("Rouge", 80540));
//        data.add(new ValueDataEntry("Foundation", 94190));
//        data.add(new ValueDataEntry("Mascara", 102610));



//        for (int i = 0; i < months.length; i++){
//
//            dataEntries.add(new ValueDataEntry(months[i], earnings[i]));
//        }

        column.data(data);
        anyChartView.setChart(column);
        column.title("Total Cholesterol mg/dL");


    }

}
