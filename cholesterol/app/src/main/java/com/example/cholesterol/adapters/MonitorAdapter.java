package com.example.cholesterol.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cholesterol.Patient;
import com.example.cholesterol.R;

import java.util.HashMap;

public class MonitorAdapter extends RecyclerView.Adapter<MonitorAdapter.MonitorListView>{

    public MonitorAdapter(HashMap<String, Patient> patientListHashA) {
        this.patientListHash = patientListHashA;
    }

    HashMap<String, Patient> patientListHash;


//  This method will be called whenever a ViewHolder is created(An Instance of ViewHolder class below
    @NonNull
    @Override
    public MonitorListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View monitorPatient = inflater.inflate(R.layout.monitor_patients, parent, false);

        MonitorListView view = new MonitorListView(monitorPatient);

        return view;
    }

//  This method binds data to viewholder
    @Override
    public void onBindViewHolder(@NonNull MonitorListView holder, int position) {






    }

    @Override
    public int getItemCount() {
        return patientListHash.size();
    }

    public class MonitorListView extends RecyclerView.ViewHolder{

        private TextView patient;
        private TextView issuedDate;

        public MonitorListView(@NonNull View itemView) {
            super(itemView);

            patient = itemView.findViewById(R.id.monitor_patient);
            issuedDate = itemView.findViewById(R.id.monitor_issueDate);

        }
    }
}
