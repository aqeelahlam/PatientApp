package com.example.cholesterol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CholesterolAdapter extends RecyclerView.Adapter<CholesterolAdapter.CholesterolView> implements View.OnClickListener {

    ArrayList<String> mCholLevel;

    public CholesterolAdapter(ArrayList<String> mCholLevel){
        this.mCholLevel = mCholLevel;

    }

    @NonNull
    @Override
    public CholesterolView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View cholView = inflater.inflate(R.layout.chol_level, parent, false);

        CholesterolView view = new CholesterolView(cholView);

        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull CholesterolView holder, int position) {
        String currentChol = mCholLevel.get(position);
        holder.cholLevel.setText(currentChol);


    }

    @Override
    public int getItemCount() {
        return mCholLevel.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class CholesterolView extends RecyclerView.ViewHolder {
        TextView cholLevel;

        public CholesterolView(@NonNull View itemView) {
            super(itemView);
            cholLevel = itemView.findViewById(R.id.cholLevel);

        }
    }
}
