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
import com.example.cholesterol.R;

import java.util.ArrayList;
import java.util.List;

public class graphActivity extends AppCompatActivity {

    AnyChartView anyChartView;

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

        Pie pie = AnyChart.pie();

        Cartesian bar = AnyChart.bar();

        List<DataEntry> dataEntries = new ArrayList<>();

        for (int i = 0; i < months.length; i++){

            dataEntries.add(new ValueDataEntry(months[i], earnings[i]));
        }

        bar.data(dataEntries);
        anyChartView.setChart(bar);

//        pie.data(dataEntries);
//        anyChartView.setChart(pie);





    }

}
