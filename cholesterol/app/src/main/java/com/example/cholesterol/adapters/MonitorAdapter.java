package com.example.cholesterol.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cholesterol.Patient;
import com.example.cholesterol.R;


import java.util.HashMap;


public class MonitorAdapter extends RecyclerView.Adapter<MonitorAdapter.MonitorListView> {

    HashMap<String, Patient> patientListHash;

    private onItemClickListener mListener;

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

        MonitorListView view = new MonitorListView(monitorPatient, mListener);

        return view;
    }

//  This method binds data to viewholder
    @Override
    public void onBindViewHolder(@NonNull final MonitorListView holder, int position) {
        final Object[] keys = patientListHash.keySet().toArray();

        final String patientID = patientListHash.get(keys[position]).getPatientID();
        final String patientname = patientListHash.get(keys[position]).getName();
        final String chol = patientListHash.get(keys[position]).getCholesterol();
        final String effectiveDate = patientListHash.get(keys[position]).getEffectiveDate();

        holder.patient.setText(patientname);
        holder.effectiveDate.setText(effectiveDate);
        holder.cholLevel.setText(chol);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), patientID, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return patientListHash.size();
    }


    public interface onItemClickListener{
        void onDeleteClick(String patientID, int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }


    public class MonitorListView extends RecyclerView.ViewHolder{

        private TextView patient;
        private TextView effectiveDate;
        private TextView cholLevel;
        public ImageView deleteImage;

        public MonitorListView(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);

            patient = itemView.findViewById(R.id.monitor_PatientName);
            effectiveDate = itemView.findViewById(R.id.monitor_effectiveDate);
            cholLevel = itemView.findViewById(R.id.monitor_cholLevel);
            deleteImage = itemView.findViewById(R.id.image_delete);

//            deleteImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listener !=null){
//                        int position = getAdapterPosition();
//                        final Object[] keys = patientListHash.keySet().toArray();
//                        String patientId = patientListHash.get(keys[position]).getPatientID();
//
//                        //SOMETHING WRONG HERE
//                        if(position != RecyclerView.NO_POSITION){
//                            listener.onDeleteClick(patientId, position);
//                        }
//                    }
//
//                }
//            });
        }

    }


}
