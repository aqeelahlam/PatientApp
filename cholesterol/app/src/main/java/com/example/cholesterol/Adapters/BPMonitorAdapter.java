package com.example.cholesterol.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cholesterol.R;

public class BPMonitorAdapter extends RecyclerView.Adapter<BPMonitorAdapter.BPMonitorListView> {


    @NonNull
    @Override
    public BPMonitorListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View BPMonitorView = inflater.inflate(R.layout.bp_monitor_card, parent, false);
        BPMonitorListView view = new BPMonitorListView(BPMonitorView);

        return view;

    }

    @Override
    public void onBindViewHolder(@NonNull BPMonitorListView holder, int position) {
//        Here is where you bind the view with the data


    }

    @Override
    public int getItemCount() {
//      Change this to the list.size()
        return 0;
    }

    public class BPMonitorListView extends RecyclerView.ViewHolder{
        TextView patientName;
        TextView SystolicValues;


        public BPMonitorListView(@NonNull View itemView) {
            super(itemView);
            patientName = itemView.findViewById(R.id.bp_patient);
            SystolicValues = itemView.findViewById(R.id.bp_systolic);

        }
    }
}
