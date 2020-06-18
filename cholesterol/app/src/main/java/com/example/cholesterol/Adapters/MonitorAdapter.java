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

import com.example.cholesterol.ServerCalls.ObservationHandler;
import com.example.cholesterol.UserInterfaces.MainActivity;
import com.example.cholesterol.Objects.Patient;
import com.example.cholesterol.R;
import com.example.cholesterol.UserInterfaces.MonitorActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;


public class MonitorAdapter extends RecyclerView.Adapter<MonitorAdapter.MonitorListView> implements Observer {

    private HashMap<String, Patient> monitoredPatientListHash;
    private Context context;

    /**
     * Constructor for MonitorAdapter
     * @param monitoredHash HashMap of Patients
     * @param context Context
     */
    public MonitorAdapter(HashMap<String, Patient> monitoredHash, Context context) {
        this.monitoredPatientListHash = monitoredHash;
        this.context = context;
    }

//  This method will be called whenever a ViewHolder is created(An Instance of ViewHolder class below)
    @NonNull
    @Override
    public MonitorListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View monitorPatient = inflater.inflate(R.layout.monitor_patients, parent, false);
        MonitorListView view = new MonitorListView(monitorPatient);

        return view;
    }

//  This method binds data to viewholder(Each Card in the recycler view)
    @Override
    public void onBindViewHolder(@NonNull final MonitorListView holder, final int position) {
        final Object[] keys = monitoredPatientListHash.keySet().toArray();

        final String patientID = monitoredPatientListHash.get(keys[position]).getPatientID();
        final String patientname = monitoredPatientListHash.get(keys[position]).getName();
        final String chol = monitoredPatientListHash.get(keys[position]).getCholesterol();
        final String effectiveDateChol = monitoredPatientListHash.get(keys[position]).getEffectiveDateChol();

        final String systolicHash = monitoredPatientListHash.get(keys[position]).getSystolic();
        final String diastolicHash = monitoredPatientListHash.get(keys[position]).getDiastolic();
        final String effectiveDateBP = monitoredPatientListHash.get(keys[position]).getEffectiveDateBP();

        double systolicHashParse = Double.parseDouble(systolicHash.replaceAll("[^\\d\\.]", ""));
        double diastolicHashParse = Double.parseDouble(diastolicHash.replaceAll("[^\\d\\.]", ""));

        holder.patientTV.setText(patientname);

        // Cholesterol
        double AverageCholesterol = getAverageReadings(monitoredPatientListHash, "chol");
        String numericChol = chol.replaceAll("[^\\d\\.]", "");
        double finalChol = Double.parseDouble(numericChol);

        holder.CholEffectiveDateTV.setText(effectiveDateChol);
        holder.BPEffectiveDateTV.setText(effectiveDateBP);

        if(finalChol > AverageCholesterol){
            holder.CholLevelTV.setText(chol);
            holder.CholLevelTV.setTextColor(Color.parseColor("#FF0000"));
        } else{
            holder.CholLevelTV.setText(chol);
        }

        // Blood Pressure

        if(systolicHashParse > MonitorActivity.getSYSTOLICBP()){
            holder.SystolicTV.setText(systolicHash);
            holder.SystolicTV.setTextColor(Color.parseColor("#800080"));
        }
        else {
            holder.SystolicTV.setText(systolicHash);
        }

        if(diastolicHashParse > MonitorActivity.getDIASTOLICBP()){
            holder.DiastolicTV.setText(diastolicHash);
            holder.DiastolicTV.setTextColor(Color.parseColor("#800080"));
        }
        else {
            holder.DiastolicTV.setText(diastolicHash);
        }




//      This method is used to delete a patient from the list of monitored Patients
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Stopped Monitoring "+ patientname +", click Refresh", Toast.LENGTH_SHORT).show();
                removeItem(monitoredPatientListHash, patientID);
            }
        });

//      This method is used to obtain the details of each patient upon click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonitorActivity.setDetails(patientID, context);
            }
        });

    }


    /**
     * This Function is used to obtain the Average Cholesterol Level of all the Monitored Patients
     * @param patientListHash HashMap of Monitored Patients
     * @return Average Cholesterol Value
     */
    private double getAverageReadings(HashMap<String, Patient> patientListHash, String valueType){
        ArrayList<Double> readingLevels = new ArrayList<>();

        final Object[] keys = patientListHash.keySet().toArray();

        String readingLevel = "";

        for(int i = 0; i<patientListHash.size(); i++){
            if (valueType.equals("chol")) {
                readingLevel = patientListHash.get(keys[i]).getCholesterol();
            }
            else if (valueType.equals("sys")) {
                readingLevel = patientListHash.get(keys[i]).getSystolic();
            }
            else if (valueType.equals("dias")) {
                readingLevel = patientListHash.get(keys[i]).getDiastolic();
            }

            try {
                readingLevel = readingLevel.replaceAll("[^\\d\\.]","");
                double numericChol = Double.parseDouble(readingLevel);
                readingLevels.add(numericChol);
            } catch(Exception e) {

            }

        }

        double total = 0.0;

        for(int i = 0; i<readingLevels.size(); i++){
            total += readingLevels.get(i);
        }

        return total/readingLevels.size();
    }

    /**
     * This function is used to remove a patient from the HashMap of Monitored Patients
     * @param monitoredPatients HashMap of Monitored Patients
     * @param patientID Patient ID
     */
    private void removeItem(HashMap<String, Patient> monitoredPatients, String patientID){
        monitoredPatients.remove(patientID);
    }

    @Override
    public int getItemCount() {
        return monitoredPatientListHash.size();
    }

    @Override
    public void update(Observable o, Object arg) {
        Log.d("timer", "time is up!");
        ObservationHandler.getObservation("Update", 2, "Chol", MainActivity.getMonitoredPatients(), MainActivity.context, MainActivity.getRecyclerView());
        ObservationHandler.getObservation("Update", 2, "BP", MainActivity.getMonitoredPatients(), MainActivity.context, MainActivity.getRecyclerView());
    }

    /**
     * Inner Class that will be used to obtain references to the views
     */
    public class MonitorListView extends RecyclerView.ViewHolder{

        private TextView patientTV;
        private TextView CholLevelTV;
        private TextView CholEffectiveDateTV;
        private ImageView imageView;
        private TextView SystolicTV;
        private TextView DiastolicTV;
        private TextView BPEffectiveDateTV;

        public MonitorListView(@NonNull View itemView) {
            super(itemView);
            patientTV = itemView.findViewById(R.id.monitor_PatientName);
            CholLevelTV = itemView.findViewById(R.id.monitor_cholLevel);
            CholEffectiveDateTV = itemView.findViewById(R.id.cholEffectiveDateTV);
            imageView = itemView.findViewById(R.id.image_delete);
            SystolicTV = itemView.findViewById(R.id.systolicTV);
            DiastolicTV = itemView.findViewById(R.id.diastolicTV);
            BPEffectiveDateTV = itemView.findViewById(R.id.BPeffectiveDateTV);

        }
    }
}
