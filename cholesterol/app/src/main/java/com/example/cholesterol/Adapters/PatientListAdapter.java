
package com.example.cholesterol.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cholesterol.Users.Patient;
import com.example.cholesterol.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.PatientListView> implements View.OnClickListener {

    private HashMap<String, Patient> patientListHash;
    private HashMap<String, Patient> monitoredPatients;

    public PatientListAdapter(HashMap<String, Patient> patientListHashA, HashMap<String, Patient> monitoredPatients){
        this.patientListHash = patientListHashA;
        this.monitoredPatients = monitoredPatients;
    }


    @NonNull
    @Override
    public PatientListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View patView = inflater.inflate(R.layout.patients, parent, false);

        PatientListView view = new PatientListView(patView);

        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull PatientListView holder, final int position) {

//      Here I obtain a the list of keys and convert it into an array in order to use "position" to access the correct card
        final Object[] keys = patientListHash.keySet().toArray();

        final String patientname = patientListHash.get(keys[position]).getName();
        final String patientID = patientListHash.get(keys[position]).getPatientID();
        final String chol = patientListHash.get(keys[position]).getCholesterol();
        final String effectiveDate = patientListHash.get(keys[position]).getEffectiveDate();


        holder.patientID.setText(patientID);
        holder.patientName.setText(patientname);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Patient patient = new Patient(patientID, patientname, chol, effectiveDate);
                monitoredPatients.put(patientID, patient);

                Snackbar.make(v,"You have Selected: " + monitoredPatients.get(keys[position]).getName(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientListHash.size();
    }

    @Override
    public void onClick(View v) {

    }


    public class PatientListView extends RecyclerView.ViewHolder {
        TextView patientID;
        TextView patientName;

        public PatientListView(@NonNull View itemView) {
            super(itemView);
            patientID = itemView.findViewById(R.id.patient_id);
            patientName = itemView.findViewById(R.id.patient_name);

        }
    }
}
