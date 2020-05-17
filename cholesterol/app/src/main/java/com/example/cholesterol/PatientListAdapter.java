
package com.example.cholesterol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.PatientListView> implements View.OnClickListener {

    HashMap<String, Patient> patientListHash;

    public PatientListAdapter(HashMap<String, Patient> patientListHashA){
        this.patientListHash = patientListHashA;

    }

    @NonNull
    @Override
    public PatientListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View patView = inflater.inflate(R.layout.chol_level, parent, false);

        PatientListView view = new PatientListView(patView);

        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull PatientListView holder, int position) {

//      Here I obtain a the list of keys and convert it into an array in order to use "position" to access the correct card
        Object[] keys = patientListHash.keySet().toArray();

        String patientname = patientListHash.get(keys[position]).getName();
        String patientID = patientListHash.get(keys[position]).getPatientID();
        holder.patList.setText(patientID + " : " + patientname);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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
        TextView patList;
        CheckBox selected;

        public PatientListView(@NonNull View itemView) {
            super(itemView);
            patList = itemView.findViewById(R.id.cholLevel);
            selected = itemView.findViewById(R.id.checkBox);

        }
    }
}
