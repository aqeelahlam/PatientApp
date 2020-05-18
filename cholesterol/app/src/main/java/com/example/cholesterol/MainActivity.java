package com.example.cholesterol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

    public static Context context;
    RecyclerView recyclerView;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void FindBtn(View view) {

        EditText keyword;
        keyword = findViewById(R.id.editText);
//        String practitionerID = keyword.getText().toString();

        String practitionerID = "1381208";
//        String practitionerID = "6832728";

        patientList.patientHandler(practitionerID, this, recyclerView);

    }

    public void monitorBtn(View view) {

    }
}











