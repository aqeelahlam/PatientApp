
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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.PatientListView> implements View.OnClickListener {

    ArrayList<ArrayList<String>> patientDetails;

    public PatientListAdapter(ArrayList<ArrayList<String>> patientDetails){
        this.patientDetails = patientDetails;

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
        final String name = patientDetails.get(0).get(position);
        final String patientID = patientDetails.get(1).get(position);
        holder.patList.setText(patientID + ": " + name);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return patientDetails.get(1).size();
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
