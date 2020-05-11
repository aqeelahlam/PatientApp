package com.example.cholesterol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/*
* Android: 9.0 (Pie)
* API Level: 28
*
* Make sure the app is given "Internet" access within the AndroidManifest.xml  :-
* Place the dependency right below "package"
* <uses-permission android:name="android.permission.INTERNET" />
*
* I have used Volley to perform the relevant network requests.
* https://developer.android.com/training/volley
*
*
* */

public class MainActivity extends AppCompatActivity {

    public static int counter = 0;
    public static Context context;
    //START
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    TextView tv;
    ArrayList<String> cholesterol = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

//        CholesterolAdapter cholesterolAdapter = new CholesterolAdapter(cholesterol);

//        recyclerView.setAdapter(cholesterolAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        tv = findViewById(R.id.tv);

//        Log.d("onCreate", "setting context");
//        patientList.setContext(this);
//        Log.d("onCreate", "initializing requestQueue");
//        patientList.initializeQueue();
    }

    public void trialBtn(View view) {

        EditText keyword;
        keyword = findViewById(R.id.editText);
        String patientID = keyword.getText().toString();

        ArrayList<String> test_list = new ArrayList<>();

//        monitoredPatientsTable.getCholesterol(patientID, test_list, recyclerView);
//        JSONObject response = APIData.getResponse();
        Log.d("results", String.valueOf(patientList.getJsonResults()));

    }

//    Best to show this button after the results appear:




    public void monitorBtn(View view) {
        EditText keyword;
        keyword = findViewById(R.id.editText);
        String practitionerID = keyword.getText().toString();
        practitionerID = "1381208";


        patientList.getPatientList(practitionerID, this);

        counter = counter + 1;
        Log.d("counter", String.valueOf(counter));

        Log.d("results", String.valueOf(patientList.getJsonResults()));
    }
}











