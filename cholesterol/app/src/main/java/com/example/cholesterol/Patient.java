package com.example.cholesterol;

public class Patient {

    private String cholesterol;
    private String patientID;

    public Patient(String patientID, String cholesterol){
        this.patientID = patientID;
        this.cholesterol = cholesterol;
    }

    public Patient() {
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setCholesterol(String cholesterol) {
        this.cholesterol = cholesterol;
    }

    public String getCholesterol() {
        return cholesterol;
    }
}
