package com.example.cholesterol.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cholesterol.UserInterfaces.MainActivity;
import com.example.cholesterol.Users.Patient;
import com.example.cholesterol.R;
import com.example.cholesterol.ServerCalls.CholesterolData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;


public class MonitorAdapter extends RecyclerView.Adapter<MonitorAdapter.MonitorListView> implements Observer {

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
    public void onBindViewHolder(@NonNull final MonitorListView holder, final int position) {
        final Object[] keys = patientListHash.keySet().toArray();

        final String patientID = patientListHash.get(keys[position]).getPatientID();
        final String patientname = patientListHash.get(keys[position]).getName();
        final String chol = patientListHash.get(keys[position]).getCholesterol();
        final String effectiveDate = patientListHash.get(keys[position]).getEffectiveDate();

        holder.patient.setText(patientname);
        holder.effectiveDate.setText(effectiveDate);

        double AverageCholesterol = getAverageCholesterol(patientListHash);
        String numericChol = chol.replaceAll("[^\\d\\.]","");
        double finalChol = Double.parseDouble(numericChol);

        try {
            if(finalChol>AverageCholesterol){
                holder.cholLevel.setText(chol);
                holder.cholLevel.setTextColor(Color.parseColor("#FF0000"));
            }else{
                holder.cholLevel.setText(chol);
            }
        } catch (Exception e) {
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Stopped Monitoring "+ patientname +", click Refresh", Toast.LENGTH_LONG).show();
                removeItem(patientListHash, patientID);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), patientID, Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * This Function is used to obtain the Average Cholesterol Level of all the Monitored Patients
     * @param patientListHash HashMap of Monitored Patients
     * @return Average Cholesterol Value
     *
     *
     */
    private double getAverageCholesterol(HashMap<String, Patient> patientListHash){
        ArrayList<Double> cholLevels = new ArrayList<>();

        final Object[] keys = patientListHash.keySet().toArray();

        for(int i = 0; i<patientListHash.size(); i++){
            String cholLevel = patientListHash.get(keys[i]).getCholesterol().replaceAll("[^\\d\\.]","");
            double numericChol = Double.parseDouble(cholLevel);
            cholLevels.add(numericChol);
        }

        double total = 0.0;

        for(int i = 0; i<cholLevels.size(); i++){
            total += cholLevels.get(i);
        }

        return total/cholLevels.size();
    }

    /**
     * This function is used to remove a patient from the HashMap of Monitored Patients
     * @param patientListHash HashMap of Monitored Patients
     * @param patientID Patient ID
     */
    private void removeItem(HashMap<String, Patient> patientListHash, String patientID){
        patientListHash.remove(patientID);
    }

    @Override
    public int getItemCount() {
        return patientListHash.size();
    }

    @Override
    public void update(Observable o, Object arg) {
        Log.d("timer", "time is up!");
        CholesterolData.getUpdate(MainActivity.getPatientDetailsMap(), MainActivity.getMonitoredPatients(), MainActivity.context);
    }



    public class MonitorListView extends RecyclerView.ViewHolder{

        private TextView patient;
        private TextView effectiveDate;
        private TextView cholLevel;
        private ImageView imageView;

        public MonitorListView(@NonNull View itemView) {
            super(itemView);

            patient = itemView.findViewById(R.id.monitor_PatientName);
            effectiveDate = itemView.findViewById(R.id.monitor_effectiveDate);
            cholLevel = itemView.findViewById(R.id.monitor_cholLevel);
            imageView = itemView.findViewById(R.id.image_delete);

        }

    }


}
