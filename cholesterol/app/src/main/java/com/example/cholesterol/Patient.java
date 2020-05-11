package com.example.cholesterol;

public class Patient {

    private String cholesterol;
    private int patientID;

    public Patient(int patientID, String cholesterol){
        this.patientID = patientID;
        this.cholesterol = cholesterol;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setCholesterol(String cholesterol) {
        this.cholesterol = cholesterol;
    }

    public String getCholesterol() {
        return cholesterol;
    }
}
