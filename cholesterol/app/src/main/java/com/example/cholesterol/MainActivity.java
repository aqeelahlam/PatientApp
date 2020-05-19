package com.example.cholesterol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    RecyclerView patientRecyclerView;


    Button monitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        patientRecyclerView = findViewById(R.id.recycler_view);
        patientRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        monitorRecyclerView = findViewById(R.id.monitor_recycler);
//        monitorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        monitor = findViewById(R.id.btn_monitor);

    }

    public void FindBtn(final View view) {

        EditText keyword;
        keyword = findViewById(R.id.editText);
//        String practitionerID = keyword.getText().toString();

        String practitionerID = "1381208";
//        String practitionerID = "6832728";

        patientList.patientHandler(practitionerID, this, patientRecyclerView);
//        monitor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = patientRecyclerView.findContainingViewHolder(view).getAdapterPosition();
//                CheckBox check = v.findViewById(R.id.checkBox);
//                if(check.isChecked()){
//                    patientRecyclerView.getAdapter().getItemId(position);
//                    Intent intent = new Intent(MainActivity.context, Monitor.class);
////                    intent.putExtra();
//                    startActivity(intent);
//                }
//
//            }
//        });


    }

    public void monitorBtn(View view) {


    }
}











