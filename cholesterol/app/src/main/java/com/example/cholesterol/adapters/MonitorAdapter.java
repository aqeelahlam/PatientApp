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

    HashMap<String, Patient> patientListHash;

    public MonitorAdapter(HashMap<String, Patient> patientListHashA) {
        this.patientListHash = patientListHashA;
    }

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
        final Object[] keys = patientListHash.keySet().toArray();

        final String patientname = patientListHash.get(keys[position]).getName();
        final String chol = patientListHash.get(keys[position]).getCholesterol();
        final String effectiveDate = patientListHash.get(keys[position]).getEffectiveDate();

        holder.patient.setText(patientname);
        holder.effectiveDate.setText(effectiveDate);
        holder.cholLevel.setText(chol);

    }

    @Override
    public int getItemCount() {
        return patientListHash.size();
    }

    public class MonitorListView extends RecyclerView.ViewHolder{

        private TextView patient;
        private TextView effectiveDate;
        private TextView cholLevel;

        public MonitorListView(@NonNull View itemView) {
            super(itemView);

            patient = itemView.findViewById(R.id.monitor_PatientName);
            effectiveDate = itemView.findViewById(R.id.monitor_effectiveDate);
            cholLevel = itemView.findViewById(R.id.monitor_cholLevel);

        }
    }
}
