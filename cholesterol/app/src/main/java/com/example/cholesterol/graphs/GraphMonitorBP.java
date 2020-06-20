package com.example.cholesterol.graphs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.cholesterol.Adapters.BPMonitorAdapter;
import com.example.cholesterol.Adapters.MonitorAdapter;
import com.example.cholesterol.Objects.Patient;
import com.example.cholesterol.R;
import com.example.cholesterol.ServerCalls.ObservationHandler;
import com.example.cholesterol.UserInterfaces.BPMonitorActivity;
import com.example.cholesterol.UserInterfaces.MainActivity;
import com.example.cholesterol.UserInterfaces.MonitorActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GraphMonitorBP extends AppCompatActivity {
    private AnyChartView lineChart;
    Handler handler;
    Cartesian cartesian;
    Line series1;

    HashMap<String, Patient> SystolicBP = new HashMap<>();
    HashMap<Integer, ArrayList<String>> XlatestBP = new HashMap<>();
    Object[] keySetSystolic;
    Object[] keySetXlatest;
    ArrayList<String> value = new ArrayList<>();
    String PatientName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();

        setContentView(R.layout.activity_graph_monitor_b_p);
//        lineChart = findViewById(R.id.any_chart_view_BP);
        lineChart = findViewById(R.id.any_chart_view_BP);

        ArrayList<DataEntry> entries = new ArrayList<>();

        SystolicBP = MonitorAdapter.getHighSystolic();

        keySetSystolic = SystolicBP.keySet().toArray();


        assert keySetSystolic != null;
        for (Object KeySystol : keySetSystolic) {
//            XlatestBP = SystolicBP.get(KeySystol).getXLatestBP();
            XlatestBP = BPMonitorAdapter.getTest1();
            PatientName = SystolicBP.get(KeySystol).getName();

            for (int i = 0; i < XlatestBP.size(); i++) {
                keySetXlatest = XlatestBP.keySet().toArray();
                value = XlatestBP.get(keySetXlatest[i]);
                Double BP = Double.parseDouble(value.get(1).replaceAll("[^\\d\\.]", ""));
                entries.add(new ValueDataEntry(String.valueOf(i), BP));
            }
        }

        cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.padding(10d, 20d, 5d, 20d);
        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.title(PatientName);
        cartesian.yAxis(0).title("Blood Pressure");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

//        Set set = Set.instantiate();
//        set.data(getData());
//        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
//        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
//        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");


        series1 = cartesian.line(entries);
//        Line series1 = cartesian.line(series1Mapping);

        series1.name("Systolic BP");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
//        series1.tooltip()
//                .position("right")
//                .offsetX(5d)
//                .offsetY(5d);
//        Line series2 = cartesian.line(series2Mapping);
//        series2.name("Hotdog");
//        series2.hovered().markers().enabled(true);
//        series2.hovered().markers()
//                .type(MarkerType.CIRCLE)
//                .size(4d);
//        series2.tooltip()
//                .position("right")
//                .offsetX(5d)
//                .offsetY(5d);
//        Line series3 = cartesian.line(series3Mapping);
//        series3.name("Icecream");
//        series3.hovered().markers().enabled(true);
//        series3.hovered().markers()
//                .type(MarkerType.CIRCLE)
//                .size(4d);
//        series3.tooltip()
//                .position("right")
//                .offsetX(5d)
//                .offsetY(5d);

        cartesian.legend().enabled(true);

        cartesian.legend().fontSize(13d);

        cartesian.legend().padding(0d, 0d, 10d, 0d);
        lineChart.setChart(cartesian);

        final int delayMillis = 10000;
//        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            public void run() {
                updateLineGraph();

                handler.postDelayed(this, delayMillis);
            }
        };
        handler.postDelayed(runnable, delayMillis);
    }



    public void updateLineGraph() {

        ObservationHandler.getObservation("Update", 1, "XBP", true, MonitorAdapter.getHighSystolic(), BPMonitorActivity.context, BPMonitorActivity.getBPMonitorRecyclerView());

        ArrayList<DataEntry> entries = new ArrayList<>();
        SystolicBP = MonitorAdapter.getHighSystolic();
        keySetSystolic = SystolicBP.keySet().toArray();

        assert keySetSystolic != null;
        for (Object KeySystol : keySetSystolic) {
//            XlatestBP = SystolicBP.get(KeySystol).getXLatestBP();
            XlatestBP = BPMonitorAdapter.getTest1();
            PatientName = SystolicBP.get(KeySystol).getName();

            for (int i = 0; i < XlatestBP.size(); i++) {
                keySetXlatest = XlatestBP.keySet().toArray();
                value = XlatestBP.get(keySetXlatest[i]);
                Double BP = Double.parseDouble(value.get(1).replaceAll("[^\\d\\.]", ""));
                entries.add(new ValueDataEntry(String.valueOf(i), BP));
            }
        }
        cartesian.removeAllSeries();
        series1 = cartesian.line(entries);
        series1.name("Systolic BP");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);

    }

    private ArrayList getData(){
        ArrayList<DataEntry> entries = new ArrayList<>();
//        entries.add(new DataEntry("2012", 3.4));

        entries.add(new CustomDataEntry("2012", 3.6, 2.3, 2.8));
        entries.add(new CustomDataEntry("2013", 7.1, 4.0, 4.1));
        entries.add(new CustomDataEntry("2014", 8.5, 6.2, 5.1));
        entries.add(new CustomDataEntry("2015", 9.2, 11.8, 6.5));
        entries.add(new CustomDataEntry("2016", 10.1, 13.0, 12.5));
        entries.add(new CustomDataEntry("2017", 11.6, 13.9, 18.0));
        entries.add(new CustomDataEntry("2018", 16.4, 18.0, 21.0));
        entries.add(new CustomDataEntry("2019", 18.0, 23.3, 20.3));
        return entries;
    }

    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        handler.removeCallbacksAndMessages(null);
        BPMonitorAdapter bpMonitorAdapter = new BPMonitorAdapter(MonitorAdapter.getHighSystolic(), this);
        BPMonitorActivity.refresh(bpMonitorAdapter);
    }
}